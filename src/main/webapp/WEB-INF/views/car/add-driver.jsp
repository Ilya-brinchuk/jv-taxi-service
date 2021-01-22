<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver to car</title>
</head>
<body>
<h1>Please enter driver id and car id</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/drivers/add">
    Driver id: <input type="text" name="driver_id" required>
    Car id: <input type="text" name="car_id" required>
    <button type="submit">register</button>
</form>
<a href="${pageContext.request.contextPath}/" style="color: blue">Return to the main menu</a>
</body>
</html>
