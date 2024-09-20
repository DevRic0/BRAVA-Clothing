<%@ page import="java.text.DecimalFormat" %>
<%@ page import="model.prodotto.Prodotto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="<c:url value="/css/navbar.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/prodotti.css"/>" rel="stylesheet">
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Products"/>
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
<div>
    <div class="dropdown">
        <div>
            <button class="dropbtn" id="cat">Seleziona Categoria</button>
        </div>
        <div class="content-product">
            <% request.getSession().setAttribute("genere", "uomo");%>
            <c:forEach items="${listaCat}" var="categoria">
                <a href="<%=request.getContextPath()%>/prodotto/selezione?id=${categoria.idCategoria}">${categoria.nome}</a>
            </c:forEach>
        </div>
    </div>
</div>

<h1>${categoriaSelected}</h1>

<section class="products">
    <%DecimalFormat df = new DecimalFormat("#.00");
    int i=0;
    ArrayList<Prodotto> prodotti = (ArrayList<Prodotto>) request.getSession().getAttribute("prodotti");%>

    <c:forEach items="${prodotti}" var="prodotto" >
        <%Prodotto prodotto = prodotti.get(i++);%>
        <div class="product-card" onclick="window.open('<%=request.getContextPath()%>/cliente/product?id=${prodotto.idProdotto}', '_self');">
            <div class="product-image">
                <img src="data:image/jpg;base64,${prodotto.base64Image}" width="350" height="370">
            </div>
            <div class="product-info">
                <h5>${prodotto.nome}</h5>
                <h6>€<%=df.format(prodotto.getPrezzo())%></h6>
            </div>
        </div>
    </c:forEach>
    <%i=0;%>
</section>
</body>
</html>
