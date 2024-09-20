
<%@ page import="model.cliente.Cliente" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/css/crm2.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/crm.css"/>" rel="stylesheet">
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Profilo Admin"/>
    </jsp:include>
    <style>
        input{
            opacity: 35%;
        }
    </style>
</head>
<body>
<main class="app">
    <aside class="sidebar" id="sideBar">
        <nav class="grid-y align-center">
            <img src="<%=request.getContextPath()%>/img/logo.png" width="100" height="115">
            <a href="<%=request.getContextPath()%>/crm/dashboard">Dashboard</a>
            <a href="<%=request.getContextPath()%>/crm/profile">Profilo</a>
            <a href="<%=request.getContextPath()%>/crm/customer">Gestione Clienti</a>
            <a href="<%=request.getContextPath()%>/crm/order">Gestione Ordini</a>
            <a href="<%=request.getContextPath()%>/crm/product">Gestione Prodotti</a>
            <a href="<%=request.getContextPath()%>/crm/category">Gestione Categorie</a>
            <a href="<%=request.getContextPath()%>/crm/logout">Logout</a>

        </nav>
    </aside>
    <section class="content grid-y" id="main">
        <div>
            <button class="openbtn" onclick="toggleNav()"><img src="<%=request.getContextPath()%>/icons/menu.png"></button>
        </div>
        <fieldset class="grid-y cell w50 login">
            <legend>Profilo Admin:</legend>
            <form action="${pageContext.request.contextPath}/crm/updatecust" method="post">
                <%Cliente c= (Cliente) session.getAttribute("profilo");%>
                <span>ID: </span>
                <input type="hidden" name="idAdmin" value="<%=c.getIdCliente()%>">
                <span>Nome: </span>
                <input type="text" value="<%=c.getNome()%>" readonly name="nome"><br>
                <span>Cognome: </span>
                <input type="text" value="<%=c.getCognome()%>" readonly name="cognome"><br>
                <span>Email: </span>
                <input type="text" value="<%=c.getEmail()%>" readonly name="email"><br>
                <span>Username: </span>
                <input type="text" value="<%=c.getUsername()%>" readonly name="username"><br>
                <span>Password: </span>
                <input type="password" value="<%=c.getPassword()%>" readonly name="password"><br>
                <span>Indirizzo: </span>
                <input type="text" value="<%=c.getIndirizzo()%>" readonly name="indirizzo"><br>
                <span>Telefono: </span>
                <input type="text" value="<%=c.getTelefono()%>" readonly name="telefono"><br>
                <button type="submit" onclick="salva()" class="btn primary">Salva</button>
                <button type="button" id="mod" class="btn primary">Modifica</button>
            </form>
        </fieldset>
    </section>
</main>

<script>
    let status = false;

    function toggleNav(){
        if(status) {
            document.getElementById("sideBar").style.width = "0";
            document.getElementById("main").style.marginLeft = "0";
            status = false;
        }else{
            document.getElementById("sideBar").style.width = "250px";
            document.getElementById("main").style.marginLeft = "250px";
            status = true;
        }
    }

    $(document).ready(function(){
            $("#mod").click(function () {
                $("input").removeAttr("readonly");
                $("input").css("opacity", "100%");
                read = false;
            })
    });
    function salva(){
        var elemento = document.getElementsByTagName("input");
        var i;
        for(i=0; i<elemento.length; i++)
            elemento[i].setAttribute("readonly", true);
    }
</script>

</body>
</html>