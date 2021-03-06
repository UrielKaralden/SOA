var Code;
var Access_Token;
var AccountID;


function syntaxHighlight(json) {
    if (typeof json != 'string') {
         json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}

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
	Code = window.location.href.substring(45);
	console.log(Code);
	var buttonValue = document.getElementById('indexButton2').value;
	if(buttonValue == "GetAccessToken")
	{
		var queryString = "value="+buttonValue+"&code="+Code;
		doAjax('AppServlet', queryString, 'getAccessToken_back', 'post', 0);
	}
}

function getAccessToken_back(result)
{
	var TokenInfo = JSON.parse(result);
	Access_Token = TokenInfo.access_token;
	AccountID = TokenInfo.account_id;
	var ShowTokenInfo = "<p>Access Token: " + TokenInfo.access_token + " <br><br>"+
	"Token Type: " + TokenInfo.token_type + " <br><br>"+
	"UID: " + TokenInfo.uid + " <br><br>"+
	"Account ID: " + TokenInfo.account_id  + " <br><br>";
	
	window.document.getElementById('getAccessTokenResult').style.display = 'block';
	window.document.getElementById('getAccessTokenResult').innerHTML = "<p>"+ShowTokenInfo+"</p>";
	
}


function getAccountInfo()
{
	var buttonValue = document.getElementById('indexButton3').value;
	if(buttonValue === "GetAccountInfo")
	{
		var queryString = "value="+buttonValue+"&access_token="+Access_Token+"&account_id="+AccountID;
		doAjax('AppServlet', queryString, 'getAccountInfo_back', 'post', 0);
	}
}

function getAccountInfo_back(result)
{
	var AccountInfo = JSON.parse(result);
	var ShowAccountInfo = 	"<p>Account ID: " + AccountInfo.account_id + " <br><br><br>"+
								"Given Name: " + AccountInfo.name.given_name + " <br><br>"+
								"Surname: " + AccountInfo.name.surname + " <br><br>"+
								"Familiar Name: " + AccountInfo.name.familiar_name  + " <br><br>"+
								"Display Name: " + AccountInfo.name.display_name  + " <br><br>"+
								"Abbreviated Name: " + AccountInfo.name.abbreviated_name + " <br><br>"+
								"Email: " + AccountInfo.email + "</p><br><br>";
	
	window.document.getElementById('getAccountInfoResult').style.display = 'block';
	window.document.getElementById('getAccountInfoResult').innerHTML = ShowAccountInfo;
								
}

function uploadFile()
{
	var buttonValue = document.getElementById('indexButton4').value;
	var FilePath = document.getElementById('fileToUpload').value;
	FilePath = FilePath.substring(12);
	window.document.getElementById('uploadFilePath').style.display = 'block';
	window.document.getElementById('uploadFilePath').innerHTML = "<p>"+FilePath+"</p>";
	window.document.getElementById('uploadFileResult').style.display = 'block';
	window.document.getElementById('uploadFileResult').innerHTML = "<p>"+"Method executed successfully"+"<br>"+result+"</p>";
	if(buttonValue === "UploadFile")
	{
		var queryString = "value="+buttonValue+"&access_token="+Access_Token+"&file_path="+FilePath;
		doAjax('AppServlet', queryString, 'uploadFile_back', 'post', 0);
	}
}

function uploadFile_back(result)
{
	window.document.getElementById('uploadFileResult').style.display = 'block';
	window.document.getElementById('uploadFileResult').innerHTML = "<p>"+"Method executed successfully"+"<br>"+result+"</p>";
}

function listSharedLinks()
{
	var buttonValue = document.getElementById('indexButton5').value;
	window.document.getElementById('prueba').style.display = 'block';
	window.document.getElementById('prueba').innerHTML = "<p>Button used</p><br>";
	var queryString = "value="+buttonValue+"&access_token="+Access_Token;
	doAjax('AppServlet', queryString, 'listSharedLinks_back', 'post', 0);
}

function listSharedLinks_back(result)
{
	var total_links = 0;
	var SharedLinks = JSON.stringify(result);
	
	window.document.getElementById('listSharedLinksResult').style.display = 'block';
	window.document.getElementById('listSharedLinksResult').innerHTML = "<p>"+SharedLinks+"</p>";
}
// more functions to be used