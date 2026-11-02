package br.com.escala.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL =
        "jdbc:mysql://127.0.0.1:3307/escala_plantao_medico"
        + "?allowPublicKeyRetrieval=true&sslMode=DISABLED"
        + "&serverTimezone=UTC";

    private static final String USUARIO =
        System.getenv("ESC_MED_DB_USER");

    private static final String SENHA =
        System.getenv("ESC_MED_DB_PASSWORD");

    public static Connection conectar() throws SQLException {

        if (USUARIO == null || SENHA == null) {
            throw new SQLException(
                "Variáveis de ambiente do banco de dados não configuradas."
            );
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                "Driver MySQL não encontrado na aplicação.", e
            );
        }

        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}