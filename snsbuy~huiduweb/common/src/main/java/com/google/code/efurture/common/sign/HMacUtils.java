package com.google.code.efurture.common.sign;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class HMacUtils {

	

	   public static String hexHMacMd5(String key, String message)
		{
		  String lSignature = "None";
		  try
		  {
		    Mac lMac = Mac.getInstance("HmacMD5");
		    SecretKeySpec lSecret = new SecretKeySpec(key.getBytes(), "HmacMD5");
		    lMac.init(lSecret);

		    byte[] lDigest = lMac.doFinal(message.getBytes());
		    BigInteger lHash = new BigInteger(1, lDigest);
		    lSignature = lHash.toString(16);
		    if ((lSignature.length() % 2) != 0) {
		      lSignature = "0" + lSignature;
		    }
		  }
		  catch (NoSuchAlgorithmException lEx)
		  {
		    throw new RuntimeException("Problems calculating HMAC", lEx);
		  }
		  catch (InvalidKeyException lEx)
		  {
		     throw new RuntimeException("Problems calculating HMAC", lEx);
		  }
		  return lSignature;
		}
	   
	   
	   public static String hexHMacSha256(String key, String message)
		{
		  String lSignature = "None";
		  try
		  {
		    Mac lMac = Mac.getInstance("HmacSHA256");
		    SecretKeySpec lSecret = new SecretKeySpec(key.getBytes(), "HmacSHA256");
		    lMac.init(lSecret);

		    byte[] lDigest = lMac.doFinal(message.getBytes());
		    BigInteger lHash = new BigInteger(1, lDigest);
		    lSignature = lHash.toString(16);
		    if ((lSignature.length() % 2) != 0) {
		         lSignature = "0" + lSignature;
		    }
		  }
		  catch (NoSuchAlgorithmException lEx)
		  {
		    throw new RuntimeException("Problems calculating HMAC", lEx);
		  }
		  catch (InvalidKeyException lEx)
		  {
		     throw new RuntimeException("Problems calculating HMAC", lEx);
		  }
		  return lSignature;
		}
	   
	   
	   public static String base64HMacSha256(String key, String message)
		{
		  String lSignature =  null;
		  try
		  {
		    Mac lMac = Mac.getInstance("HmacSHA256");
		    SecretKeySpec lSecret = new SecretKeySpec(key.getBytes(), "HmacSHA256");
		    lMac.init(lSecret);

		    byte[] lDigest = lMac.doFinal(message.getBytes());
		    lSignature = Base64.encodeBase64String( lDigest);
		  }
		  catch (Exception e)
		  { 
		      e.printStackTrace();  
		   }
		   return lSignature;
		}


	   
	   public static void main(String [] args){
		   
		   System.out.println(System.currentTimeMillis());
		   String secret = "test";
		   String appKey =  "12008678";
		   String timestamp = "1222";
		   String message =  secret + "app_key" + appKey + "timestamp" + timestamp  + secret;
		   System.out.println(message);
		   System.out.println( hexHMacMd5(secret, message));
		  //O7huwnkIsZch0Uv/Ks+jOChMRKEJjerYWa1l4lxxc98=
		  System.out.println( base64HMacSha256("love", "love中国"));
	   }
	   
}
