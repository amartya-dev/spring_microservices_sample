package com.amartya.queueservice;

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
    private final Cipher decipher;

    public Encryption() throws FileNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        File file = new File("security.key");
        Scanner sc = new Scanner(file);
        String keyAsString = sc.nextLine();
        byte[] encodedKey = Base64.getDecoder().decode(keyAsString);
        SecretKey key = new SecretKeySpec(encodedKey, "AES");
        this.secretKey = key;
        this.decipher = Cipher.getInstance("AES");
        this.decipher.init(Cipher.DECRYPT_MODE, this.secretKey);
    }

    public String decrypt(final String strToDecrypt) {
        try {
            return new String(this.decipher.doFinal(Base64.getDecoder()
                    .decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
