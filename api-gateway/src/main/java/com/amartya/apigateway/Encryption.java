package com.amartya.apigateway;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class Encryption {
    private final SecretKey secretKey;
    private final Cipher cipher;

    public Encryption() throws FileNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        File file = new File("security.key");
        Scanner sc = new Scanner(file);
        String keyAsString = sc.nextLine();
        byte[] encodedKey = Base64.getDecoder().decode(keyAsString);
        SecretKey key = new SecretKeySpec(encodedKey, "AES");
        this.secretKey = key;
        this.cipher = Cipher.getInstance("AES");
        this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
    }

    public String encrypt(final String strToEncrypt) {
        try {
            return Base64.getEncoder()
                    .encodeToString(this.cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
}
