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
                <li class="active"><a href="./wordstat">Статистика по КС</a></li>
                <li><a href="./naturalnesstext">Естественность текста</a></li>
            </ul>
        </nav>
    </header>

    <div class="container-1">
        <h1>Статистика из поисковой системы Яндекс</h1>

        <details>
            <summary>Как составить семантическое ядро сайта?</summary>
            <p>Смотри др.</p>
        </details>
        <details>
            <summary>На что влияет частота запросов?</summary>
            <p class="information">Высокочастотные – это самые популярные запросы, но продвигать сайт по ним сложно, так как существует
                высокая конкуренция. Как правило высокочастотные запросы состоят из 1-2 слов и описывают общую
                тематическую область. Например, запросом «Видеокарта NVIDIA» по данным Яндекс.Wordstat интересовались
                185 238. Но не ясно, что именно пользователи имели ввиду, вводя этот запрос, хотели ли купить новую
                видеокарту или узнать характеристики нынешней?
            </p>
            <p class="information"> Среднечастотные запросы – запросы в которых есть некоторые уточнения по сравнению с высокочастотными
                запросами. Например, запрос «Купить видеокарту NVIDIA» будет среднечастотным им интересовались 18 020
                раз в месяц. Но в запросе всё ещё остаются не ясные детали.
            </p>
            <p class="information">Низкочастотные запросы – запросы, которые максимально конкретизированы. Как правило, если пользователь
                вводит такой запрос, то скорее всего он уже знает, что он хочет купить или посмотреть. И, несмотря на
                то, что их запрашивают реже, но за счёт точности формулировки именно по ним приходят на сайт целевые
                посетители. Например, запросом «купить видеокарту NVIDIA geforce gtx 1060 ti» интересовались 744 раза за
                месяц, но скорее всего именно эти пользователи совершат целевое действие на сайте.
            </p>
        </details>

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
                <input id="send" type="submit" align="center"/><label for="send" class="btn">Сохранить Excel
                файл</label>
            </div>
        </form:form>
    </div>
    <%--<footer class="main-footer"><p align="right">2019 г. МАИ.</p></footer>--%>

</div>
</body>
</html>
