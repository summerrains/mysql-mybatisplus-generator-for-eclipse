package net.summerrains.generator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public final class TempleteUtils {

	public static final String readTemplete(Class<?> clazz,String tplFile) {
		StringBuilder builder = new StringBuilder();
		try {
			InputStreamReader in = new InputStreamReader(clazz.getResourceAsStream(tplFile),"UTF-8");
			BufferedReader br = new BufferedReader(in);
			String line = null;
			while((line = br.readLine()) != null) {
				builder.append(line);
				builder.append("\r\n");
			}
			in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return builder.toString();
	}
	
	public static final String readTemplete(String tplFile) {
		StringBuilder builder = new StringBuilder();
		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream(tplFile),"UTF-8");
			BufferedReader br = new BufferedReader(in);
			String line = null;
			while((line = br.readLine()) != null) {
				builder.append(line);
				builder.append("\r\n");
			}
			in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return builder.toString();
	}
	public static final void writeTemplete(String tplFile,String content) {
		try {
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(tplFile),"UTF-8");
			out.write(content);
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
