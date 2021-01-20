<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add manufacturer</title>
</head>
<body>
<h1>Please enter company name and country of brand registration: </h1>
<h4 style="color: red">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/manufacturers/add">
    Company name: <input required="" type="text" name="name">
    Country of brand registration: <input required="" type="text" name="country">
    <button type="submit">register</button>
</form>
<a href="${pageContext.request.contextPath}/" style="color: blue">Return to the main menu</a>
</body>
</html>
