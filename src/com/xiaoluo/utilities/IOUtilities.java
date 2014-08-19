package com.xiaoluo.utilities;

import java.io.*;

/**
 * Created by xiaoluo on 2014/7/23.
 * XiaoluoLibHttp
 */
public class IOUtilities {
	public static String readFromFile(String path) {
		ByteArrayOutputStream out = null;
		FileInputStream in = null;

		try{
			File f = new File(path);
			in = new FileInputStream(f);
			out = new ByteArrayOutputStream(1024);
			byte[] b = new byte[1000];
			int n;

			while((n = in.read(b)) != -1) {
				out.write(b, 0, n);
			}
			out.flush();
			return new String(out.toByteArray());
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			if(in != null){
				try{
					in.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}

			if(out != null){
				try{
					out.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
