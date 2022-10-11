package com.simple.poll.config.security;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class JwtUtils {

    public static String convertPublicKey(final PublicKey publicKey) {
        byte[] byte_pubkey = publicKey.getEncoded();
        String str_key = Base64.getEncoder().encodeToString(byte_pubkey);
        return str_key;
    }

    public static PublicKey convertPublicKey(final String strKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] byte_pubkey = Base64.getDecoder().decode(strKey);

        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(byte_pubkey));
        return publicKey;
    }

    public static String convertPrivateKey(final PrivateKey publicKey) {
        byte[] byteKey = publicKey.getEncoded();
        String strKey = Base64.getEncoder().encodeToString(byteKey);
        return strKey;
    }

    public static PrivateKey convertPrivateKey(final String strKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] byteKey = Base64.getDecoder().decode(strKey);

        PrivateKey publicKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(byteKey));
        return publicKey;
    }
    
    public static PublicKey getPublicKeyByX5c(String x5c) throws CertificateException {
        byte[] x5c0Bytes = Base64.getDecoder().decode(x5c);
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        X509Certificate cer = (X509Certificate) fact.generateCertificate(new ByteArrayInputStream(x5c0Bytes));
        return cer.getPublicKey();
    }
    
}
