<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Driver</title>
</head>
<body>
<h1>Please enter your name and licence number: </h1>
<form method="post" action="${pageContext.request.contextPath}/drivers/add">
    Name: <input type="text" name="name" required>
    Id: <input type="number" name="licence" required>
    <button type="submit">register</button>
</form>
<a href="${pageContext.request.contextPath}/" style="color: blue">Return to the main menu</a>
</body>
</html>
