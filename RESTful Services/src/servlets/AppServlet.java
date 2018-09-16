package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
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
		String Result = "";
		String Access_Token = "";
		
		PrintWriter out = response.getWriter();

		String value = request.getParameter("value").trim();
		System.out.println("Value = " +value);
		
		if(value.equals("SendRequest"))
		{
			try {
				Result = client.sendRequest();
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
				Access_Token = client.GetAccessToken(Code);
				out.write(Access_Token);
				out.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		else if(value.equals("GetAccountInfo"))
		{		
			String accountID = "";
			try {
				//accountID = jsEngine.eval("window.document.getParameterById(\"accountID\")").toString();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				out.write(client.getAccountInfo(Access_Token, accountID));
				out.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		else if(value.equals("UploadFile"))
		{
			ScriptEngineManager engineManager = new ScriptEngineManager();
			ScriptEngine jsEngine = engineManager.getEngineByName("JavaScript");
			String token = jsEngine.get("Access_Token").toString();
			
			String filePath = "";
			try {
				filePath = jsEngine.eval("window.document.getParameterById(\"fileToUpload\").value").toString();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				out.write(client.uploadFile(token, filePath));
				out.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		out.close();
	}

}
