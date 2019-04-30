<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Getting Started: Handling Form Submission</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <c:url value="/css/style.css" var="jstlCssStyle"/>
    <link href="${jstlCssStyle}" rel="stylesheet"/>

    <c:url value="/css/checkbox.css" var="jstlCssCheckbox"/>
    <link href="${jstlCssCheckbox}" rel="stylesheet"/>



</head>
<body>
<div class="container">
    <header>
        <nav>
            <ul>
                <li><a href="./keywords">Выделение ключевых слов</a></li>
                <li class="active"><a href="./wordstat">Яндекс вордстат</a></li>
                <li><a href="./naturalnesstext">Естественность текста</a></li>
            </ul>
        </nav>
    </header>

    <div>
        <h1>Статистика из поисковой системы Яндекс</h1>

        <form:form method="post" modelAttribute="wsModel" action="savefile">

            <div class="list">
                <table align="center">
                        <tr>
                        <td>Статистика по словам</td>
                        <td>Показов в месяц</td>
                        </tr>

                    <c:forEach var="word" items="${wordstatStatistic}" varStatus="theCount">
                        <tr>
                            <td>
                                <form:checkbox path="checkedItems" value="${theCount.index}" class="checkbox"
                                               id="checkbox${theCount.index}"/>
                                <label for="checkbox${theCount.index}"><c:out
                                        value="${word.phrase}"/></label>
                            </td>
                                <td class="rt">${word.shows}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div align="center">
                <input id="send" type="submit" align="center"/><label for="send" class="btn">Сохранить Excel файл</label>
            </div>
        </form:form>
    </div>
    <footer class="main-footer"><p align="right">2019 г. МАИ.</p></footer>

</div>
</body>
</html>
