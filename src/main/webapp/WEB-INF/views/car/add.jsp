<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Car</title>
</head>
<body>
<h1>Please enter model  and manufacturer id: </h1>
<form method="post" action="${pageContext.request.contextPath}/cars/add">
    Model:
    <br>
    <input type="text" name="model" required>
    <br>
    Manufacturer id:
    <br>
    <input type="number" name="manufacturer_id" required>
    <br>
    <button type="submit">register</button>
</form>
<a href="${pageContext.request.contextPath}/" style="color: blue">Return to the main menu</a>
</body>
</html>
