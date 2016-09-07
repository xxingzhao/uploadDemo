package org.xiaoxz.up;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

public class UpImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6413490036243194330L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String path = this.getServletContext().getRealPath("/up");
		//第一步。声明 DiskFileItemFactory ，设置临时目录
		File f = new File("D:/a");
		if(!f.exists()){  //如果文件夹不存在，则新建一个
			f.mkdir();
		}
		DiskFileItemFactory disk = new DiskFileItemFactory(1024*10, f);
		//第二步，声明ServletFileUpload，接受临时目录
		ServletFileUpload up = new ServletFileUpload(disk);
		
		List<String> imgs = new ArrayList<String>();
		
		try{
			List<FileItem> list = up.parseRequest(request);
			for(FileItem file:list){
				if(file.getContentType().contains("image/")){
					String fileName = file.getName();
					fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					
					InputStream in = file.getInputStream();
					
					//获取扩展名
					String extName = fileName.substring(fileName.lastIndexOf(".")); // .jpg
					//UUID
					String uuid = UUID.randomUUID().toString().replace("-", "");
					//新名称
					String newName = uuid+extName;
					
					FileUtils.copyInputStreamToFile(in,new File(path+newName));
					imgs.add(newName);
					in.close();
					//删除临时目录
					file.delete();
				}
				request.setAttribute("imgs",imgs);
				request.getRequestDispatcher("/jsps/imgs.jsp").forward(request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
