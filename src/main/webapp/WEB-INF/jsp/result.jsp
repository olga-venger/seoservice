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
                <li class="active"><a href="./keywords">Выделение ключевых слов</a></li>
                <li><a href="./wordstat">Статистика по КС</a></li>
                <li><a href="./naturalnesstext">Естественность текста</a></li>
            </ul>
        </nav>
    </header>

    <div class="container-1">
        <h1>Ключевые слова</h1>

        <form:form method="post" modelAttribute="keywordsForm" action="getStatisticFromWordstat">

            <div class="list">
                <table align="center">
                    <c:forEach var="word" items="${content}" varStatus="theCount" end="49">
                        <tr>
                            <td><form:checkbox path="keywords" value="${word}" class="checkbox"
                                               id="keyword${theCount.count}"/>
                                <label for="keyword${theCount.count}"><c:out
                                        value="${word}"/></label>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <div align="center">
                <input id="send" type="submit" align="center"/><label for="send" class="btn">Получить статистику по КС</label>
            </div>
        </form:form>
    </div>
    <%--<footer class="main-footer"><p align="right">2019 г. МАИ.</p></footer>--%>

</div>
</body>
</html>