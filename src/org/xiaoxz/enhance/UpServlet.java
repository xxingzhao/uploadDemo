package org.xiaoxz.enhance;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UpServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4032971870916882356L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String txt = request.getParameter("txt");
		System.out.println("txt:"+txt);
		System.out.println("=================");
		InputStream in = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String firstLine = br.readLine();
		String fileName = br.readLine();
		
		fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
		fileName = fileName.substring(0,fileName.length()-1);
		
		br.readLine();
		br.readLine();
		
		String data = null;

	}

}
