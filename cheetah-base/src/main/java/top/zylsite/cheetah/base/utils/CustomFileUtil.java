package top.zylsite.cheetah.base.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

/**
 * 文件工具类
 * 
 * @author: zhaoyl
 * @since: 2017年6月6日 下午3:28:18
 * @history:
 */
public class CustomFileUtil {

	// 创建临时文件
	public static File createTempFile(String prefix, String suffix, String fileDir) throws IOException {
		File file = new File(fileDir + "/" + prefix + suffix);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	public static String getFileDir(Environment env, String path) {
		String dirPath = env.getProperty(path);
		return getFileDir(dirPath);
	}

	public static String getFileDir(String dirPath) {
		File fileDir = null;
		if (StringUtils.isNotBlank(dirPath)) {
			fileDir = new File(dirPath);
			// 如果文件夹不存在则创建
			if (!fileDir.exists() && !fileDir.isDirectory()) {
				fileDir.mkdirs();
			}
		}
		return dirPath;
	}

	public static void downloadFile(String fileName, String filePath, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		download(fileName, new FileInputStream(filePath), request, response);
	}

	public static void downloadFile(String fileName, FileInputStream fileInputStream, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		download(fileName, fileInputStream, request, response);
	}

	public static void downloadFile(String fileName, byte[] bytes, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initDownloadParams(fileName, request, response);
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		try {
			bos.write(bytes);
		} finally {
			closeStream(bos);
		}
	}

	public static void showPdfToPage(String pdfFilePath, HttpServletResponse response) {
		try {
			showPdfToPage(new FileInputStream(pdfFilePath), response);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 向页面展示pdf
	public static void showPdfToPage(FileInputStream fileInputStream, HttpServletResponse response) {
		try {
			response.setContentType("application/pdf");
			DataOutputStream temps = new DataOutputStream(response.getOutputStream());
			DataInputStream in = new DataInputStream(fileInputStream);
			byte[] b = new byte[2048];
			while ((in.read(b)) != -1) {
				temps.write(b);
				temps.flush();
			}
			in.close();
			temps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showHtmlToPage(File file, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String html = readFileToStr(file, "utf-8");
			out.write(html);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void download(InputStream inputStream, HttpServletResponse response) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());

		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(inputStream);
			byte[] b = new byte[1024];
			int bytes;
			while (-1 != (bytes = bis.read(b, 0, b.length))) {
				bos.write(b, 0, bytes);
			}
		} finally {
			closeStream(bis);
			closeStream(bos);
		}
	}

	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	public static String readFileToStr(File file, String encode) {
		String str = null;
		try {
			FileInputStream in = new FileInputStream(file);
			// size 为字串的长度 ，这里一次性读完
			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			str = new String(buffer, encode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String readFileToStr(InputStream in, String encode) {
		String str = null;
		try {
			// size 为字串的长度 ，这里一次性读完
			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			str = new String(buffer, encode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String readFileToStr(String path) throws Exception {
		File file = new File(path);// 定义一个file对象，用来初始化FileReader
		FileReader reader = new FileReader(file);// 定义一个fileReader对象，用来初始化BufferedReader
		BufferedReader bReader = new BufferedReader(reader);// new一个BufferedReader对象，将文件内容读取到缓存
		StringBuilder sb = new StringBuilder();// 定义一个字符串缓存，将字符串存放缓存中
		String s = "";
		while ((s = bReader.readLine()) != null) {// 逐行读取文件内容，不读取换行符和末尾的空格
			sb.append(s);
		}
		bReader.close();
		String str = sb.toString();
		return str;
	}

	public static void writeStrToFile(String sourceString, String filePath) {
		byte[] sourceByte = sourceString.getBytes();
		if (null != sourceByte) {
			FileOutputStream outStream = null;
			try {
				File file = new File(filePath); // 文件路径（路径+文件名）
				if (!file.exists()) { // 文件不存在则创建文件，先创建目录
					File dir = new File(file.getParent());
					dir.mkdirs();
					file.createNewFile();
				}
				outStream = new FileOutputStream(file); // 文件输出流用于将数据写入文件
				outStream.write(sourceByte);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CustomFileUtil.closeStream(outStream);
			}
		}
	}

	private static void download(String fileName, FileInputStream fileInputStream, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initDownloadParams(fileName, request, response);
		download(fileInputStream, response);
	}

	public static void initDownloadParams(String fileName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.reset();
		response.setContentType("application/x-msdownload;charset=utf-8");
		if (RequestUtil.isFirefoxBrowser(request)) {
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		} else {
			fileName = URLEncoder.encode(fileName, "utf-8");
			if (RequestUtil.isMSBrowser(request)) {
				if (fileName.length() > 150) {
					// 根据request的locale 得出可能的编码
					fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
				}
			}
		}
		response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
	}

	public static void closeStream(Closeable closeable) {
		if (null == closeable) {
			return;
		}
		try {
			closeable.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据文件流计算出文件的MD5
	 * 
	 * @param file
	 * @return
	 */
	public static String getInputStreamMD5(InputStream in) {
		MessageDigest digest = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());

		return bigInt.toString(16);
	}

}
