<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Driver</title>
</head>
<body>
<h1>Please enter your name and licence number: </h1>
<form method="post" action="${pageContext.request.contextPath}/drivers/add">
    Name:
    <br>
    <input type="text" name="name" required>
    <br>
    Licence:
    <br>
    <input type="number" name="licence" required>
    <br>
    Login:
    <br>
    <input type="email" name="login" required>
    <br>
    Password:
    <br>
    <input type="password" name="pwd" required>
    <br>
    <button type="submit">register</button>
</form>
<a href="${pageContext.request.contextPath}/" style="color: blue">Return to the main menu</a>
</body>
</html>
