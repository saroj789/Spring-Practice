package com.newgen.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;

import com.newgen.bean.Constants;
import com.newgen.bean.StatusBean;
import com.newgen.business.AddDocHelper;
import com.newgen.business.Validations;
import com.newgen.logger.CustomLogger;

/**
 * Servlet implementation class AddDocument
 */
@WebServlet("/AddDocument")
public class AddDocument extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddDocument() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
    		response.getWriter().append("InValid Request. Please use Post request");
    	}catch (Exception e) {
    		 CustomLogger.printErr("Error in AddDocument Servlet - "+Validations.stackTraceToString(e));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = RandomStringUtils.randomAlphanumeric(20);
		CustomLogger.printOut(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "servlet - " + request.getRequestURI()
				+ " Started for KFV - " + request.getParameter("KFV"));
		Map<String, String> params = new HashMap<>();
		Map<String, String> files = new HashMap<>();
		try {
			Validations validations=new Validations();
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					if (params.containsKey(item.getFieldName())) {
						params.put(item.getFieldName(), params.get(item.getFieldName()) + "," + item.getString());
					} else {

						params.put(item.getFieldName(), item.getString());
					}

				} else {

					String fullFileName = item.getName();
					File ftemp = new File(fullFileName);
					String fileName = ftemp.getName();

					if (!Validations.isEmpty(fileName)) {

						File tmpFile = new File(System.getProperty(Constants.USERDIR) + File.separator + Constants.TMPDIR
								+ File.separator + fileName);
						String ext = FilenameUtils.getExtension(fileName);
						String filenamewithoutext = fileName.substring(0, fileName.lastIndexOf('.'));
						int count = 1;
						while (tmpFile.exists()) {

							tmpFile = new File(System.getProperty(Constants.USERDIR) + File.separator + Constants.TMPDIR
									+ File.separator + filenamewithoutext + "_" + count + "." + ext);
							count++;
						}
						if(writeToFile(item,tmpFile,sessionId)) {
							files.put(tmpFile.getAbsolutePath(), fileName);
						}
						
						
					}

				}

			}
			StatusBean validationBean=validations.validateUploadData(params,files,sessionId);
			if(!validationBean.getStatus().equals(Constants.FAILSTATUSSTRING)) {
				validationBean=new AddDocHelper().addDocument(params,files,sessionId);
			}
			response.getWriter().print(validationBean.getMessage());

		} catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "Error in "
					+ request.getRequestURI() + " - " + Validations.stackTraceToString(e));
		} finally {
			deleteFiles(files,sessionId);
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER + sessionId + " - " + "servlet - "
					+ request.getRequestURI() + " Ended");
		}
	}
	
	private boolean writeToFile(FileItem item, File tmpFile,String sessionId) {
		try (OutputStream outputStream = new FileOutputStream(tmpFile);){
			IOUtils.copy(item.getInputStream(), outputStream);
			return true;
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in AddDocument Servlet while writing files - "+Validations.stackTraceToString(e));
			return false;
		}
	}

	private void deleteFiles(Map<String, String> files, String sessionId) {
		Set<String> fileList=files.keySet();
		try {
			for(String filepath:fileList) {
				File f2=new File(filepath);
				if(f2.exists()) {
					Files.delete(f2.toPath());
				}
			}
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionId+" - "+"Error in AddDocument Servlet while deleting files - "+Validations.stackTraceToString(e));
		}
	}

}
