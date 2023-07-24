package com.newgen.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;

import org.apache.tika.Tika;

import com.newgen.bean.Constants;
import com.newgen.omni.jts.cmgr.XMLParser;

public class TestMain {

	public static void main(String[] args) throws IOException {
		/*Empty method to test code
		 * 
		 */
		System.out.println(new Tika().detect(new File("C:\\Users\\prashant.nathe\\Pictures\\39.png")));
		
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		System.out.println(mimeTypesMap.getContentType(new File("C:\\Users\\prashant.nathe\\Pictures\\39.png")));
		
		String str="";
		try(BufferedReader br=new BufferedReader(new FileReader(System.getProperty(Constants.USERDIR)+File.separator+"uploadconf.xml"))) {
			String record = "";
			while ((record = br.readLine()) != null) {
				str = String.valueOf(str) + record;
			}
		} catch (Exception e) {
			
		}
		XMLParser xmlParser=new XMLParser(str);
		int len=xmlParser.getNoOfFields("MimeType");
		System.out.println(xmlParser.getFirstValueOf("MimeType"));
		for(int i=1;i<len;i++) {
			System.out.println(xmlParser.getNextValueOf("MimeType"));
		}
	}

}
