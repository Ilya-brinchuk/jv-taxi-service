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
    <input required="" type="text" name="model">
    <br>
    Manufacturer id:
    <br>
    <input type="number" required="" name="manufacturer_id">
    <br>
    <button type="submit">register</button>
</form>
<a href="${pageContext.request.contextPath}/" style="color: blue">Return to the main menu</a>
</body>
</html>
