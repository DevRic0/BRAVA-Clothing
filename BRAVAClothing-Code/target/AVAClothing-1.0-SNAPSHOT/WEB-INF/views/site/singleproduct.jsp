<%@ page import="model.prodotto.Prodotto" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/css/navbar.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/sign.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/prodotti.css"/>" rel="stylesheet">
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Single Product"/>
    </jsp:include>
    <c:choose>
        <c:when test="${log}">
            <jsp:include page="/WEB-INF/views/partials/headerLoggato.jsp">
                <jsp:param name="title" value=""/>
            </jsp:include>
        </c:when>
        <c:otherwise>
            <jsp:include page="/WEB-INF/views/partials/header.jsp">
                <jsp:param name="title" value=""/>
            </jsp:include>
        </c:otherwise>
    </c:choose>
</head>
<body>
    <%Prodotto pro= (Prodotto) request.getSession().getAttribute("prodotto");%>

    <form action="${pageContext.request.contextPath}/cliente/inputcarrello" method="post">
        <div class="products">
            <div class="product-card-single">
                <input type="hidden" name="id" value="<%=pro.getIdProdotto()%>">
                <div class="product-image">
                    <img src="data:image/jpg;base64,<%=pro.getBase64Image()%>" width="500" height="500">
                </div>
                <div class="product-info">
                    <%DecimalFormat df = new DecimalFormat("#.00");%>
                    <h5>Nome:</h5><h6> <%=pro.getNome()%></h6><br> <h5>Prezzo:</h5> <h6>€<%=df.format(pro.getPrezzo())%></h6><br>
                    <h5>Descrizione Dettagliata:</h5><h6> <%=pro.getDescrizioneDettagliata()%></h6><br>
                    <h5>Quantità:</h5><h6> <%=pro.getQuantita()%></h6><br>
                    <h5>Seleziona Quantità</h5>
                    <input type="number" min="1" max="<%=pro.getQuantita()%>" name="selected"><br><br>
                    <button type="submit" class="butAdd btn primary">Aggiungi al Carrello</button>
                </div>
            </div>
        </div>
    </form>
</body>
</html>