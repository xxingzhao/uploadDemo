package org.xiaoxz.up;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 单文件上传
 * @author xiaoxz
 *
 */
public class Up2Servlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//��ȡ��Ŀ��·��
		String path = getServletContext().getRealPath("/upload");
        //��һ������ DiskFileItemFactory�����࣬����������ʱĿ¼
		DiskFileItemFactory diskFactory = new DiskFileItemFactory(1024*10,new File("d:/a"));
		//�ڶ��� ����ServletUpload ,�����������ʱĿ¼
		ServletFileUpload up = new ServletFileUpload(diskFactory);
		//���� ������request����
		try{
			@SuppressWarnings("unchecked")
			List<FileItem> list = up.parseRequest(request);
			//���ֻ�ϴ�һ���ļ�
			FileItem file  = list.get(0);   
			String fileName = file.getFieldName();
			//��ȡ�ļ���
			fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
			//��ȡ�ļ�����
			String fileType = file.getContentType();
			//��ȡ�ļ�������
			InputStream  in = file.getInputStream();
			
			OutputStream out = new FileOutputStream(path+fileName);
			
			byte[] b = new byte[1024];
			int len = 0;
			while((len=in.read(b))!=-1){
				out.write(b, 0, len);
			}
			out.close();
			//�ļ���С  ��λ /bytes
			long size = file.getInputStream().available();
			
			//ɾ����ʱĿ¼
			file.delete();
			//��ʾ���
			response.setContentType("text/html;charset=utf-8");
			PrintWriter op = response.getWriter();
			op.print("文件上传成功<br/>文件名:"+fileName);
			op.print("<br/>文件类型:"+fileType);
			op.print("<br/>文件大小（bytes）"+size);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
