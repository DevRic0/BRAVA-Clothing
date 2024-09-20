<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="<c:url value="/css/navbar.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/sign.css"/>" rel="stylesheet">
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Info"/>
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

    <style>
        body{
            background-color: var(--cream);
        }
        .content{
            margin-top: 110px;
            z-index: -1;
        }
        .infotext {
            font-family: Raleway;
            color: var(--darkcream);
        }

        .ainfo:link, .ainfo:visited {
            color: var(--darkcream);
        }

        .ainfo:active {
            color: #c7c1b8;
        }

    </style>
</head>
<body>
<br><br>
<div class="content grid-y">
    <fieldset class="grid-y cell w50 login" style="z-index: -1">
        <legend>Obiettivo</legend>
        <p class="infotext">
            Il sito si pone come obiettivo quello di diventare un punto di riferimento tra gli e-commerce del
            fast fashion. Prendendo spunto dalle features più interessanti dei siti competitors, AVA
            Clothing propone un’interfaccia semplice, minimale e funzionale sia dal punto di vista
            dell’utente, sia dal punto di vista amministrativo. All’interno del catalogo saranno presenti
            capi d’abbigliamento rivolti a un pubblico giovane che cerca uno stile casual senza
            rinunciare alla qualità.
            Allo scopo di coinvolgere sia utenti che utilizzano smartphone sia utenti che utilizzano
            computer, il sito web è strutturato in modo responsive.
        </p>
    </fieldset>
    <fieldset class="grid-y cell w50 login">
        <legend>Orari di Apertura</legend>
        <p class="infotext">Lunedì – Venerdì 08.00 – 21.00</p>
        <p class="infotext">Sabato e Domenica 09.00 – 17.00</p>
        <p class="infotext">Giorni festivi 09.00 – 17.00</p>
    </fieldset>
    <fieldset class="grid-y cell w50 login">
        <legend>Contattaci</legend>
        <p class="infotext">Puoi trovarci su <a class="ainfo" target="_blank" href="https://www.instagram.com" methods="_blank">Instagram</a>
            e su <a class="ainfo" target="_blank" href="https://www.facebook.com">Facebook</a>.</p>
    </fieldset>
</div>
</body>
</html>
