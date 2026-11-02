
package br.com.escala.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class GerarSenhaAdmin {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        System.out.print("Digite a senha do administrador: ");
        String senha = teclado.nextLine();

        try {
            String hash = HashSenha.gerar(senha);

            System.out.println("Hash gerado:");
            System.out.println(hash);

        } catch (NoSuchAlgorithmException
                | InvalidKeySpecException e) {

            System.out.println("Erro ao gerar hash da senha.");
            e.printStackTrace();
        }

        teclado.close();
    }
}
