package com.edu;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

public class FileAction extends HttpServlet {
	private static final long serialVersionUID = -7021477422321931179L;

	private String file_name;//文件名称
	public static final int BUFFER_SIZE = 2 * 1024;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String filePath = this.getServletConfig().getServletContext().getRealPath("")+ "\\uploads\\";
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("utf-8");
			FileItemIterator iter = upload.getItemIterator(req);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				InputStream in = item.openStream();
				if (item.isFormField()) {//是否是表单属性
					String fieldName = item.getFieldName();//属性名称
					String value = Streams.asString(in);//属性值
					if("Filename".equals(fieldName))this.file_name = value;
				}else {
					File dstFile = new File(filePath);
					if (!dstFile.exists()) {
						dstFile.mkdirs();
					}
					System.out.println("文件保存路径："+filePath);
					File dst = new File(dstFile.getPath() + "\\" + this.file_name);
					saveUploadFile(in, dst);
				}
			}
			resp.getWriter().print("ok");
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	
	/**
	 * 保存上传文件
	 * @param src 原文件
	 * @param dst 另存文件
	 */
	private void saveUploadFile(InputStream in, File dst) throws IOException {
		OutputStream out = null;
		if (dst.exists()) {
			out = new BufferedOutputStream(new FileOutputStream(dst, true),BUFFER_SIZE);
		} else {
			out = new BufferedOutputStream(new FileOutputStream(dst),BUFFER_SIZE);
		}
		
		byte[] buffer = new byte[BUFFER_SIZE];
		int len = 0;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		
		if (in != null) in.close();
		if (out != null) out.close();
	}
	
	
}