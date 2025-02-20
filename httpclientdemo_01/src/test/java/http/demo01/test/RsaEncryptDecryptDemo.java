package http.demo01.test;

import javax.crypto.Cipher;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RsaEncryptDecryptDemo {
    public static String encrypt(String plainText , PublicKey publicKey) throws Exception{
        Cipher cipher = Cipher.getInstance(RSAKeyGenerator.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] bytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }
    public static String decrypt(String encodeText , PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance(RSAKeyGenerator.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(encodeText.getBytes(StandardCharsets.UTF_8)));
        return new String(bytes);
    }
}

class TEST{
    public static void main(String[] args) throws Exception{
        KeyPair keyPair = RSAKeyGenerator.generatorKeyPair();
        PublicKey aPublic = keyPair.getPublic();
        PrivateKey aPrivate = keyPair.getPrivate();
//        System.out.println("公钥："+aPublic);
//        System.out.println("私钥："+aPrivate);
        String plainText="wangwu";
        String encrypt = RsaEncryptDecryptDemo.encrypt(plainText, aPublic);
        System.out.println(encrypt);
        Thread.sleep(1000);
        String decrypt = RsaEncryptDecryptDemo.decrypt(encrypt, aPrivate);
        System.out.println(decrypt);

      /*  Class<RsaEncryptDecryptDemo> aClass = RsaEncryptDecryptDemo.class;
        KeyPair keyPair = RSAKeyGenerator.generatorKeyPair();
        PublicKey aPublic = keyPair.getPublic();
        Object rsa = aClass.getDeclaredConstructor().newInstance();
        Method encrypt = aClass.getDeclaredMethod("encrypt", String.class, PublicKey.class);
        System.out.println(encrypt.invoke(rsa, "wangwu", aPublic));*/


    }
}