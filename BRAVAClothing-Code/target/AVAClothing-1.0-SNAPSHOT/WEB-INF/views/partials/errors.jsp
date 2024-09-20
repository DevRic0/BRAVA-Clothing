<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<link href="<c:url value="/css/reset.css"/>" rel="stylesheet">
<link href="<c:url value="/css/library.css"/>" rel="stylesheet">
<link href="<c:url value="/css/errors.css"/>" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!DOCTYPE html>
<html>
<head>
    <title>Error Page</title>
    <style>
        h1 {
            color: var(--cream);
        }
    </style>
</head>
<body>
<%//exception.printStackTrace(System.out);%>
    <div class="bg">
    </div>
    <div class="errortext">
        <h1>Ops! Sembra che qualcosa sia andato storto. Riprova più tardi.</h1>
        <!--div style="color: #EE4037;">
            <Error message: <%//exception.getMessage();%>
        </div--><br>
        <a href="<%=request.getContextPath()%>/index.jsp">Torna alla Home</a>
    </div>
</body>
</html>
