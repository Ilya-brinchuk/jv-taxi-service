<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Please, enter your login and password:</h1>
<h4 style="color: red">${msg}</h4>
<form method="post" action="${pageContext.request.contextPath}/login">
Login:
<br>
<input type="email" name="login" required>
<br>
Password:
<br>
<input type="password" name="pwd" required>
<br>
<button type="submit">log in</button>
</form>
<br>
<a href="${pageContext.request.contextPath}/drivers/add">registration page</a>
</body>
</html>
