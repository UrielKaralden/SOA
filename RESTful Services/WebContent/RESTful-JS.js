var Code;
var Access_Token;

function sendRequest()
{
	var queryString = "type=button&value="+document.getElementById('indexButton').value;
	
	doAjax('AppServlet', queryString, 'sendRequest_back', 'post', 0);
}

function sendRequest_back(result)
{
	window.location = result;
	Code = window.location.href.substring(41);
	window.document.getElementById('sendRequestResult').style.display = 'block';
	window.document.getElementById('sendRequestResult').innerHTML = "<p>"+Code+"</p>";
}

function getAccessToken()
{
	var queryString = "";
	doAjax('AppServlet', queryString, 'getAccessToken_back', 'post', 0);
}

function getAccessToken_back(result)
{
	window.document.getElementById('getAccessTokenResult').style.display = 'block';
	window.document.getElementById('getAccessTokenResult').innerHTML = "<p>"+result+"</p>";
}


function getAccountInfo()
{
	var queryString = "";
	doAjax('AppServlet', queryString, 'getAccountInfo_back', 'post', 0);
}

function getAccountInfo_back(result)
{
	window.document.getElementById('getAccountInfoResult').style.display = 'block';
	window.document.getElementById('getAccountInfoResult').innerHTML = "<p>"+result+"</p>";
}

function uploadFile()
{
	var queryString = "";
	doAjax('AppServlet', queryString, 'uploadFile_back', 'post', 0);
}

function uploadFile_back(result)
{
	window.document.getElementById('uploadFileResult').style.display = 'block';
	window.document.getElementById('uploadFileResult').innerHTML = "<p>"+result+"</p>";
}


// more functions to be used