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
	<h2>スコープ設定</h2>
	<form method="post" action="send">
		<table border="1">
			<tr>
				<td>MyRequestAttribute</td>
				<td><input type="text" name="requestValue" /></td>
			</tr>
			<tr>
				<td>MySessionAttribute</td>
                <td><input type="text" name="sessionValue" /></td>
			</tr>
			<tr>
				<td>MyCookieValue</td>
                <td><input type="text" name="cookieValue" /></td>
			</tr>
		</table>
		<input type="submit" value="送信">
	</form>

</body>

</html>
