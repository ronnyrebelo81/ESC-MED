package br.com.escala.dao;

import br.com.escala.modelo.Medico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    public boolean cadastrar(Medico medico) throws SQLException {

        String sql = "INSERT INTO medicos "
                + "(nome, crm, especialidade, usuario, senha_hash, perfil, ativo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, medico.getNome());
            comando.setString(2, medico.getCrm());
            comando.setString(3, medico.getEspecialidade());
            comando.setString(4, medico.getUsuario());
            comando.setString(5, medico.getSenhaHash());
            comando.setString(6, medico.getPerfil());
            comando.setBoolean(7, medico.isAtivo());

            return comando.executeUpdate() == 1;
        }
    }

    public Medico buscarPorUsuario(String usuario) throws SQLException {

        String sql = "SELECT * FROM medicos WHERE usuario = ? AND ativo = 1";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, usuario);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {

                    Medico medico = new Medico();

                    medico.setId(resultado.getInt("id"));
                    medico.setNome(resultado.getString("nome"));
                    medico.setCrm(resultado.getString("crm"));
                    medico.setEspecialidade(resultado.getString("especialidade"));
                    medico.setUsuario(resultado.getString("usuario"));
                    medico.setSenhaHash(resultado.getString("senha_hash"));
                    medico.setPerfil(resultado.getString("perfil"));
                    medico.setAtivo(resultado.getBoolean("ativo"));

                    return medico;
                }
            }
        }

        return null;
    }

    public List<Medico> listarTodos() throws SQLException {

        List<Medico> medicos = new ArrayList<>();

        String sql = "SELECT * FROM medicos "
                + "WHERE ativo = 1 AND perfil = 'MEDICO' "
                + "ORDER BY nome";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {

                Medico medico = new Medico();

                medico.setId(resultado.getInt("id"));
                medico.setNome(resultado.getString("nome"));
                medico.setCrm(resultado.getString("crm"));
                medico.setEspecialidade(resultado.getString("especialidade"));
                medico.setUsuario(resultado.getString("usuario"));
                medico.setSenhaHash(resultado.getString("senha_hash"));
                medico.setPerfil(resultado.getString("perfil"));
                medico.setAtivo(resultado.getBoolean("ativo"));

                medicos.add(medico);
            }
        }

        return medicos;
    }
}