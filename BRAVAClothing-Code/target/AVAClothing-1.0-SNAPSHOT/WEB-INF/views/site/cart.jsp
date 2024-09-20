<%@ page import="java.util.ArrayList" %>
<%@ page import="model.prodotto.Prodotto" %>
<%@ page import="model.cliente.Cliente" %>
<%@ page import="model.prodottoordine.ProdottoOrdine" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/css/navbar.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/prodotti.css"/>" rel="stylesheet">
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Carrello"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/views/partials/headerLoggato.jsp">
        <jsp:param name="title" value=""/>
    </jsp:include>
    <style>
        .totale span{
            left:20px;
            font-style: inherit;

        }
    </style>
</head>
<body>
     <br>
    <%ArrayList<Prodotto> lista=(ArrayList<Prodotto>) request.getSession().getAttribute("cart");
      Cliente c=(Cliente) request.getSession().getAttribute("profilo");
      ArrayList<ProdottoOrdine> elenco =(ArrayList<ProdottoOrdine>) request.getSession().getAttribute("elencocart");
      double totale=0;
      Double tot=(Double) request.getSession().getAttribute("totale");
      if(tot!=null)
          totale=tot;
      DecimalFormat df = new DecimalFormat("0.00");
    %>
     <br> <br>
    <div class="totale">
        <span>Totale Carrello: €<%=df.format(totale)%></span>
    </div>
    <%if (lista.isEmpty()){%>
    <div class="card">
        <p>Carrello Vuoto</p>
    </div>
    <%}else {
        for (int i=0; i<lista.size(); i++){%>
        <form method="post">
            <div class="product-card">
                <div class="product-image">
                    <img src="data:image/jpg;base64,<%=lista.get(i).getBase64Image()%>" width="350" height="370">
                </div>
                <div class="product-info">
                    <h5>Nome: </h5><h6><%=lista.get(i).getNome()%></h6><br>
                    <h5>Prezzo: </h5><h6>€<%=df.format(lista.get(i).getPrezzo())%></h6><br>
                    <h5>Descrizione Dettagliata: </h5><h6><%=lista.get(i).getDescrizioneDettagliata()%></h6><br>
                    <h5>Quantità: </h5><h6><%=elenco.get(i).getQuantita()%></h6>
                    <button type="submit" class="delete btn primary" name="delete" value="<%=lista.get(i).getIdProdotto()%>">Elimina</button>
                </div>
            </div>
            <%}
        }%>
            <div>
                <button type="submit" class="buy btn primary" name="buy">Acquista</button>
            </div>
        </form>
<script>
    $(document).ready(function(){
        $(".delete").click(function () {
            $('form').attr('action', '${pageContext.request.contextPath}/cliente/deletepro');
        })
        $(".buy").click(function () {
            $('form').attr('action', '${pageContext.request.contextPath}/cliente/acquistacarrello');
        })
    });
</script>

</body>
</html>
