
package br.com.escala.dao;

import br.com.escala.modelo.Escala;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EscalaDAO {

    public boolean cadastrar(Escala escala) throws SQLException {

        String sql = "INSERT INTO escalas (nome, unidade, ativa) "
                   + "VALUES (?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, escala.getNome());
            comando.setString(2, escala.getUnidade());
            comando.setBoolean(3, escala.isAtiva());

            return comando.executeUpdate() == 1;
        }
    }

    public List<Escala> listarTodas() throws SQLException {

        List<Escala> escalas = new ArrayList<>();

        String sql = "SELECT id, nome, unidade, ativa "
                   + "FROM escalas ORDER BY nome";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {

                Escala escala = new Escala();

                escala.setId(resultado.getInt("id"));
                escala.setNome(resultado.getString("nome"));
                escala.setUnidade(resultado.getString("unidade"));
                escala.setAtiva(resultado.getBoolean("ativa"));

                escalas.add(escala);
            }
        }

        return escalas;
    }
}
