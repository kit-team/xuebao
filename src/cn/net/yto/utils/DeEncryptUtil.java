package cn.net.yto.utils;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;

public class DeEncryptUtil {

	private static final String ALGORITHM = "AES";
	private static final String TAG = "DeEncryptUtil";

	public static byte[] encrypt(byte[] data, byte[] secret) throws Exception {
		return doEnDecrypt(data, secret, Cipher.ENCRYPT_MODE);
	}

	public static byte[] decrypt(byte[] data, byte[] secret) throws Exception {
		return doEnDecrypt(data, secret, Cipher.DECRYPT_MODE);
	}

	public static byte[] encrypt(byte[] data, String secret) throws Exception {
		Log.i(TAG, "key = " + secret);
		return doEnDecrypt(data, secret.getBytes("UTF-8"), Cipher.ENCRYPT_MODE);
	}

	public static byte[] decrypt(byte[] data, String secret) throws Exception {
		Log.i(TAG, "key = " + secret);
		return doEnDecrypt(data, secret.getBytes("UTF-8"), Cipher.DECRYPT_MODE);
	}

	private static byte[] shapeKey(byte[] secret) {
		int validkeyLen = (secret.length / 16) * 16;
		if (validkeyLen < secret.length) {
			validkeyLen += 16;
		}
		return Arrays.copyOf(secret, validkeyLen);
	}

	private static byte[] doEnDecrypt(byte[] data, byte[] secret, int mode)
			throws Exception {
		byte[] mykey = shapeKey(secret);
		IvParameterSpec zeroIv = new IvParameterSpec(new byte[16]);
		SecretKeySpec key = new SecretKeySpec(mykey, ALGORITHM);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(mode, key, zeroIv);
		return cipher.doFinal(data);
	}

	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static String changStr(String srcStr) {
		if (null == srcStr || srcStr.isEmpty()) {
			return null;
		}
		if (srcStr.length() == 1) {
			return srcStr;
		}
		int pre = 0;
		int next = 0;
		char tmpChar = 0;
		StringBuffer buffer = new StringBuffer(srcStr.length());
		for (int i = 0; i < srcStr.length(); ++i) {
			pre = (i - 1 >= 0) ? (i - 1) : srcStr.length() - 1;
			next = (i + 1 >= srcStr.length()) ? 0 : (i + 1);
			if (srcStr.charAt(pre) > srcStr.charAt(i)) {
				if (srcStr.charAt(i) >= srcStr.charAt(next)) {
					tmpChar = srcStr.charAt(i);
				} else if (srcStr.charAt(pre) >= srcStr.charAt(next)) {
					tmpChar = srcStr.charAt(next);
				} else {
					tmpChar = srcStr.charAt(pre);
				}
			} else {
				if (srcStr.charAt(pre) >= srcStr.charAt(next)) {
					tmpChar = srcStr.charAt(pre);
				} else if (srcStr.charAt(next) >= srcStr.charAt(i)) {
					tmpChar = srcStr.charAt(i);
				} else {
					tmpChar = srcStr.charAt(next);
				}
			}
			buffer.append(tmpChar);
		}
		return buffer.substring(buffer.length() / 2, buffer.length())
				+ buffer.substring(0, buffer.length() / 2);

	}
}
