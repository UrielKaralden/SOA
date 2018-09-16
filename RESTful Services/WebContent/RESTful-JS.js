var Code;
var Access_Token;

function sendRequest()
{
	var buttonValue = document.getElementById('indexButton1').value;
	if(buttonValue == "Send Request")
	{
		var queryString = "type=button&value="+buttonValue;
		doAjax('AppServlet', queryString, 'sendRequest_back', 'post', 0);
	}
}

function sendRequest_back(result)
{
	window.document.getElementById('sendRequestResult').style.display = 'block';
	window.document.getElementById('sendRequestResult').innerHTML = "<p>Hello world</p>";
}

function getAccessToken()
{
	var buttonValue = document.getElementById('indexButton2').value;
	if(buttonValue == "Get Access Token")
	{
		var queryString = "type=button&value="+buttonValue;
		doAjax('AppServlet', queryString, 'getAccessToken_back', 'post', 0);
	}
}

function getAccessToken_back(result)
{
	window.document.getElementById('getAccessTokenResult').style.display = 'block';
	window.document.getElementById('getAccessTokenResult').innerHTML = "<p>"+result+"</p>";
}


function getAccountInfo()
{
	var buttonValue = document.getElementById('indexButton3').value;
	if(buttonValue === "Get Account Info")
	{
		var queryString = "type=button&value="+buttonValue;
		doAjax('AppServlet', queryString, 'getAccountInfo_back', 'post', 0);
	}
}

function getAccountInfo_back(result)
{
	window.document.getElementById('getAccountInfoResult').style.display = 'block';
	window.document.getElementById('getAccountInfoResult').innerHTML = "<p>"+result+"</p>";
}

function uploadFile()
{
	var buttonValue = document.getElementById('indexButton4').value;
	if(buttonValue === "Upload File")
	{
		var queryString = "type=button&value="+buttonValue;
		doAjax('AppServlet', queryString, 'uploadFile_back', 'post', 0);
	}
}

function uploadFile_back(result)
{
	window.document.getElementById('uploadFileResult').style.display = 'block';
	window.document.getElementById('uploadFileResult').innerHTML = "<p>"+result+"</p>";
}


// more functions to be used