<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/css/ordineeffettuato.css"/>" rel="stylesheet">
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Registered"/>
    </jsp:include>
</head>
<body>
<div class="bg">
</div>
<div class="registeredtext">
    <h1>Il tuo ordine è stato accettato correttamente!</h1>
    <h3>Ti ringraziamo per aver scelto AVAClothing!</h3>
    <br>
    <a href="<%=request.getContextPath()%>/index.jsp">Torna alla home</a>
</div>
</body>
</html>

