package com.dmf.boot.learn.algorithms;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

/**
 * @author dengmingfeng
 * @date 2019/12/19
 */
public class RSACrypt {

    /**
     * 生成RAS公钥与私钥字符串，直接返回
     *
     * @return
     */
    public static HashMap<String, String> getKeys() {
        HashMap<String, String> map = new HashMap<String, String>();
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到公钥字符串
        String publicKey = base64ToStr(keyPair.getPublic().getEncoded());
        // 得到私钥字符串
        String privateKey = base64ToStr(keyPair.getPrivate().getEncoded());
        map.put("publicKey", publicKey);
        map.put("privateKey", privateKey);
        return map;
    }

    /**
     * 根据公钥字符串加载公钥
     *
     * @param publicKeyStr 公钥字符串
     * @return
     * @throws Exception
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = javax.xml.bind.DatatypeConverter.parseBase64Binary(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法", e);
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法", e);
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空", e);
        }
    }

    /**
     * 根据私钥字符串加载私钥
     *
     * @param privateKeyStr 私钥字符串
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = javax.xml.bind.DatatypeConverter.parseBase64Binary(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法", e);
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法", e);
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空", e);
        }
    }

    /**
     * 公钥加密
     *
     * @param publicKey     公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return base64ToStr(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }


    /**
     * 私钥解密
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return new String(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("密文数据已损坏");
        }
    }

    public static String base64ToStr(byte[] b) {
        return javax.xml.bind.DatatypeConverter.printBase64Binary(b);
    }

    public static byte[] strToBase64(String str) {
        return javax.xml.bind.DatatypeConverter.parseBase64Binary(str);
    }

    public static void main(String[] args) throws Exception{
        //消息发送方
        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCaqq+BUcxIwhExmF3UTCaDpwz001rScNKgPn7Lto8dPMlV+0Ed0iB8bNdP1mti7wQ9uX0hVBIKBcrG5QI3DuL+17qm6C8Tt2qM7AgyOICWmMlJTC9asm5Ho+d3olj1Oxy47wKpARrWRIHmOONN+4iex6bwQM5NzPCbDUGHL2xfLQIDAQAB";
        String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJqqr4FRzEjCETGYXdRMJoOnDPTTWtJw0qA+fsu2jx08yVX7QR3SIHxs10/Wa2LvBD25fSFUEgoFysblAjcO4v7XuqboLxO3aozsCDI4gJaYyUlML1qybkej53eiWPU7HLjvAqkBGtZEgeY44037iJ7HpvBAzk3M8JsNQYcvbF8tAgMBAAECgYEAgBdD6pHX4Zn3q19Itf2rF/VNYaV43jfZ+CRJ1mSG+evRwoenUg8oIGPznYcywGi61nO8EdISboKFgVtADmEh1CZVG3V9uLFveF+3KodFKg58B6FX8IodK6Gh9AIs8U/6L5+UXoEdVUU79yRq4my5zlWuuxO1ZdkH7qfxitAYSHECQQDsTBFHBv4iagJ34q+wIITLlz3Sc7fhRLoAbdWSNiP3cPRsYXuXYWyaJbSsoHS/twIwASLZULgCjegGC7eLRXIPAkEAp5AokZYMCh/x8MMcPukScdV5w0bX4QCMTgCT3/JGuRlAgvz0CGlU+cN3mPe1aG/jMX/NND8vp5GCOJU+b8hnAwJACUm0JCaSNqgUEMJIRZQBpD79nxgNUj1xUjL/AhA+uasm8Fukj/60VhrFT0O/UwnOfDSPOhuCIBeT1yj+veqs4QJAFg/4F3svKKIeI/o8nNfpUs2t05VfVUPSNW8ECWeIAlUd1mFh7D2aL6yZ8a0j2JVqz5ipNyjeqGyTPcoAx40WawJAFNvv/S3ggjyyoGJ8f6JrdOh2U5+0xri7LDVmOTiSE3vQuqXipzdltQMaA+5D484LyK7UIcV7ZLPDda3uifJyXg==";
        String originData="周末约你看电影吧";
        System.out.println("信息原文："+originData);
        String encryptData=RSACrypt.encrypt(RSACrypt.loadPublicKey(pubKey),originData.getBytes());
        System.out.println("加密后："+encryptData);

        //消息接收方
        String decryptData=RSACrypt.decrypt(RSACrypt.loadPrivateKey(priKey),RSACrypt.strToBase64(encryptData));
        System.out.println("解密后："+decryptData);
    }
}
