package com.miscot.springmvc.service;



import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.miscot.springmvc.helper.HmacAadhar;
import com.miscot.springmvc.helper.RTREQ;
import com.miscot.springmvc.helper.SkeyAadhar;
import com.miscot.springmvc.helper.Store;




@Service
public class EncryptionImpl implements EncryptionInterface{
	@Autowired
	AESCipherImpl aesCipher;
	@Autowired
	 RSAImpl RSA;
	
	
	@Override
	public void EncryptAadharVault(String ts, String aadharData, String encryptioncer, SkeyAadhar skey, Store store,
			HmacAadhar hmac, byte[] sessionK, String Password) {
		byte[] srcHash=null;
   
try{
	byte[] bytes = aadharData.getBytes("UTF-8");
    srcHash = aesCipher.generateHash(bytes);
    byte[] cipherTextWithTS = aesCipher.encrypt(bytes, sessionK, ts);
    store.innerText =Base64.encodeBase64String(cipherTextWithTS);
    String rsaPubKey=RSAImpl.getKeyPem(encryptioncer);
	java.security.interfaces.RSAPublicKey publicKey=RSAImpl.getPublicKeyFromString(rsaPubKey);
	String sessionKeyenc=RSAImpl.encrypt(sessionK, publicKey);
	skey.setCi("20201030");
	skey.setInnerText(sessionKeyenc);
}
catch(Exception e)
{
	e.printStackTrace();
}
	
	byte[] hmacTextWithTS=null;
	
	try {
		hmacTextWithTS = aesCipher.encryptwithoutts(srcHash, sessionK, ts);
	    hmac.innerText = Base64.encodeBase64String(hmacTextWithTS);
	} catch (IllegalStateException e) {
		e.printStackTrace();
	} catch (InvalidCipherTextException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	
	@Override
	public String EncryptRetrivalAadharVault(String ts, String aadharData, String encryptioncer, SkeyAadhar skey, RTREQ store,
			HmacAadhar hmac, byte[] sessionK, String Password) {
		byte[] srcHash=null,cipherTextWithTS=null;
try{
	byte[] bytes = aadharData.getBytes("UTF-8");
    srcHash = aesCipher.generateHash(bytes);
    cipherTextWithTS = aesCipher.encrypt(bytes, sessionK, ts);
    //store.innerText =Base64.encodeBase64String(cipherTextWithTS);
    String rsaPubKey=RSAImpl.getKeyPem(encryptioncer);
	java.security.interfaces.RSAPublicKey publicKey=RSAImpl.getPublicKeyFromString(rsaPubKey);
	String sessionKeyenc=RSAImpl.encrypt(sessionK, publicKey);
	skey.setCi("20201030");
	skey.setInnerText(sessionKeyenc);
}
catch(Exception e)
{
	e.printStackTrace();
}
	byte[] hmacTextWithTS=null;
	try {
		hmacTextWithTS = aesCipher.encryptwithoutts(srcHash, sessionK, ts);
	    hmac.innerText = Base64.encodeBase64String(hmacTextWithTS);
	} catch (IllegalStateException e) {
		e.printStackTrace();
	} catch (InvalidCipherTextException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return Base64.encodeBase64String(cipherTextWithTS);
}
}
