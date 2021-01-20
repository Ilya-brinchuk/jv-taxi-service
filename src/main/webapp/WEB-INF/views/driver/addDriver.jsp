<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Driver</title>
</head>
<body>
<h1>Please enter your name and licence number: </h1>
<h4 style="color: red">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/drivers/add">
    Name: <input required="" type="text" name="name">
    Licence number: <input type="number" required="" name="licence">
    <button type="submit">register</button>
</form>
<a href="${pageContext.request.contextPath}/" style="color: blue">Return to the main menu</a>
</body>
</html>
