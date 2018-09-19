package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import client.DropboxClient;


/**
 * Servlet implementation class AppServlet
 */
@WebServlet("/AppServlet")
public class AppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DropboxClient client = new DropboxClient();
		
		PrintWriter out = response.getWriter();
		System.out.println("hola");
		String value = request.getParameter("value").trim();
		System.out.println("Value = " +value);
		
		if(value.equals("SendRequest"))
		{
			try {
				String Result = client.sendRequest();
				System.out.println(Result);
				out.write(Result);
				out.flush();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(value.equals("GetAccessToken"))
		{
			try {
				String Code = request.getParameter("code");
				String Access_Token = client.GetAccessToken(Code);
				out.write(Access_Token);
				out.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		else if(value.equals("GetAccountInfo"))
		{		
			String accountID = request.getParameter("account_id");
			String token = request.getParameter("access_token");
			
			try {
				out.write(client.getAccountInfo(token, accountID));
				out.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		else if(value.equals("UploadFile"))
		{
			String token = request.getParameter("access_token");
			String filePath = "../../jeje.jpeg";//request.getParameter("file_path");
			
			try {
				client.uploadFile(token, filePath);
				out.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		else if(value.equals("ListSharedLinks"))
		{
			
			String token = request.getParameter("access_token");
			try {
				out.write(client.listSharedLinks(token));
				out.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		out.close();
	}

}
