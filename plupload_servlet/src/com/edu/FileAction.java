package com.edu;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

public class FileAction extends HttpServlet {
	private static final long serialVersionUID = -1004698406282276388L;
	private static final String RESP_SUCCESS = "{\"jsonrpc\" : \"2.0\", \"result\" : \"success\", \"id\" : \"id\"}";
	private static final String RESP_ERROR = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to open input stream.\"}, \"id\" : \"id\"}";
	public static final String RESP_TYPE = "application/json";
	public static final int BUFFER_SIZE = 2 * 1024;
	
	private String name;//临时文件名
	private int chunk;//数据块指针
	private int chunks;//数据块指针长度
	
	private String saveFilePath = "resources\\files\\";//文件存放路径
	private String resultString = "";//返回结果
	/**
	 * 上传文件
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resultString= RESP_SUCCESS;
		String filePath =  req.getSession().getServletContext().getRealPath("/")+saveFilePath;
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (isMultipart) {
			ServletFileUpload upload = new ServletFileUpload();
			try {
				FileItemIterator iter = upload.getItemIterator(req);
				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					InputStream in = item.openStream();
					
					if (item.isFormField()) {//是否是表单属性
						String fieldName = item.getFieldName();//属性名称
						String value = Streams.asString(in);//属性值
						if ("name".equals(fieldName)) {
							this.name = value;
						} else if ("chunk".equals(fieldName)) {
							this.chunk = Integer.parseInt(value);
						} else if ("chunks".equals(fieldName)) {
							this.chunks = Integer.parseInt(value);
						} 
					}else {// Handle a multi-part MIME encoded file.
						File dstFile = new File(filePath);
						if (!dstFile.exists()) {
							dstFile.mkdirs();
						}
						File dst = new File(dstFile.getPath() + "\\" + this.name);
						saveUploadFile(in, dst);
					}
				}
			} catch (Exception e) {
				resultString = RESP_ERROR;
				e.printStackTrace();
			}
		} else {// Not a multi-part MIME request.
			resultString = RESP_ERROR;
		}

		if (this.chunk == this.chunks - 1) {
			// 完成一整个文件的上传;
			System.out.println("文件保存目录："+filePath);
			System.out.println("name："+this.name);
			System.out.println("chunk："+this.chunk);
			System.out.println("chunks："+this.chunks);
		}
		resultStr(RESP_TYPE,resultString,resp);//返回结果
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
	
	/**
	 * 返回结果
	 * @param resp_type 以什么形式返回
	 * @param resultStr 返回的内容
	 * @param resp response对象
	 * @throws IOException IO异常
	 */
	private void resultStr(String resp_type,String resultStr,HttpServletResponse resp) throws IOException{
		resp.setContentType(RESP_TYPE);
		byte[] responseBytes = resultStr.getBytes();
		resp.setContentLength(responseBytes.length);
		ServletOutputStream output = resp.getOutputStream();
		output.write(responseBytes);
		output.flush();
	}
	
	
}
