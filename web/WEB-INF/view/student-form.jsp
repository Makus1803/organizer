<%--
  Created by IntelliJ IDEA.
  User: Mat
  Date: 2019-10-16
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Student Registration Form</title>
</head>
<body>
    <form:form action="processForm" modelAttribute="student">

        First name: <form:input path="firstName" />
        <br><br>

        Last name: <form:input path="lastName" />

        <br><br>
        Country:

        <form:select path="country">
            <form:options items="${student.countryOption}" />
        </form:select>

        <br><br>

        Fav programming language:

        C# <form:radiobutton path="favouriteLanguage" value="C#" /><br>
        C++ <form:radiobutton path="favouriteLanguage" value="C++" /><br>
        JAVA <form:radiobutton path="favouriteLanguage" value="JAVA" /><br>
        PYTHON <form:radiobutton path="favouriteLanguage" value="PYTHON" /><br>

        <br><br>

        Fav operating systems:

        <form:checkboxes path="favouriteSystems" items="${student.systemOption}" />

        <input type="submit" value="Submit" />

    </form:form>
</body>
</html>
