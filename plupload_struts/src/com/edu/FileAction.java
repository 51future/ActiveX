package com.edu;

import java.io.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FileAction extends ActionSupport {
	private static final long serialVersionUID = 597867126894001021L;
	
	private static final int BUFFER_SIZE = 2 * 1024;
	
	private File upload;//上传的文件
	private String name;//临时文件名
	private String uploadFileName;//上传文件名
	private String uploadContentType;//上传文件类型
	private int chunk;//数据块位置
	private int chunks;//数据块长度
	
	private String saveFilePath;//另存路径
	private String resultString = "";//返回结果

	public String upload() throws Exception {
		String dstPath = ServletActionContext.getServletContext().getRealPath(this.getSaveFilePath());
		File dstFile = new File(dstPath + "\\" + this.getName());
		// 文件已存在（上传了同名的文件）
		if (chunk == 0 && dstFile.exists()) {
			dstFile.delete();
			dstFile = new File(dstPath + "\\" + this.getName());
		}
		saveUploadFile(this.upload, dstFile);
		if (chunk == chunks - 1) {
			// 完成一整个文件;
			System.out.println("保存文件路径： " + dstPath);
			System.out.println("上传文件名：" + uploadFileName + "--->上传文件类型：" + uploadContentType);
			System.out.println("临时文件名：" + name + "--->chunk：" + chunk + "--->chunks：" + chunks);
		}
		return SUCCESS;
	}
	
	
	public String submit() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int count = Integer.parseInt(request.getParameter("uploader_count"));
		for (int i = 0; i < count; i++) {
			uploadFileName = request.getParameter("uploader_" + i + "_name");
			name = request.getParameter("uploader_" + i + "_tmpname");
			resultString += "上传文件名：" + uploadFileName + "--->临时文件名：" + name+"<br/>";
		}
		return SUCCESS;
	}

	/**
	 * 保存上传文件
	 * @param src 原文件
	 * @param dst 另存文件
	 */
	private void saveUploadFile(File src, File dst) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		if (dst.exists()) {
			out = new BufferedOutputStream(new FileOutputStream(dst, true),BUFFER_SIZE);
		} else {
			out = new BufferedOutputStream(new FileOutputStream(dst),BUFFER_SIZE);
		}
		in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
		
		byte[] buffer = new byte[BUFFER_SIZE];
		int len = 0;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		if (in != null) in.close();
		if (out != null) out.close();
	}


	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public int getChunk() {
		return chunk;
	}
	public void setChunk(int chunk) {
		this.chunk = chunk;
	}

	public int getChunks() {
		return chunks;
	}
	public void setChunks(int chunks) {
		this.chunks = chunks;
	}

	public String getSaveFilePath() {
		return saveFilePath;
	}
	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}

	public String getResultString() {
		return resultString;
	}
	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static int getBufferSize() {
		return BUFFER_SIZE;
	}

}
