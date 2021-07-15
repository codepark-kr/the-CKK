package center.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CenterFileDownloadServlet
 */
@WebServlet("/center/fileDownload")
public class CenterFileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CenterFileDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rName = request.getParameter("rName");
		String oName = request.getParameter("oName");
		
		String saveDirectory = getServletContext().getRealPath("/upload/cscenter");
		File downFile = new File(saveDirectory, rName);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(downFile));
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		
		String userAgent = request.getHeader("user-agent");
		boolean isMSIE = userAgent.indexOf("Trident") != -1;
		if(isMSIE) {
			oName = URLEncoder.encode(oName, "utf-8").replaceAll("\\+", "%20");
		}
		else {
			oName = new String(oName.getBytes("utf-8"), "iso-8859-1");	
		}
		
		response.setContentType("aplication/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="+oName);
		
		int data = -1;
		while((data = bis.read()) != -1) {
			bos.write(data);
		}
		bis.close();
		bos.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
