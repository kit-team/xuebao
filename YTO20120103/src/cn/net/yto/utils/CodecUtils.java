package cn.net.yto.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;



public class CodecUtils {
	public static String encodeBase64(String s){
		byte[] source = s.getBytes();
		//return Base64.encodeBase64String(s.getBytes());
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// md5.update(source);
		byte[] d = md5.digest(source);
		
		// BASE64Encoder base64en = new BASE64Encoder();
		String result = new String(Base64.encodeBase64(d)); // base64en.encode(d);
		//return result.toCharArray();
		return result;

	//	return Base64Util.encode(s);
	}
	
	public static String encode32BitMd5(String s){
		return Md5Util.md5(s).substring(8, 24);
		//return DigestUtils.md5Hex(s.getBytes()).substring(8, 24);
	}
	
	public static String encode64BitMd5(String s){
		//return DigestUtils.md5Hex(s.getBytes());
		return Md5Util.md5(s);
	}
}
