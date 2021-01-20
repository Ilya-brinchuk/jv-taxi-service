<%--
  Created by IntelliJ IDEA.
  User: Ilya
  Date: 20.01.2021
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver to car</title>
</head>
<body>
<h1>Please enter driver id and car id</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/drivers/add">
    Driver id: <input type="text" required=""  name="driver_id">
    Car id: <input type="text" required="" name="car_id">
    <button type="submit">register</button>
</form>
<a href="${pageContext.request.contextPath}/" style="color: blue">Return to the main menu</a>
</body>
</html>
