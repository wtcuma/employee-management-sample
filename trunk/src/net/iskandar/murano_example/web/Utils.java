package net.iskandar.murano_example.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Utils {
	public static void parseMappings(String mappingsStr, Map mappings){
		try{
			InputStream inputStream = new ByteArrayInputStream(mappingsStr.getBytes("ISO-8859-1"));
			Properties props = new Properties();
			props.load(inputStream);
			for(Object key : props.keySet()){
				mappings.put((String) key, (String) props.get(key));
			}
		} catch(UnsupportedEncodingException e){
			throw new RuntimeException(e);
		} catch(IOException ioe){
			throw new RuntimeException(ioe);
		}
	}
	
	public static void main(String[] args){
		
		String str = "\n\n/rpc/employeeManagement.rpc=employeeManagement\n\n\n";
		Map map = new HashMap();
		parseMappings(str, map);
		System.out.println(map);
	}
	
	
}
