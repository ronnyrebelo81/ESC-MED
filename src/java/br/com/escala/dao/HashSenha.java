
package br.com.escala.dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.security.MessageDigest;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashSenha {

    private static final int ITERACOES = 210000;
    private static final int TAMANHO_CHAVE = 256;
    private static final int TAMANHO_SALT = 16;

    public static String gerar(String senha)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] salt = new byte[TAMANHO_SALT];
        new SecureRandom().nextBytes(salt);

        byte[] hash = calcularHash(senha, salt, ITERACOES);

        return ITERACOES + ":"
                + Base64.getEncoder().encodeToString(salt) + ":"
                + Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verificar(String senha, String hashArmazenado)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        try {
            String[] partes = hashArmazenado.split(":");

            if (partes.length != 3) {
                return false;
            }

            int iteracoes = Integer.parseInt(partes[0]);
            byte[] salt = Base64.getDecoder().decode(partes[1]);
            byte[] hashOriginal = Base64.getDecoder().decode(partes[2]);

            byte[] hashCalculado = calcularHash(senha, salt, iteracoes);

            return MessageDigest.isEqual(hashOriginal, hashCalculado);

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static byte[] calcularHash(String senha, byte[] salt,
            int iteracoes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        PBEKeySpec spec = new PBEKeySpec(
                senha.toCharArray(),
                salt,
                iteracoes,
                TAMANHO_CHAVE
        );

        try {
            SecretKeyFactory fabrica = SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA256");

            return fabrica.generateSecret(spec).getEncoded();

        } finally {
            spec.clearPassword();
        }
    }
}
