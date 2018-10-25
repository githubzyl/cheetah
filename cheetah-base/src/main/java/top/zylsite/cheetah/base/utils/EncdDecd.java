package top.zylsite.cheetah.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Description: 加解密工具类
 * @author jason
 * 2018年10月25日
 * @version 1.0
 */
public class EncdDecd {
	private static String key = "MIIBUgIBADANBgkqhkiG9w0BAQEFAASCATwwggE4AgEAAkEAmrQOnp8Rn0sRVP/8D024fTtAFx0BoyCyvBL65mOh+7WETWiF0suOp8YkZk/ns0p1AwkVwG8d+uDwddwrS41I6wIBEQJAOya6S7VKgKvC3Lw6uo6obB4vF+VxkTImONoUo2JUheLsxCwR1uNhijluDe0Pw3Q43dX6LHl4JHvx6fHlEvilhQIhANcdLBrsdk8ZohgxCGWbM99DE3qu/QDj/CNG2+C0HNZ3AiEAuBt6Y6CgGRDwTtXHzwU1Y3vF+RNIHfKM8gY438hImi0CIQCX2FteTI+/XWNcXtjAMVHKxe+h5O7Tc8EJ16pEQuct+QIgIH1R1ViUuSEbWTTI6Ew2mRXXpGzQfcFkKrXNzSNYGzUCID4CJH2+6BuWLSTX2h+i7y8eOUE5vEqrswpEjK4PZSj6";

	public static String EncodeBase64(byte[] data){
		return Base64.getEncoder().encodeToString(data);
	}
	
	public static String EncodeBase64(String str) {
		return EncodeBase64(str.getBytes());
	}
	

	public static String EncodeBase64(File file) {
		String result = null;
		try {
			FileInputStream in = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			in.read(buffer);
			in.close();
			result = EncodeBase64(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public static String DecodeBase64(String str) {
		String result = new String(Base64.getDecoder().decode(str));
		return result;
	}

	public static void DecodeBase64(String str, String outFile) {
		try {
			File file = new File(outFile);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			out.write(Base64.getDecoder().decode(str));
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String buffer2hex(byte[] buffer) {
		String result = null;
		StringBuffer sb = new StringBuffer("");
		int i;
		for (int offset = 0; offset < buffer.length; offset++) {
			i = buffer[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				sb.append("0");
			sb.append(Integer.toHexString(i));
		}
		result = sb.toString();
		result = result.toUpperCase();
		return result;
	}

	private static byte[] hex2buffer(byte[] buffer) {

		if ((buffer.length % 2) != 0)

			throw new IllegalArgumentException("长度不是偶数");

		byte[] b2 = new byte[buffer.length / 2];

		for (int n = 0; n < buffer.length; n += 2) {

			String item = new String(buffer, n, 2);

			b2[n / 2] = (byte) Integer.parseInt(item, 16);

		}

		return b2;
	}
	
	public static byte[] intToByteArray(int i) {  
	    return new byte[] { 
	    		(byte) (i & 0xFF) 	,
	    		(byte) ((i >> 8) & 0xFF), 
	    		(byte) ((i >> 16) & 0xFF),
	    		(byte) ((i >> 24) & 0xFF) 
	    };  
	}  
	
	public static String MD5String(byte[] buffer){
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(buffer);
			result = buffer2hex(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;		
	}
	
	public static String MD5String(ByteBuffer buffer){
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(buffer);
			result = buffer2hex(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;		
	}
	
	
	public static String MD5String(String s) {
		return MD5String(s.getBytes());

	}
	

	public static String MD5String(int i) {
		return MD5String(intToByteArray(i));

	}
	

	public static String MD5String(File file) throws FileNotFoundException, IOException{
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer ByteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		in.close();
		return MD5String(ByteBuffer);
	}
	

	private static String Des(String data, String key, int opmode) {
		String algorithm = "DES";
		try {
			SecureRandom Random = new SecureRandom();
			DESKeySpec keySpec = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
			SecretKey securekey = keyFactory.generateSecret(keySpec);

			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(opmode, securekey, Random);
			switch (opmode) {
			case Cipher.ENCRYPT_MODE:
				// return
				// EncodeBase64(buffer2hex(cipher.doFinal(data.getBytes()))) ;
				return buffer2hex(cipher.doFinal(data.getBytes()));
			case Cipher.DECRYPT_MODE:
				// return new
				// String(cipher.doFinal(hex2buffer(Base64.getDecoder().decode(data.getBytes()))));
				return new String(cipher.doFinal(hex2buffer(data.getBytes())));
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String DesEncode(String data, String key) {
		return Des(data, key, Cipher.ENCRYPT_MODE);
	}

	public static String DesDecode(String data, String key) {
		String result = null;
		result = Des(data, key, Cipher.DECRYPT_MODE);

		return result;
	}

	public static String hsEnctypt(String data) {
		String result = DesEncode(data, MD5String(key));
		return result;
	}

	public static String hsDectypt(String data) {
		String result = DesDecode(data, MD5String(key));
		return result;
	}
}
