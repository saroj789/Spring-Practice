package com.springsearch.contoller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class FileUploadController {
	
	@RequestMapping("/fileupload")
	public String showFileForm() {
		return "fileform";
	}
	
	@RequestMapping(path ="/uploadimage",method = RequestMethod.POST)
	public String fileUpload(@RequestParam("profile") CommonsMultipartFile file,
			Model model,HttpSession session) {
		
		System.out.println("file name : "+file.getOriginalFilename());
		
		byte[] filedata = file.getBytes();
		
		String path= session.getServletContext().getRealPath("/")+ "WEB-INF/resources/images/"+ file.getOriginalFilename();
		System.out.println("path : "+path);
		
		 try {
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(filedata);
			fos.close();
			System.out.println("file Uploaded");
			model.addAttribute("msg","file uploaded successfully");
			model.addAttribute("filename",file.getOriginalFilename());
			
		 } catch (FileNotFoundException e) {
			System.out.println("file not found");
			model.addAttribute("msg","file not found");
			e.printStackTrace();
		 } catch (IOException e) {
			 System.out.println("file upload error");
			 model.addAttribute("msg","file upload error");
			 e.printStackTrace();
		 }
		
		 return "uploadsuccess";
		}
	

}
