Set html = CreateObject("htmlfile")
Set http = CreateObject("Msxml2.ServerXMLHTTP")

html.designMode = "on" ' �����༭ģʽ

http.open "GET", "http://www.cnblogs.com/cate/python/", False
http.send
strHtml = http.responseText

html.write strHtml ' д������
Set post_list = html.getElementById("post_list")
For Each el In post_list.children
    WScript.Echo el.getElementsByTagName("a")(0).innerText
Next