
package br.com.escala.dao;

import br.com.escala.modelo.Plantao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlantaoDAO {

    public boolean cadastrar(Plantao plantao) throws SQLException {

        String sql = "INSERT INTO plantoes "
                + "(escala_id, medico_id, data_plantao, "
                + "hora_inicio, hora_fim, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, plantao.getEscalaId());
            comando.setInt(2, plantao.getMedicoId());
            comando.setDate(3, plantao.getDataPlantao());
            comando.setTime(4, plantao.getHoraInicio());
            comando.setTime(5, plantao.getHoraFim());
            comando.setString(6, plantao.getStatus());

            return comando.executeUpdate() == 1;
        }
    }

    public List<Plantao> listarTodos() throws SQLException {

        List<Plantao> plantoes = new ArrayList<>();

        String sql = "SELECT p.id, p.escala_id, p.medico_id, "
                + "p.data_plantao, p.hora_inicio, p.hora_fim, "
                + "p.status, e.nome AS nome_escala, e.unidade, "
                + "m.nome AS nome_medico "
                + "FROM plantoes p "
                + "INNER JOIN escalas e ON p.escala_id = e.id "
                + "INNER JOIN medicos m ON p.medico_id = m.id "
                + "ORDER BY p.data_plantao, p.hora_inicio";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                plantoes.add(mapearPlantao(resultado));
            }
        }

        return plantoes;
    }

    public List<Plantao> listarPorMedico(int medicoId)
            throws SQLException {

        List<Plantao> plantoes = new ArrayList<>();

        String sql = "SELECT p.id, p.escala_id, p.medico_id, "
                + "p.data_plantao, p.hora_inicio, p.hora_fim, "
                + "p.status, e.nome AS nome_escala, e.unidade, "
                + "m.nome AS nome_medico "
                + "FROM plantoes p "
                + "INNER JOIN escalas e ON p.escala_id = e.id "
                + "INNER JOIN medicos m ON p.medico_id = m.id "
                + "WHERE p.medico_id = ? "
                + "AND p.status = 'PROGRAMADO' "
                + "ORDER BY p.data_plantao, p.hora_inicio";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, medicoId);

            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    plantoes.add(mapearPlantao(resultado));
                }
            }
        }

        return plantoes;
    }

    public List<Plantao> listarEscalaPorMedico(int medicoId)
            throws SQLException {

        List<Plantao> plantoes = new ArrayList<>();

        String sql = "SELECT p.id, p.escala_id, p.medico_id, "
                + "p.data_plantao, p.hora_inicio, p.hora_fim, "
                + "p.status, e.nome AS nome_escala, e.unidade, "
                + "m.nome AS nome_medico "
                + "FROM plantoes p "
                + "INNER JOIN escalas e ON p.escala_id = e.id "
                + "INNER JOIN medicos m ON p.medico_id = m.id "
                + "WHERE p.medico_id = ? "
                + "ORDER BY p.data_plantao DESC, p.hora_inicio";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, medicoId);

            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    plantoes.add(mapearPlantao(resultado));
                }
            }
        }

        return plantoes;
    }

    private Plantao mapearPlantao(ResultSet resultado)
            throws SQLException {

        Plantao plantao = new Plantao();

        plantao.setId(resultado.getInt("id"));
        plantao.setEscalaId(resultado.getInt("escala_id"));
        plantao.setMedicoId(resultado.getInt("medico_id"));
        plantao.setDataPlantao(resultado.getDate("data_plantao"));
        plantao.setHoraInicio(resultado.getTime("hora_inicio"));
        plantao.setHoraFim(resultado.getTime("hora_fim"));
        plantao.setStatus(resultado.getString("status"));
        plantao.setNomeEscala(resultado.getString("nome_escala"));
        plantao.setUnidade(resultado.getString("unidade"));
        plantao.setNomeMedico(resultado.getString("nome_medico"));

        return plantao;
    }
}
