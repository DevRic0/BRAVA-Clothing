<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <link href="<c:url value="/css/crm2.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/crm.css"/>" rel="stylesheet">
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Dashboard Admin"/>
    </jsp:include>
    <style>
        .dash {
            display: flex;
            position: relative;
            flex-direction: column;
            align-items: center;
            border: solid 5px var(--cream);
            border-radius: 10px;
            width: 50%;
            float: left;
            background-color: var(--darkcream);
            margin: 0 auto;
            padding: 24px;
            box-sizing: border-box;
        }

        @media screen and (max-width: 768px) {
            .dash {
                display: flex;
                position: relative;
                align-items: center;
                border: solid 5px var(--cream);
                border-radius: 5px;
                width: 100%;
                background-color: var(--darkcream);
                padding: 24px;
                box-sizing: border-box;
                margin: 0 auto;
            }
        }

        .dash:link {
            background-color: var(--darkcream);
        }

        .dash:visited {
            background-color: var(--darkcream);
        }

        .dash:hover {
            background-color: var(--darkred);
            color: var(--cream);
            cursor: pointer;
        }

        .dash:active {
            background-color: var(--red);
            cursor: pointer;
        }
    </style>

</head>

<body style="background-color: var(--cream)">
<main class="app ">
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
            <div>
                <button class="openbtn" onclick="toggleNav()"><img src="<%=request.getContextPath()%>/icons/menu.png">
                </button>
            </div>

            <div class="dash" onclick="window.open('<%=request.getContextPath()%>/crm/order', '_self');">
                <h4>Gestione Ordini</h4>
                <p>N° Ordini: <%=request.getAttribute("numeroOrdini")%>
                </p>
            </div>
            <div class="dash" onclick="window.open('<%=request.getContextPath()%>/crm/customer', '_self');">
                <h4>Gestione Clienti</h4>
                <p>N° Cliente: <%=request.getAttribute("numeroClienti")%>
                </p>
            </div>
            <div class="dash" onclick="window.open('<%=request.getContextPath()%>/crm/category', '_self');">
                <h4>Gestione Categorie</h4>
                <p>N° Categorie: <%=request.getAttribute("numeroCategorie")%>
                </p>
            </div>
            <div class="dash" onclick="window.open('<%=request.getContextPath()%>/crm/product', '_self');">
                <h4>Gestione Prodotti</h4>
                <p>N° Prodotti: <%=request.getAttribute("numeroProdotti")%>
                </p>
            </div>
        </div>
    </section>
</main>


<script>
    let status = false;

    function toggleNav() {
        if (status) {
            document.getElementById("sideBar").style.width = "0";
            document.getElementById("main").style.marginLeft = "0";
            status = false;
        } else {
            document.getElementById("sideBar").style.width = "250px";
            document.getElementById("main").style.marginLeft = "250px";
            status = true;
        }
    }

    function modify() {
        document.getElementsByTagName("input").readOnly = true
    }
</script>

</body>
</html>
