<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Mat
  Date: 2019-10-20
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Registration Form</title>

    <style>
        .error {color:red}
    </style>
</head>
<body>
<i>Fill out the form. Asterisk(*) means required.</i>

    <form:form action="preocessForm" modelAttribute="customer">

        First name: <form:input path="firstName" />
        <br><br>

        Last name (*): <form:input path="LastName" />
        <form:errors path="LastName" cssClass="error" />

        <br><br>

        <input type="submit" value="Submit" />

    </form:form>

</body>
</html>
