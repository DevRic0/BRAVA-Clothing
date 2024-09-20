<%@ page import="model.cliente.Cliente" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link href="<c:url value="/css/navbar.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/crm.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/crm2.css"/>" rel="stylesheet">
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Profilo Utente"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/views/partials/headerLoggato.jsp">
        <jsp:param name="title" value=""/>
    </jsp:include>
    <style>
        body{
            background-color: var(--cream);
        }

        .content{
            margin-top: 30px;
        }

        input{
             opacity: 35%;
         }
    </style>
</head>
<body>
<br><br>
<section class="content grid-y">
    <fieldset class="grid-y cell w50 login">
        <legend> Profilo:</legend>
        <form action="${pageContext.request.contextPath}/cliente/update" method="post" class="agg" onsubmit="return validatePass()">
            <%Cliente c = (Cliente) session.getAttribute("profilo");%>
            <input type="hidden" name="id" value="<%=c.getIdCliente()%>">
            <span>Nome: </span>
            <input type="text" value="<%=c.getNome()%>" readonly name="nome"><br>
            <span>Cognome: </span>
            <input type="text" value="<%=c.getCognome()%>" readonly name="cognome"><br>
            <span>Email: </span>
            <input type="text" value="<%=c.getEmail()%>" readonly name="email"><br>
            <span>Username: </span>
            <input type="text" value="<%=c.getUsername()%>" readonly name="username"><br>
            <span>Password: </span>
            <input type="password" value="<%=c.getPassword()%>" readonly name="password" id="password"><br>
            <span>Indirizzo: </span>
            <input type="text" value="<%=c.getIndirizzo()%>" readonly name="indirizzo"><br>
            <span>Telefono: </span>
            <input type="text" value="<%=c.getTelefono()%>" readonly name="telefono"><br><br>
            <button type="submit" onclick="salva()" class="btn primary">Salva</button>
            <button type="button" id="mod" class="btn primary">Modifica</button>
        </form>
    </fieldset>
</section>

<section class="content grid-y" id="main">
    <fieldset>
        <legend> Ordini:</legend>
        <table class="allOrders">
            <tr>
                <th>ID Ordine</th>
                <th>Data Inserimento</th>
                <th>IVA</th>
            </tr>
            <c:forEach var="order" items="${listaOrd}">
                <tr>
                    <td>
                        <a href="<%=request.getContextPath()%>/cliente/showord?id=${order.idOrdine}">${order.idOrdine}</a>
                    </td>
                    <td>${order.dataInserimento}</td>
                    <td>${order.iva}</td>
                </tr>
            </c:forEach>
        </table>
    </fieldset>
</section>


<script>
    $(document).ready(function () {
        $("#mod").click(function () {
            $("input").removeAttr("readonly");
            $("input").css("opacity", "100%");
        })
    });

    function salva() {
        var elemento = document.getElementsByTagName("input");
        var i;
        for (i = 0; i < elemento.length; i++)
            elemento[i].setAttribute("readonly", true);
    }

    function validatePass() {
        var str1 = document.getElementById("password").value;
        var patt1 = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,10}$/;
        var result1 = str1.match(patt1)
        var x = result1;
        if (x== str1 ){
            return true;
        }else {
            alert('Compila Password Correttamente!')
            return false
        }
    }

</script>
</body>
</html>


