package net.java.efurture.reader.base64;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import net.java.efurture.reader.image.ImageUtils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.Test;



public class ImageBase64Test  extends TestCase{

	public static void main(String [] args) throws IOException{
		InputStream in = ImageBase64Test.class.getResourceAsStream("/320x320.jpg");
		byte[] bts = IOUtils.toByteArray(in);
		String  imageBase64 = Base64.encodeBase64String(bts);
		System.out.println(imageBase64  +  "\n --->  " + imageBase64.length()
				
				+ "  " + imageBase64.getBytes().length/1000.0);
		
		System.out.println("\n\n\n\n\n");
		IOUtils.closeQuietly(in);
		 in = ImageBase64Test.class.getResourceAsStream("/640x640.jpg");
		bts = IOUtils.toByteArray(in);
		 imageBase64 = Base64.encodeBase64String(bts);
		System.out.println("\n " + "\n --->  " + imageBase64.length()
				
				+ "  " + imageBase64.getBytes().length/1000.0);
		IOUtils.closeQuietly(in);
	}
	
	@Test
	public void testOne(){
	   String base = 	ImageUtils.base64ImageFromUrl("http://cued.xunlei.com/wp-content/uploads/2013/11/08.jpg");
	    System.out.println(base);
	}
	
}
