
package br.com.escala.dao;

import br.com.escala.modelo.Troca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class TrocaDAO {

    private static final String CONSULTA_TROCAS =
            "SELECT t.*, "
            + "ms.nome AS nome_medico_solicitante, "
            + "mb.nome AS nome_medico_substituto, "
            + "p.data_plantao, p.hora_inicio, p.hora_fim, "
            + "e.nome AS nome_escala, e.unidade "
            + "FROM trocas t "
            + "INNER JOIN plantoes p ON t.plantao_id = p.id "
            + "INNER JOIN medicos ms "
            + "ON t.medico_solicitante_id = ms.id "
            + "LEFT JOIN medicos mb "
            + "ON t.medico_substituto_id = mb.id "
            + "INNER JOIN escalas e ON p.escala_id = e.id ";

    public boolean cadastrar(Troca troca) throws SQLException {

        String sql = "INSERT INTO trocas "
                + "(plantao_id, medico_solicitante_id, "
                + "medico_substituto_id, nome_externo, "
                + "contato_externo, status) "
                + "SELECT p.id, p.medico_id, ?, ?, ?, 'PENDENTE' "
                + "FROM plantoes p "
                + "WHERE p.id = ? AND p.medico_id = ? "
                + "AND p.status = 'PROGRAMADO' "
                + "AND NOT EXISTS (SELECT 1 FROM trocas t "
                + "WHERE t.plantao_id = p.id AND t.status = 'PENDENTE')";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            if (troca.getMedicoSubstitutoId() != null) {
                comando.setInt(1, troca.getMedicoSubstitutoId());
            } else {
                comando.setNull(1, Types.INTEGER);
            }

            comando.setString(2, troca.getNomeExterno());
            comando.setString(3, troca.getContatoExterno());
            comando.setInt(4, troca.getPlantaoId());
            comando.setInt(5, troca.getMedicoSolicitanteId());

            if (troca.getMedicoSubstitutoId() != null
                    && (troca.getMedicoSubstitutoId()
                    == troca.getMedicoSolicitanteId()
                    || !medicoAtivo(conexao,
                    troca.getMedicoSubstitutoId()))) {
                return false;
            }

            return comando.executeUpdate() == 1;
        }
    }

    private boolean medicoAtivo(Connection conexao, int medicoId)
            throws SQLException {

        String sql = "SELECT id FROM medicos "
                + "WHERE id = ? AND ativo = 1 AND perfil = 'MEDICO'";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, medicoId);

            try (ResultSet resultado = comando.executeQuery()) {
                return resultado.next();
            }
        }
    }

    public List<Troca> listarTodas() throws SQLException {

        List<Troca> trocas = new ArrayList<>();

        String sql = CONSULTA_TROCAS
                + "ORDER BY t.data_solicitacao DESC";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                trocas.add(mapearTroca(resultado));
            }
        }

        return trocas;
    }

    public List<Troca> listarPorMedico(int medicoId)
            throws SQLException {

        List<Troca> trocas = new ArrayList<>();

        String sql = CONSULTA_TROCAS
                + "WHERE t.medico_solicitante_id = ? "
                + "OR t.medico_substituto_id = ? "
                + "ORDER BY t.data_solicitacao DESC";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, medicoId);
            comando.setInt(2, medicoId);

            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    trocas.add(mapearTroca(resultado));
                }
            }
        }

        return trocas;
    }

    private Troca mapearTroca(ResultSet resultado)
            throws SQLException {

        Troca troca = new Troca();

        troca.setId(resultado.getInt("id"));
        troca.setPlantaoId(resultado.getInt("plantao_id"));
        troca.setMedicoSolicitanteId(
                resultado.getInt("medico_solicitante_id"));

        int substitutoId = resultado.getInt("medico_substituto_id");

        if (resultado.wasNull()) {
            troca.setMedicoSubstitutoId(null);
        } else {
            troca.setMedicoSubstitutoId(substitutoId);
        }

        troca.setNomeExterno(resultado.getString("nome_externo"));
        troca.setContatoExterno(resultado.getString("contato_externo"));
        troca.setStatus(resultado.getString("status"));
        troca.setDataSolicitacao(
                resultado.getTimestamp("data_solicitacao"));

        troca.setNomeMedicoSolicitante(
                resultado.getString("nome_medico_solicitante"));
        troca.setNomeMedicoSubstituto(
                resultado.getString("nome_medico_substituto"));

        troca.setNomeEscala(resultado.getString("nome_escala"));
        troca.setUnidade(resultado.getString("unidade"));
        troca.setDataPlantao(resultado.getDate("data_plantao"));
        troca.setHoraInicio(resultado.getTime("hora_inicio"));
        troca.setHoraFim(resultado.getTime("hora_fim"));

        return troca;
    }

    public boolean atualizarStatus(int id, String status)
            throws SQLException {

        if (!"APROVADA".equals(status)
                && !"RECUSADA".equals(status)) {
            throw new IllegalArgumentException("Status inválido.");
        }

        String sqlBuscar = "SELECT plantao_id, medico_solicitante_id, "
                + "medico_substituto_id, status "
                + "FROM trocas WHERE id = ? FOR UPDATE";

        String sqlAtualizarPlantao = "UPDATE plantoes "
                + "SET medico_id = ? "
                + "WHERE id = ? AND status = 'PROGRAMADO'";

        String sqlAtualizarTroca = "UPDATE trocas SET status = ? "
                + "WHERE id = ? AND status = 'PENDENTE'";

        try (Connection conexao = Conexao.conectar()) {

            conexao.setAutoCommit(false);

            try {
                int plantaoId;
                int medicoSolicitanteId;
                Integer medicoSubstitutoId = null;
                String statusAtual;

                try (PreparedStatement comando
                        = conexao.prepareStatement(sqlBuscar)) {

                    comando.setInt(1, id);

                    try (ResultSet resultado = comando.executeQuery()) {

                        if (!resultado.next()) {
                            conexao.rollback();
                            return false;
                        }

                        plantaoId = resultado.getInt("plantao_id");
                        medicoSolicitanteId =
                                resultado.getInt("medico_solicitante_id");

                        int substitutoId =
                                resultado.getInt("medico_substituto_id");

                        if (!resultado.wasNull()) {
                            medicoSubstitutoId = substitutoId;
                        }

                        statusAtual = resultado.getString("status");
                    }
                }

                if (!"PENDENTE".equals(statusAtual)) {
                    conexao.rollback();
                    return false;
                }

                if ("APROVADA".equals(status)
                        && medicoSubstitutoId != null) {

                    if (medicoSubstitutoId == medicoSolicitanteId
                            || !medicoAtivo(conexao, medicoSubstitutoId)) {
                        conexao.rollback();
                        return false;
                    }

                    try (PreparedStatement comando
                            = conexao.prepareStatement(sqlAtualizarPlantao)) {

                        comando.setInt(1, medicoSubstitutoId);
                        comando.setInt(2, plantaoId);

                        if (comando.executeUpdate() != 1) {
                            conexao.rollback();
                            return false;
                        }
                    }
                }

                try (PreparedStatement comando
                        = conexao.prepareStatement(sqlAtualizarTroca)) {

                    comando.setString(1, status);
                    comando.setInt(2, id);

                    if (comando.executeUpdate() != 1) {
                        conexao.rollback();
                        return false;
                    }
                }

                conexao.commit();
                return true;

            } catch (SQLException | RuntimeException e) {
                conexao.rollback();
                throw e;
            }
        }
    }
}
