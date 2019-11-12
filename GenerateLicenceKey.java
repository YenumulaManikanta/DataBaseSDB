package com.miscot.springmvc.helper;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;
@Component
public class GenerateLicenceKey {
/*public static void main(String[] args){
	GenerateLicenceKey gen=new GenerateLicenceKey();
	System.out.println(gen.lk("Girishkumar K K"));
	//gen.validateLK("Shakti","6916-8ad1b-cdbe-3f1f5");
	//gen.validateLK("Shakti","6916-8ad1b-cdbe-3f1e5");
} */

public String lk(String strLK){
	strLK+="M$IOB$"+strLK;
	String serialNumber = "";
	try {
		String serialNumberEncoded = calculateSecurityHash(strLK,"MD2") +
			    calculateSecurityHash(strLK,"MD5") +
			        calculateSecurityHash(strLK,"SHA1");
		//System.out.println(serialNumberEncoded);
		
		 serialNumber = ""
			    + serialNumberEncoded.charAt(32)  + serialNumberEncoded.charAt(76)
			    + serialNumberEncoded.charAt(100) + serialNumberEncoded.charAt(50) + "-"
			    + serialNumberEncoded.charAt(2)   + serialNumberEncoded.charAt(91)
			    + serialNumberEncoded.charAt(73)  + serialNumberEncoded.charAt(72)
			    + serialNumberEncoded.charAt(98)  + "-"
			    + serialNumberEncoded.charAt(47)  + serialNumberEncoded.charAt(65)
			    + serialNumberEncoded.charAt(18)  + serialNumberEncoded.charAt(85) + "-"
			    + serialNumberEncoded.charAt(27)  + serialNumberEncoded.charAt(53)
			    + serialNumberEncoded.charAt(102) + serialNumberEncoded.charAt(15)
			    + serialNumberEncoded.charAt(99);
		//System.out.println(serialNumber);
		
		
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return serialNumber;
}
public String validateLK(String strLK,String serialNumber){
	String result="";
	try {
		GenerateLicenceKey registrationAppSerialGenerationReversal=new GenerateLicenceKey();
		String serialNumberEncoded =
			    registrationAppSerialGenerationReversal.calculateSecurityHash(strLK,"MD2")
			    + registrationAppSerialGenerationReversal.calculateSecurityHash(strLK,"MD5")
			    + registrationAppSerialGenerationReversal.calculateSecurityHash(strLK,"SHA1");

			String serialNumberCalc = ""
			    + serialNumberEncoded.charAt(32)  + serialNumberEncoded.charAt(76)
			    + serialNumberEncoded.charAt(100) + serialNumberEncoded.charAt(50) + "-"
			    + serialNumberEncoded.charAt(2)   + serialNumberEncoded.charAt(91)
			    + serialNumberEncoded.charAt(73)  + serialNumberEncoded.charAt(72)
			    + serialNumberEncoded.charAt(98)  + "-" + serialNumberEncoded.charAt(47)
			    + serialNumberEncoded.charAt(65)  + serialNumberEncoded.charAt(18)
			    + serialNumberEncoded.charAt(85)  + "-" + serialNumberEncoded.charAt(27)
			    + serialNumberEncoded.charAt(53)  + serialNumberEncoded.charAt(102)
			    + serialNumberEncoded.charAt(15)  + serialNumberEncoded.charAt(99);

			if (serialNumber.equals(serialNumberCalc)){
				System.out.println("Serial MATCH");
				result="M";
			}
			else{
				System.out.println("Serial MIS-MATCH");
				result="N";
			}
			    
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return result;
}
private String calculateSecurityHash(String stringInput, String algorithmName)
	    throws java.security.NoSuchAlgorithmException {
	        String hexMessageEncode = "";
	        byte[] buffer = stringInput.getBytes();
	        java.security.MessageDigest messageDigest =
	            java.security.MessageDigest.getInstance(algorithmName);
	        messageDigest.update(buffer);
	        byte[] messageDigestBytes = messageDigest.digest();
	        for (int index=0; index < messageDigestBytes.length ; index ++) {
	            int countEncode = messageDigestBytes[index] & 0xff;
	            if (Integer.toHexString(countEncode).length() == 1)
	                hexMessageEncode = hexMessageEncode + "0";
	            hexMessageEncode = hexMessageEncode + Integer.toHexString(countEncode);
	        }
	        return hexMessageEncode;
	    }
}
