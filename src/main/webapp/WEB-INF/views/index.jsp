<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>MATE</title>
  </head>
  <body>
  <h1>Taxi service</h1>
  <h2>Menu</h2>
  <a href="${pageContext.request.contextPath}/manufacturers/add">add manufacturer</a>
  <br>
  <a href="${pageContext.request.contextPath}/drivers">show the list of all drivers</a>
  <br>
  <a href="${pageContext.request.contextPath}/cars/add">add car</a>
  <br>
  <a href="${pageContext.request.contextPath}/cars/drivers/add">add driver to the car</a>
  <br>
  <a href="${pageContext.request.contextPath}/cars/all">show the list of all your cars</a>
  </body>
</html>
