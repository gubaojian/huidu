package net.java.mapyou.device;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

public class TokenUtils {

	
	public static String locationToken(){
		String rand = RandomStringUtils.randomAlphanumeric(32);
		return DigestUtils.md5Hex( UUID.randomUUID().toString() + rand);
	}
	
	public static String trackToken(){
		String rand = RandomStringUtils.randomAlphanumeric(32);
		String prefix = UUID.randomUUID().toString().replaceAll("-", "");
		return prefix + DigestUtils.md5Hex( UUID.randomUUID().toString() + rand);
	}
	
	public static String token(){
		return DigestUtils.md5Hex(RandomStringUtils.randomAlphanumeric(16) + UUID.randomUUID().toString() + RandomStringUtils.randomAlphanumeric(16));
	}
	
	public static void main(String [] args){
		System.out.println(locationToken());
		System.out.println(trackToken());
		
	}
}
