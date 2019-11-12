package com.miscot.springmvc.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.security.cert.Certificate;
import javax.security.cert.X509Certificate;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

@Service
public class RSAImpl implements RSAInterface{
	/*public static void main(String args[]){
		String cipherText="ilhshjTG3VwU0dgRGg/Nji50c803I2F6ni4SeyLUaP04iUlKKxfNSjBnWAFqDXCTu+Wl9wLGjmCONedwQLT0W2ici4OkgedIjUEyP58TaE6ctAouIH1mEkiE2l6bTt74Eoed91d3rnbbnbESy1wRQTwNJfBtRlUvK4UOcJz7mZfpUDeXnGcROq+HBnhgHH8+57KtO5qmSAlEwxLfpVf+O8ZHvuP+8qE77SYZ/0vVWl1oebt6uHuEmy/x/UiYppMNuHonlA9PIera4Q11zg9l7ild6syQVWmfsf8bP4/GyYjzhRYCwSstovsSFLzCSBQnZJBwOXT3YkPaRyB4R5ITUA==";//"VA7ZAiORTFG+DRPTQ3Y+Gz33Hq3nPpH+LDjW//CA0DJhfLyJMRnxcAMYkGn9t9ElA1lGWfWqDU2gsRRfB4Tz3+r6lH1Rv+0aYHZcbLn/ANA3JWvmLwQr2iZix99vrr9bOqwgCHqlgmVd6RL61HGPAC/kKyNdgrA0F0ukJCB8E7v8HYsmGhxY96GLlyJNlb/QXZa6Bda5GRPbCO5SslQSFWOBaJv+6dXVD4mHXZAQ3g3q6rUybmyf1YlE9DFE5nKUk4kELi0/Zw6/UZJ/32MX9E76MGikkJPJJZLnLOdpLV6kuaaiyniU4fBgrbhVSy4EzI1C2i5hyoK84CiQMgP7/w==";
		try {
			RSAPrivateKey privateKey=getPrivateKey("E:/Projects/2018/SwiftDBvault/Keys/SWIFTDBVAULTPUBkey.pem");
			System.out.println(new BASE64Encoder().encode( decrypt1(cipherText, privateKey)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	@Override
    public PublicKey getKey(String filename) throws IOException {
        // Read key from file
      /*  String strKeyPEM = "";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            strKeyPEM += line + "\n";
        }
        br.close();
        return strKeyPEM;*/
    	FileInputStream fileInputStream = null;
    	PublicKey pk=null;
		try {
			CertificateFactory f = CertificateFactory.getInstance("X.509");
			fileInputStream = new FileInputStream(new File(filename));
			java.security.cert.Certificate certificate = f.generateCertificate(fileInputStream);
			 pk = certificate.getPublicKey();
			System.out.println("Public Key is"+pk.getEncoded().toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not intialize encryption module", e);
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return pk;
    }

    /**
     * Constructs a private key (RSA) from the given file
     * 
     * @param filename PEM Private Key
     * @return RSA Private Key
     * @throws IOException
     * @throws GeneralSecurityException
     */
	@Override
    public  RSAPrivateKey getPrivateKeyRetirval(String filename) throws IOException, GeneralSecurityException {
        String privateKeyPEM = getPrivateKeyRetiricved(filename);
        return getPrivateKeyFromString(privateKeyPEM);
    }
	@Override
	 public  String getPrivateKeyRetiricved(String filename) throws IOException {
        // Read key from file
        String strKeyPEM = "";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            strKeyPEM += line + "\n";
        }
        br.close();
        return strKeyPEM;
    }
	@Override
    public RSAPrivateKey getPrivateKey(String filename) throws IOException, GeneralSecurityException {
        PublicKey privateKeyPEM = getKey(filename);
        return (RSAPrivateKey)privateKeyPEM;//getPrivateKeyFromString(privateKeyPEM);
    }
    public static RSAPrivateKey getPrivateKeyAadharVault(String filename) throws IOException, GeneralSecurityException {
        String privateKeyPEM = getPrivateKeyAadhar(filename);
        return getPrivateKeyFromString(privateKeyPEM);
    }
    private static String getPrivateKeyAadhar(String filename) throws IOException {
        // Read key from file
        String strKeyPEM = "";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            strKeyPEM += line + "\n";
        }
        br.close();
        return strKeyPEM;
    }

    /**
     * Constructs a private key (RSA) from the given string
     * 
     * @param key PEM Private Key
     * @return RSA Private Key
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static RSAPrivateKey getPrivateKeyFromString(String key) throws IOException, GeneralSecurityException {
        String privateKeyPEM = key;

        // Remove the first and last lines
        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----\n", "");
        privateKeyPEM = privateKeyPEM.replace("-----END PRIVATE KEY-----", "");

        // Base64 decode data
        byte[] encoded = Base64.decodeBase64(privateKeyPEM);

        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(encoded));
        return privKey;
    }

    /**
     * Constructs a public key (RSA) from the given file
     * 
     * @param filename PEM Public Key
     * @return RSA Public Key
     * @throws IOException
     * @throws GeneralSecurityException
     */
    @Override
    public  RSAPublicKey getPublicKey(String filename) throws IOException, GeneralSecurityException {
        PublicKey publicKeyPEM = getKey(filename);
       // return getPublicKeyFromString(publicKeyPEM);
        return (RSAPublicKey) publicKeyPEM;
    }

    /**
     * Constructs a public key (RSA) from the given string
     * 
     * @param key PEM Public Key
     * @return RSA Public Key
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static RSAPublicKey getPublicKeyFromString(String key) throws IOException, GeneralSecurityException {
        String publicKeyPEM = key;
        publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----\n", "");
        publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
        byte[] encoded = Base64.decodeBase64(publicKeyPEM);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));
        return pubKey;
    }

    /**
     * @param privateKey
     * @param message
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    public static String sign(PrivateKey privateKey, String message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        Signature sign = Signature.getInstance("SHA1withRSA");
        sign.initSign(privateKey);
        sign.update(message.getBytes("UTF-8"));
        return new String(Base64.encodeBase64(sign.sign()), "UTF-8");
    }

    /**
     * @param publicKey
     * @param message
     * @param signature
     * @return
     * @throws SignatureException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    public static boolean verify(PublicKey publicKey, String message, String signature) throws SignatureException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Signature sign = Signature.getInstance("SHA1withRSA");
        sign.initVerify(publicKey);
        sign.update(message.getBytes("UTF-8"));
        return sign.verify(Base64.decodeBase64(signature.getBytes("UTF-8")));
    }

    /**
     * Encrypts the text with the public key (RSA)
     * 
     * @param rawText Text to be encrypted
     * @param publicKey
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static String encrypt(String rawText, PublicKey publicKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(rawText.getBytes("UTF-8")));
    }
    public static String encrypt(byte[] rawText, PublicKey publicKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(rawText));
    }
    /**
     * Decrypts the text with the private key (RSA)
     * 
     * @param cipherText Text to be decrypted
     * @param privateKey
     * @return Decrypted text (Base64 encoded)
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static String decrypt(String cipherText, PrivateKey privateKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.decodeBase64(cipherText)), "UTF-8");
    }
    
    public static byte[] decrypt1(String cipherText, PrivateKey privateKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(Base64.decodeBase64(cipherText));
    }
    ///
    public static String getKeyPem(String filename) throws IOException {
        String strKeyPEM = "";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            strKeyPEM += line + "\n";
        }
        br.close();
        return strKeyPEM;
    }
}
