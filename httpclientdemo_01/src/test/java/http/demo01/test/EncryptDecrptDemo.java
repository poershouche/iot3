package http.demo01.test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 统一门户 加解密 ECB /CBC
 */
public class EncryptDecrptDemo {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding"; // CBC模式
    private static final String TRANSFORMATION_ECB = "AES/ECB/PKCS5Padding"; // EBC模式

    /**
     * 加密
     */
    public static String encrypt(String plainText, String secretKey, String iv) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }


    /**
     * 解密
     */
    public static String decrypt(String encryptedText, String secretKey, String iv) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     *
     * @param plaintext
     * @param secretKey
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static  String  encryptEcb(String plaintext ,String secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION_ECB);
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        byte[] bytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);

    }

    public static String decryptEcb(String encryptText , String secretKye) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKye.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher instance = Cipher.getInstance(TRANSFORMATION_ECB);
        instance.init(Cipher.DECRYPT_MODE,secretKeySpec);
        byte[] bytes = instance.doFinal(Base64.getDecoder().decode(encryptText));
        return new String(bytes,StandardCharsets.UTF_8);
    }


    public static void main(String[] args) {
        /*try {
            String secretKey = "fd34f3gdf78kju43"; // 16字节密钥
            String iv = "abcdefghijklmnop"; // 16字节初始化向量
            String plainText = "bG9jYWxob3N0ODA4MUFjdHVhdG9yaGVhbHRo!";

            // 加密
            String encryptedText = encrypt(plainText, secretKey, iv);
            System.out.println("Encrypted Text: " + encryptedText);

            // 解密
            String decryptedText = decrypt(encryptedText, secretKey, iv);
            System.out.println("Decrypted Text: " + decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        try {
            String secretKey = "fd34f3gdf78kju43";
            String plainText = "bG9jYWxob3N0ODA4MUFjdHVhdG9yaGVhbHRo!";

            // 加密
            String encryptEcb = encryptEcb(plainText, secretKey);
            System.out.println(encryptEcb);

            Thread.sleep(3000);
            // 解密
            String s = decryptEcb(encryptEcb, secretKey);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

