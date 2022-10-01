package com.wolves.mainproject.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256 implements Crypto{
    public static String alg = "AES/CBC/PKCS5Padding";
    private final String key = "01234567890123456789012345678901";
    private final String iv = key.substring(0, 16); // 16byte

    public String encrypt(String text)  {
        Cipher cipher = getCipher();
        cipherInitialize(Cipher.ENCRYPT_MODE, cipher);

        byte[] encrypted = cipherDoFinal(getStringBytes(text, "UTF-8"), cipher);
        return Base64.getEncoder().encodeToString(encrypted);
    }


    public String decrypt(String cipherText) {
        Cipher cipher = getCipher();
        cipherInitialize(Cipher.DECRYPT_MODE, cipher);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipherDoFinal(decodedBytes, cipher);
        return getBytesToString(decrypted, "UTF-8");
    }

    private Cipher getCipher(){
        try {
            return Cipher.getInstance(alg);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private void cipherInitialize(int mode, Cipher cipher){
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(mode, keySpec, ivParamSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] cipherDoFinal(byte[] bytes, Cipher cipher){
        try {
            return cipher.doFinal(bytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getStringBytes(String text, String charsetName){
        try {
            return text.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getBytesToString(byte[] bytes, String charsetName){
        try {
            return new String(bytes, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}