var Code;
var Access_Token;

function sendRequest()
{
	var buttonValue = document.getElementById('indexButton1').value;
	if(buttonValue == "SendRequest")
	{
		var queryString = "type=button&value="+buttonValue;
		doAjax('AppServlet', queryString, 'sendRequest_back', 'POST', 0);
	}
}

function sendRequest_back(result)
{
	window.location = result;
}

function getAccessToken()
{
	Code = window.location.href.substring(41);
	var buttonValue = document.getElementById('indexButton2').value;
	if(buttonValue == "GetAccessToken")
	{
		var queryString = "value="+buttonValue+"&code="+Code;
		doAjax('AppServlet', queryString, 'getAccessToken_back', 'post', 0);
	}
}

function getAccessToken_back(result)
{
	window.document.getElementById('getAccessTokenResult').style.display = 'block';
	window.document.getElementById('getAccessTokenResult').innerHTML = "<p>How are you doing?</p><br><p>"+result+"</p>";
}


function getAccountInfo()
{
	var buttonValue = document.getElementById('indexButton3').value;
	if(buttonValue === "GetAccountInfo")
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
	if(buttonValue === "UploadFile")
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