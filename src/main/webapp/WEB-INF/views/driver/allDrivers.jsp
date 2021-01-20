<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All drivers</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/" style="color: blue">Return to the main menu</a>
<br>
<a href="${pageContext.request.contextPath}/cars/drivers/add">add driver to the car</a>
<table border="1">
    <tr>
        <th>Name</th>
        <th>Licence number</th>
    </tr>
    <c:forEach var="driver" items="${drivers}">
        <tr>
            <td>
                <c:out value="${driver.name}"/>
            </td>
            <td>
                <c:out value="${driver.licenceNumber}"/>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
