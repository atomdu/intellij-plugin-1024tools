package atomdu.tools.core.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtils {

	public static String inputStreamToString(InputStream in) throws IOException {
		return inputStreamToString(in,"UTF-8");
	}
	public static String inputStreamToString(InputStream in,String charset) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n,charset));
		}
		return out.toString();
	}

	/**
	 * 这个处理json也会有问题
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String inputStreamToString1(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	/**
	 * 这样转换处理json会有问题
	 * 
	 * @param is
	 * @return
	 */
	public static String inputStreamToString2(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
