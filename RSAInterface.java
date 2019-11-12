package com.miscot.springmvc.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public interface RSAInterface {

	PublicKey getKey(String filename) throws IOException;

	RSAPrivateKey getPrivateKeyRetirval(String filename) throws IOException, GeneralSecurityException;

	String getPrivateKeyRetiricved(String filename) throws IOException;

	RSAPrivateKey getPrivateKey(String filename) throws IOException, GeneralSecurityException;

	RSAPublicKey getPublicKey(String filename) throws IOException, GeneralSecurityException;

}
