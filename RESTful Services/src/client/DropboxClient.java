package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

public class DropboxClient 
{
	private static final String APP_KEY = "feczefcf9r4tqkj";
	private static final String APP_SECRET = "sc2el4p2lvljrb8";
	private static final String redirectURI = "http://localhost:8080/RESTful_Services";
	
	public DropboxClient(){}
	
	 public String sendRequest() throws URISyntaxException, IOException
	    {
	    	String str = "";
	    	URI uri = new URI("https://www.dropbox.com/oauth2/authorize");
	    	
	    	StringBuilder requestUri = new StringBuilder(uri.toString());
	    	requestUri.append("?client_id=");
	    	requestUri.append(URLEncoder.encode(APP_KEY, "UTF-8"));
	    	requestUri.append("&response_type=code");
	    	requestUri.append("&redirect_uri="+redirectURI.toString());
	    	str = requestUri.toString();
	    	return str;
	    }
	    
	    public String GetAccessToken (String codeStr) throws URISyntaxException, IOException
	    {
	    	String queryResult;
	    	System.out.println("codeStr = "+codeStr);
	    	
	    	StringBuilder tokenUri = new StringBuilder("code=");
	    	tokenUri.append(URLEncoder.encode(codeStr, "UTF-8"));
	    	tokenUri.append("&grant_type=");
	    	tokenUri.append(URLEncoder.encode("authorization_code", "UTF-8"));
	    	tokenUri.append("&client_id=");
	    	tokenUri.append(URLEncoder.encode(APP_KEY, "UTF-8"));
	    	tokenUri.append("&client_secret=");
	    	tokenUri.append(URLEncoder.encode(APP_SECRET, "UTF-8"));
	    	tokenUri.append("&redirect_uri="+redirectURI);
	    	
	    	System.out.println(tokenUri);
	    	URL url = new URL("https://api.dropbox.com/oauth2/token");
	    	
	    	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    	try
	    	{
	    		connection.setDoInput(true);
	    		connection.setDoOutput(true);
	    		connection.setRequestMethod("POST");
	    		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    		connection.setRequestProperty("Content-Length",""+tokenUri.toString().length());
	    		connection.connect();
	    		
	    		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
	    		outputStreamWriter.write(tokenUri.toString());
	    		outputStreamWriter.flush();
	    		
	    		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    		String inputLine;
	    		StringBuffer response = new StringBuffer();
	    		
	    		while ((inputLine = in.readLine())!= null)
	    		{
	    			response.append(inputLine);
	    		}
	    		in.close();
	    		
	    		queryResult = response.toString();
	    	}
	    	
	    	finally
	    	{
	    		connection.disconnect();
	    	}
	    	System.out.println(queryResult);
	    	return queryResult;
	    }
	    
	    public String getAccountInfo(String tokenStr, String accountIDStr)  throws URISyntaxException, IOException
	    {
	    	String access_token = ""+tokenStr;
	    	String content = "{\"account_id\": \""+ accountIDStr +"\"}";
	    	URL url = new URL("https://api.dropboxapi.com/2/users/get_account");
	    	
	    	String queryResult;
	    	
	    	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    	try
	    	{
	    		connection.setDoOutput(true);
	    		connection.setRequestMethod("POST");
	    		connection.setRequestProperty("Authorization", "Bearer " + access_token);
	    		connection.setRequestProperty("Content-Type", "application/json");
	    		connection.setRequestProperty("Content-Length", "" + content.length());
	    		
	    		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
	    		outputStreamWriter.write(content);
	    		outputStreamWriter.flush();
	    		
	    		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    		String inputLine;
	    		StringBuffer response = new StringBuffer();
	    		
	    		while ((inputLine = in.readLine())!= null)
	    		{
	    			response.append(inputLine);
	    		}
	    		in.close();
	    		
	    		queryResult = response.toString();
	    	}
	    	finally
	    	{
	    		connection.disconnect();
	    	}
	    	System.out.println(queryResult);
	    	return queryResult;
	    }
	    
	    public String uploadFile(String token, String path) throws URISyntaxException, IOException
	    {
	    	String queryResult;

	    	Path pathFile = Paths.get(path);
	    	System.out.println(pathFile);
	    	byte[] data = Files.readAllBytes(pathFile);
	    	String content = "{\"path\": \"image.png\",\"mode\":\"add\",\"autorename\":true,\"mute\":false, \"strict_conflict\":false}";
	    	
	    	URL url = new URL("https://content.dropboxapi.com/2/files/upload");
	    	
	    	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    	try
	    	{
	    		connection.setDoOutput(true);
	    		connection.setDoInput(true);
	    		connection.setRequestMethod("POST");
	    		connection.setRequestProperty("Authorization", "Bearer " + token);
	    		connection.setRequestProperty("Dropbox-API-Arg", content);
	    		connection.setRequestProperty("Content-Type", "application/octet-stream");
	    		connection.setRequestProperty("Content-Length", String.valueOf(data.length));
	    		
	    		OutputStream outputStream = connection.getOutputStream();

	    		outputStream.write(data);
	    		outputStream.flush();
	    		outputStream.close();
	    		connection.connect();
	    		
	    		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    		String inputLine;
	    		StringBuffer response = new StringBuffer();
	    		
	    		while ((inputLine = in.readLine())!= null)
	    		{
	    			response.append(inputLine);
	    		}
	    		in.close();
	    		
	    		queryResult = response.toString();
	    	}
	    	finally
	    	{
	    		connection.disconnect();
	    	}
	    	System.out.println("Show something");
	    	System.out.println(queryResult);
	    	return queryResult;
	    }
	    
	    public String listSharedLinks(String token)throws URISyntaxException, IOException
	    {
	    	String queryResult = "";
	    	URL url = new URL("https://api.dropboxapi.com/2/sharing/list_shared_links");
	    	
	    	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    	try
	    	{
	    		System.out.println("Executing List Shared Links");
	    		connection.setDoOutput(true);
	    		connection.setRequestMethod("POST");
	    		connection.setRequestProperty("Authorization", "Bearer " + token);
	    		
	    		/*OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
	    		outputStreamWriter.write();
	    		outputStreamWriter.flush();*/
	    		
	    		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    		String inputLine;
	    		StringBuffer response = new StringBuffer();
	    		
	    		while ((inputLine = in.readLine())!= null)
	    		{
	    			response.append(inputLine);
	    		}
	    		in.close();
	    		
	    		queryResult = response.toString();
	    	}
	    	finally
	    	{
	    		connection.disconnect();
	    	}
	    	System.out.println(queryResult);
	    	return queryResult;
	    }
}
