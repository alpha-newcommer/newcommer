<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<script>
function getCookie() {
    var r = document.cookie.split(';');
    r.forEach(function(value) {
         
        //cookie名と値に分ける
        var contents = value.split('=');
        if (contents[0] == "MyCookieValue") {
            var div = document.getElementById("cookieValueDiv");  
            div.textContent = contents[1];
            return;
        }
    });
};
</script>
<body onload="getCookie()">
	<h2>スコープテスト</h2>
	<table border="1">
		<tr>
			<td>MyRequestAttribute</td>
			<td><%=request.getAttribute("MyRequestAttribute") %></td>
		</tr>
		<tr>
			<td>MySessionAttribute</td>
			<td><%=session.getAttribute("MySessionAttribute") %></td>
		</tr>
		<tr>
			<td>MyCookieValue</td>
			<td><div id="cookieValueDiv"></div></td>
		</tr>
	</table>


</body>

</html>
