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
		
		PrintWriter out = response.getWriter();
		String sendRequestButton = request.getParameter("indexButton1");
		String getAccessTokenButton = request.getParameter("indexButton2");
		String getAccountInfoButton = request.getParameter("indexButton3");
		String uploadFileButton = request.getParameter("indexButton4");
		
		if(sendRequestButton != null)
		{
			try {
				out.write(client.sendRequest());
				out.flush();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		if (getAccessTokenButton != null)
		{
			ScriptEngineManager engineManager = new ScriptEngineManager();
			ScriptEngine jsEngine = engineManager.getEngineByName("JavaScript");
			String Code = jsEngine.get("Code").toString();
			
			try {
				out.write(client.GetAccessToken(Code));
				out.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if (getAccountInfoButton != null)
		{
			ScriptEngineManager engineManager = new ScriptEngineManager();
			ScriptEngine jsEngine = engineManager.getEngineByName("JavaScript");
			String token = jsEngine.get("Access_Token").toString();
			
			String accountID = "";
			try {
				accountID = jsEngine.eval("window.document.getParameterById(\"accountID\")").toString();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				out.write(client.getAccountInfo(token, accountID));
				out.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		if (uploadFileButton != null)
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
