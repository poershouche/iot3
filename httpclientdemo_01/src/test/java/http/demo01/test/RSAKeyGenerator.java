package http.demo01.test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

class RSAKeyGenerator{
    public static final String ALGORITHM = "RSA";
    public static KeyPair generatorKeyPair() throws  Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
}
