package com.miscot.springmvc.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

//import org.apache.tomcat.util.codec.binary.Base64;
//import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miscot.springmvc.configuration.PropertiesPath;
import com.miscot.springmvc.repository.DBUtil;
//import com.connection.DBUtil;
import com.sun.xml.internal.fastinfoset.Encoder;

import sun.misc.BASE64Encoder;
@Service
public class AESCipherImpl implements AESCipherInterface {

	 // AES-GCM parameters
	
		// AES Key size - in bits
	    public static final int AES_KEY_SIZE_BITS = 256; 
	    
	    // IV length - last 96 bits of ISO format timestamp
	    public static final int IV_SIZE_BITS = 96;  
	    
	    // Additional authentication data - last 128 bits of ISO format timestamp 
	    public static final int AAD_SIZE_BITS = 128; 
	    
	    // Authentication tag length - in bits
	    public static final int AUTH_TAG_SIZE_BITS = 128; 
	    
	    private static final String JCE_PROVIDER = "BC";

	    /**
		 * Hashing Algorithm Used for encryption and decryption
		 */
	    private String algorithm = "SHA-256";
	    
	    /**
		 * SHA-256 Implementation provider
		 */
		private final static String SECURITY_PROVIDER = "BC";
		
		/**
		 * Default Size of the HMAC/Hash Value in bytes
		 */
		private int HMAC_SIZE = 32;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    
  
    /**
     * Encrypts given data using session key, iv, aad
     * @param cipherOperation - true for encrypt, false otherwise
     * @param skey	- Session key
     * @param iv  	- initialization vector or nonce
     * @param aad 	- additional authenticated data
     * @param data 	- data to encrypt
     * @return encrypted data
     * @throws IllegalStateException
     * @throws InvalidCipherTextException
     */
    @Override
    public byte[] encryptDecryptUsingSessionKey(boolean cipherOperation, byte[] skey, byte[] iv, byte[] aad,
			byte[] data) throws IllegalStateException, InvalidCipherTextException {

		AEADParameters aeadParam = new AEADParameters(new KeyParameter(skey), AUTH_TAG_SIZE_BITS, iv, aad);
		GCMBlockCipher gcmb = new GCMBlockCipher(new AESEngine());

		gcmb.init(cipherOperation, aeadParam);
		int outputSize = gcmb.getOutputSize(data.length);
		byte[] result = new byte[outputSize];
		int processLen = gcmb.processBytes(data, 0, data.length, result, 0);
		gcmb.doFinal(result, processLen);

		return result;
	}
	
	
	/**
	 * Creates a AES key that can be used as session key (skey)
	 * @return session key byte array 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
    @Override
	public byte[] generateSessionKey() throws NoSuchAlgorithmException, NoSuchProviderException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES", JCE_PROVIDER);
		kgen.init(AES_KEY_SIZE_BITS);
		SecretKey key = kgen.generateKey();
		byte[] symmKey = key.getEncoded();
		return symmKey;
	}

	
	/**
	 * Get current ISO time 
	 * @return current time in String
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
@Override
	public String getCurrentISOTimeInUTF8() {
		SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss"); 
		String timeNow = df.format(new Date());
		return timeNow;
	}

	
	/**
	 * Generate IV using timestamp 
	 * @param ts - timestamp string
	 * @return 12 bytes array
	 * @throws UnsupportedEncodingException
	 */
@Override
	public byte[] generateIv(String ts) throws UnsupportedEncodingException {
		return getLastBits(ts, IV_SIZE_BITS / 8);
	}

	
	/**
	 * Generate AAD using timestamp
	 * @param ts - timestamp string
	 * @return 16 bytes array
	 * @throws UnsupportedEncodingException
	 */
@Override
	public byte[] generateAad(String ts) throws UnsupportedEncodingException {
		return getLastBits(ts, AAD_SIZE_BITS / 8);
	}

	
	/**
	 * Fetch specified last bits from String
	 * @param ts - timestamp string 
	 * @param bits - no of bits to fetch
	 * @return byte array of specified length
	 * @throws UnsupportedEncodingException
	 */
@Override
	public byte[] getLastBits(String ts, int bits) throws UnsupportedEncodingException {
		byte[] tsInBytes = ts.getBytes("UTF-8");
		return Arrays.copyOfRange(tsInBytes, tsInBytes.length - bits, tsInBytes.length);
	}
    
	
	/**
	 * Main method
	 * @param args
	 * @throws Exception
	 */
  /*  public static void main(String[] args) throws Exception {
    	AESCipherImpl aesCipher = new AESCipherImpl();
        byte[] inputData = "UIDAI World!".getBytes();
        byte[] sessionKey = aesCipher.generateSessionKey();

        String ts = aesCipher.getCurrentISOTimeInUTF8();
        
        System.out.println("Plain text Hex  ---> "+byteArrayToHexString(inputData));
        
        byte[] cipherTextWithTS = aesCipher.encrypt(inputData, sessionKey, ts);
        System.out.println("Cipher text Hex ---> "+byteArrayToHexString(cipherTextWithTS));
        
//        ---------------------------------------------------------
        
        byte[] srcHash = aesCipher.generateHash(inputData);
        System.out.println("source Hash in Hex ---> "+byteArrayToHexString(srcHash));
        byte[] iv = aesCipher.generateIv(ts);
        byte[] aad = aesCipher.generateAad(ts);
        byte[] encSrcHash = aesCipher.encryptDecryptUsingSessionKey(true, sessionKey, iv, aad, srcHash);
        System.out.println("encrypted Hash Cipher text Hex ---> "+byteArrayToHexString(encSrcHash));
        
        byte[] decryptedText = aesCipher.decrypt(cipherTextWithTS, sessionKey, encSrcHash);
        System.out.println("Decrypted Plain text Hex  ---> "+byteArrayToHexString(decryptedText));
        
    }
    */
    
@Override
    public String getEncrypted(byte[] inputData,String ts, byte[] sessionKey) throws Exception{
    	System.out.println("Plain text Hex  ---> "+byteArrayToHexString(inputData));
        byte[] cipherTextWithTS = encrypt(inputData, sessionKey, ts);
        System.out.println("Cipher text Hex ---> "+byteArrayToHexString(cipherTextWithTS));
    	
        byte[] srcHash = generateHash(inputData);
        System.out.println("source Hash in Hex ---> "+byteArrayToHexString(srcHash));
        byte[] iv = generateIv(ts);
        byte[] aad = generateAad(ts);
        byte[] encSrcHash = encryptDecryptUsingSessionKey(true, sessionKey, iv, aad, srcHash);
        System.out.println("encrypted Hash Cipher text Hex ---> "+byteArrayToHexString(encSrcHash));
        
        return byteArrayToHexString(cipherTextWithTS)+"~~~~~"+ byteArrayToHexString(encSrcHash);
    	
    }
@Override
    public String getDecrypted(byte[] cipherTextWithTS, byte[] sessionKey , byte[] encSrcHash) throws Exception{
    	
        
        byte[] decryptedText = decrypt(cipherTextWithTS, sessionKey, encSrcHash);
        System.out.println("Decrypted Plain text Hex  ---> "+byteArrayToHexString(decryptedText));
        return  new String( decryptedText, "UTF-8");
    	//return byteArrayToHexString(decryptedText);
    }
@Override
    public String getTS() throws Exception{
    	AESCipherImpl aesCipher = new AESCipherImpl();
    	String ts = aesCipher.getCurrentISOTimeInUTF8();
        return ts;
    }
@Override
    public String getASDF() throws Exception{
    	AESCipherImpl aesCipher = new AESCipherImpl();
        byte[] inputData = "UIDAI World!".getBytes();
        byte[] sessionKey = aesCipher.generateSessionKey();

        String ts = aesCipher.getCurrentISOTimeInUTF8();
        
        System.out.println("Plain text Hex  ---> "+byteArrayToHexString(inputData));
        
        byte[] cipherTextWithTS = aesCipher.encrypt(inputData, sessionKey, ts);
        System.out.println("Cipher text Hex ---> "+byteArrayToHexString(cipherTextWithTS));
        
//        ---------------------------------------------------------
        
        byte[] srcHash = aesCipher.generateHash(inputData);
        System.out.println("source Hash in Hex ---> "+byteArrayToHexString(srcHash));
        byte[] iv = aesCipher.generateIv(ts);
        byte[] aad = aesCipher.generateAad(ts);
        byte[] encSrcHash = aesCipher.encryptDecryptUsingSessionKey(true, sessionKey, iv, aad, srcHash);
        System.out.println("encrypted Hash Cipher text Hex ---> "+byteArrayToHexString(encSrcHash));
        
        byte[] decryptedText = aesCipher.decrypt(cipherTextWithTS, sessionKey, encSrcHash);
        System.out.println("Decrypted Plain text Hex  ---> "+byteArrayToHexString(decryptedText));
        return byteArrayToHexString(decryptedText);
    }
    /**
     * Convert byte array to hex string
     * @param bytes - input bytes
     * @return - hex string
     */
@Override
    public  String byteArrayToHexString(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			result.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return result.toString();
	}
     
    
    /**
     * Convert hex string to byte array
     * @param data  - input hex string
     * @return byte array
     */
@Override
	public byte[] hexStringToByteArray(String data) {
		int k = 0;
		byte[] results = new byte[data.length() / 2];
		for (int i = 0; i < data.length();) {
			results[k] = (byte) (Character.digit(data.charAt(i++), 16) << 4);
			results[k] += (byte) (Character.digit(data.charAt(i++), 16));
			k++;
		}
		return results;
	}
	
	/**
     * Encrypts given data using a generated session and used ts as for all other needs.
     * @param inputData - data to encrypt
     * @param sessionKey  - Session key
     * @param ts - timestamp as per the PID
     * @return encrypted data
     * @throws IllegalStateException
     * @throws InvalidCipherTextException
	 * @throws Exception 
     */    
@Override
	public byte[] encrypt(byte[] inputData, byte[] sessionKey, String ts) throws IllegalStateException, InvalidCipherTextException, Exception {
		byte[] iv = this.generateIv(ts);
        byte[] aad = this.generateAad(ts);
        byte[] cipherText = this.encryptDecryptUsingSessionKey(true, sessionKey, iv, aad, inputData);
        byte[] tsInBytes = ts.getBytes("UTF-8");
       
        byte [] packedCipherData = new byte[cipherText.length + tsInBytes.length];   
		System.arraycopy(tsInBytes, 0, packedCipherData, 0, tsInBytes.length);
		System.arraycopy(cipherText, 0, packedCipherData, tsInBytes.length, cipherText.length);
		return packedCipherData;
    }
	
@Override
	public byte[] encryptwithoutts(byte[] inputData, byte[] sessionKey, String ts) throws IllegalStateException, InvalidCipherTextException, Exception {
        byte[] iv = this.generateIv(ts);
        byte[] aad = this.generateAad(ts);
        byte[] cipherText = this.encryptDecryptUsingSessionKey(true, sessionKey, iv, aad, inputData);
        byte[] tsInBytes = ts.getBytes("UTF-8");
        byte [] packedCipherData = new byte[cipherText.length + tsInBytes.length];   
		return cipherText;
    }

	/**
     * Decrypts given input data using a sessionKey.
     * @param inputData - data to decrypt
     * @param sessionKey  - Session key
     * @return decrypted data
     * @throws IllegalStateException
     * @throws InvalidCipherTextException
	 * @throws Exception 
     */ 
@Override
	public byte[] decrypt(byte[] inputData, byte[] sessionKey, byte[] encSrcHash) throws IllegalStateException, InvalidCipherTextException, Exception {
		//byte[] bytesTs = Arrays.copyOfRange(inputData, 0, 17);
		byte[] bytesTs = Arrays.copyOfRange(inputData, 0, 19);
		//String ts ="20180205175752881";
		String ts= new String(bytesTs);// new String(bytesTs);//
		byte[] cipherData = Arrays.copyOfRange(inputData, bytesTs.length, inputData.length); //inputData; //Arrays.copyOfRange(inputData, bytesTs.length, inputData.length);
        byte[] iv = this.generateIv(ts);
        byte[] aad = this.generateAad(ts);
        byte[] plainText = this.encryptDecryptUsingSessionKey(false, sessionKey, iv, aad, cipherData);
        byte[] srcHash = this.encryptDecryptUsingSessionKey(false, sessionKey, iv, aad, encSrcHash);
        boolean result = this.validateHash(srcHash, plainText);
        if(!result){
        	throw new Exception( "Integrity Validation Failed : " + "The original data at client side and the decrypted data at server side is not identical");
        } else{
        	return plainText;
        }
    }
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	
	/**
	 * Returns true / false value based on Hash comparison between source and generated 
	 * @param srcHash
	 * @param plainTextWithTS
	 * @return hash value
	 * @throws Exception
	 */
    @Override
	public boolean validateHash(byte[] srcHash, byte[] plainTextWithTS) throws Exception {
		byte[] actualHash = this.generateHash(plainTextWithTS);
		if (new String(srcHash, "UTF-8").equals(new String(actualHash, "UTF-8"))) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Returns the 256 bit hash value of the message
	 * 
	 * @param message
	 *            full plain text
	 * 
	 * @return hash value
	 * @throws HashingException
	 * @throws HashingException
	 *             I/O errors
	 */
@Override
	public byte[] generateHash(byte[] message) throws Exception {
		byte[] hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm, SECURITY_PROVIDER);
			digest.reset();
			HMAC_SIZE = digest.getDigestLength();
			hash = digest.digest(message);
		} catch (GeneralSecurityException e) {
			throw new Exception(
					"SHA-256 Hashing algorithm not available");
		}
		return hash;
	}

@Override
	  public String getActualDecryptedData(byte[] cipherTextWithTS, byte[] sessionKey , byte[] encSrcHash) throws Exception{
	    	byte[] decryptedText = decrypt(cipherTextWithTS, sessionKey, encSrcHash);
	        String decryptedTextACtual= new String(decryptedText);//
			return decryptedTextACtual;
	    }
	
}

