package com.miscot.springmvc.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.bouncycastle.crypto.InvalidCipherTextException;

public interface AESCipherInterface {

	byte[] encryptDecryptUsingSessionKey(boolean cipherOperation, byte[] skey, byte[] iv, byte[] aad, byte[] data)
			throws IllegalStateException, InvalidCipherTextException;

	byte[] generateSessionKey() throws NoSuchAlgorithmException, NoSuchProviderException;

	String getCurrentISOTimeInUTF8();

	byte[] generateIv(String ts) throws UnsupportedEncodingException;

	byte[] generateAad(String ts) throws UnsupportedEncodingException;

	byte[] getLastBits(String ts, int bits) throws UnsupportedEncodingException;

	String getEncrypted(byte[] inputData, String ts, byte[] sessionKey) throws Exception;

	String getDecrypted(byte[] cipherTextWithTS, byte[] sessionKey, byte[] encSrcHash) throws Exception;

	String getTS() throws Exception;

	String getASDF() throws Exception;

	String byteArrayToHexString(byte[] bytes);

	byte[] hexStringToByteArray(String data);

	byte[] encrypt(byte[] inputData, byte[] sessionKey, String ts)
			throws IllegalStateException, InvalidCipherTextException, Exception;

	byte[] encryptwithoutts(byte[] inputData, byte[] sessionKey, String ts)
			throws IllegalStateException, InvalidCipherTextException, Exception;

	byte[] decrypt(byte[] inputData, byte[] sessionKey, byte[] encSrcHash)
			throws IllegalStateException, InvalidCipherTextException, Exception;

	boolean validateHash(byte[] srcHash, byte[] plainTextWithTS) throws Exception;

	byte[] generateHash(byte[] message) throws Exception;

	String getActualDecryptedData(byte[] cipherTextWithTS, byte[] sessionKey, byte[] encSrcHash) throws Exception;

}
