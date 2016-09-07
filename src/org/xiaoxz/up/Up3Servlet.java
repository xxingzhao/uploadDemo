package org.xiaoxz.up;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

/**
 * 多文件上传
 * @author xiaoxz
 * @Date 2016/09/06
 *
 */
public class Up3Servlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3047947893749122889L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);

	}
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//获取项目路径
		String path = this.getServletContext().getRealPath("/up");
		//第一步：声明DiskFileItemFactory，设置临时目录
		File f =  new File("d:/a");
		if(!f.exists()){  //判断文件夹是否存在，如果不存则创建一个文件夹
			f.mkdir();
		}
		DiskFileItemFactory disk = new DiskFileItemFactory(1024*10,f);
		//第二步，声明ServletFileUpload，接受 临时目录
		ServletFileUpload upload = new ServletFileUpload(disk);
		try{
			//解析request
			List<FileItem> list = upload.parseRequest(request);
			List<Map<String,String>> maps = new ArrayList<Map<String,String>>();
			
			for(FileItem file:list){
				Map<String,String> mm = new HashMap<String,String>();
				String fileName = file.getName();
				//文件名
				fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
				//文件类型
				String fileType = file.getContentType();
				InputStream in = file.getInputStream();
				long size = file.getInputStream().available();
				
				FileUtils.copyInputStreamToFile(in, new File(path + "/" + fileName));
				mm.put("fileName", fileName);
				mm.put("fileType", fileType);
				mm.put("size", ""+size);
				
				maps.add(mm);
				//删除临时目录
				file.delete();
			}
			
			request.setAttribute("ups", maps);
			request.getRequestDispatcher("/jsps/show.jsp").forward(request, response);
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
