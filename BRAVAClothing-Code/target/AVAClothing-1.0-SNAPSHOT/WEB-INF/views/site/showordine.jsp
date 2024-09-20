<%@ page import="model.prodotto.Prodotto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.prodottoordine.ProdottoOrdine" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/css/navbar.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/sign.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/prodotti.css"/>" rel="stylesheet">
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Show Ordine"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/views/partials/headerLoggato.jsp">
        <jsp:param name="title" value=""/>
    </jsp:include>
   <style>
       .content{
           margin-top: 120px;
       }
   </style>
</head>
<body>
    <%ArrayList<Prodotto> showOrdine=(ArrayList<Prodotto>) request.getSession().getAttribute("showOrdine");
      ArrayList<ProdottoOrdine> result =(ArrayList<ProdottoOrdine>) request.getSession().getAttribute("result");
      double tot=(Double) request.getSession().getAttribute("totOrdine");
      DecimalFormat df = new DecimalFormat("#.00");
    %>

    <div class="content">
        <div class="grid-y login">
            <h2>Riepilogo Ordine:</h2>
            <div class="grid-x">
                <p><b>Ordine n°</b>${idOrdine}  <b>Totale:</b>€<%=df.format(tot)%></p>
            </div>
        </div>
    </div>

    <div class="products">
        <%for (int i=0; i<showOrdine.size(); i++){%>
            <div class="product-card">
                <div class="product-image">
                    <img src="data:image/jpg;base64,<%=showOrdine.get(i).getBase64Image()%>" width="350" height="370">
                </div>
                <div class="product-info">
                    <h5>Nome: </h5><h6> <%=showOrdine.get(i).getNome()%></h6><br>
                    <h5>Prezzo:</h5><h6>€<%=df.format(showOrdine.get(i).getPrezzo())%></h6><br>
                    <h5>Descrizione Dettagliata: </h5><h6> <%=showOrdine.get(i).getDescrizioneDettagliata()%></h6><br>
                    <h5>Quantità: </h5><h6> <%=result.get(i).getQuantita()%></h6>
                </div>
            </div>
        <%}%>
    </div>
</body>
</html>
