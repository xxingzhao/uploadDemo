package org.xiaoxz.enhance;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

/**
 * 提升文件上传性能
 * @author xiaoxz
 *
 */
public class FastServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5893453484194963883L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String path = this.getServletContext().getRealPath("/up");
		File f = new File("D:/a");
		if(!f.exists()){
			f.mkdir();
		}
		DiskFileItemFactory disk = new DiskFileItemFactory();
		disk.setRepository(f);   //设置临时目录
		disk.setSizeThreshold(1024*10);  //设置临时上传文件大小
		
		ServletFileUpload upload = new ServletFileUpload();
		
		upload.setFileSizeMax(1024*1024*1024); //设置每个文件大小为 10M
		upload.setSizeMax(1024*1024*1024*10);  //设置最多上传文件总数为 100M
		try{
			upload.setFileItemFactory(disk);
			FileItemIterator fileIterator = upload.getItemIterator(request);
			while(fileIterator.hasNext()){
				FileItemStream item = fileIterator.next();
				if(!item.isFormField()){
					String fileName = item.getName();  //获取文件名
					fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					InputStream in = item.openStream();
					FileUtils.copyInputStreamToFile(in, new File(path+"/"+fileName));
				}
			}
		}catch(Exception e){
			
		}
		
		
		

	}

}
