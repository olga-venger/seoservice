<%@ page import="java.util.Map" %>
<%@ page import="com.seoservice.naturaltextanalizer.WordRankZipf" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Естественность текста</title>


    <c:url value="/js/form.js" var="jstlJs"/>
    <script type="text/javascript" src="${jstlJs}"></script>


    <c:url value="/css/style.css" var="jstlCss"/>
    <link href="${jstlCss}" rel="stylesheet"/>

</head>

<body>
<div class="container">
    <header>

        <nav>
            <ul>
                <li><a href="./keywords">Выделение ключевых слов</a></li>
                <li><a href="./wordstat">Статистика по КС</a></li>
                <li class="active"><a href="./naturalnesstext">Естественность текста</a></li>
            </ul>
        </nav>
    </header>


    <section class="content">

        <div class="container-1">
            <h1>Естественность текста</h1>

            <p class="information">Проверка «естественности» текста необходима для того чтобы после добавления в
                текст ключевых слов из составленного семантического ядра он оставался читаемым как для пользователя,
                так и для поискового робота, который не смог бы расценить текст как заспамленный.</p>
            <p class="information">Текст проверяется по следующим показателям: средняя длина слова в тексте, средняя
                длина предложения в
                тексте, проверка на пары одинаковых слов, идущих подряд, Закон Ципфа, а также относительная частота
                вхождения каждого слова в тексте.</p>

            <div class="error" id="non-valid-form">Недопустимое значение</div>

            <%--@elvariable id="textForm" type=""--%>
            <form:form method="post" modelAttribute="textForm" action="naturalnesstext"
                       class="text-form">

                <p><form:textarea type="text" path="textString"
                                  placeholder="Введите сюда ваш текст"
                                  id="fileOutput"/>
                </p>
                <p>
                    <label for="choose-file" class="btn">Выбрать файл</label><input id="choose-file" type="file"
                                                                                    onchange="processFiles(this.files)"/>
                    <label for="send-txt" class="btn">Анализировать</label><input id="send-txt" type="submit"
                                                                                  onclick="return validateTextForm(document.getElementById('fileOutput'), 10, 4000);"/>
                </p>
            </form:form>


            <c:if test="${textInfo != null}">

            <div class="box">
                <div class="error" id="attention-red">Обратите внимание, что некоторые значения не соответствуют
                    рекомендуемым,<br>за
                    дополнительной информацией обратитесь к <a style="color: white; text-decoration: underline;"
                            onclick="document.getElementById('message').style.display = 'inline-block';">справке</a>
                </div>
                <div class="attention" id="message"></div>

                <%--====================================================================================--%>

                <div style="width: 40%;">
                    <h2>Информация по тексту</h2>
                    <table>
                        <tr>
                            <td>Количество предложений:</td>
                            <td align="center">${textInfo.numberOfSentences}</td>
                        </tr>
                        <tr>
                            <td>Количество слов:</td>
                            <td align="center">${textInfo.numberOfWord}</td>
                        </tr>
                        <tr>
                            <td>Количество символов <br>(без пробела):</td>
                            <td align="center">${textInfo.numberOfCharsWithoutSpace}</td>
                        </tr>
                        <tr>
                            <td>Средняя длина слова:
                                <a><b data-tooltip="Согласно статистике нормальная длина слова 5,28 символа">?</b></a>
                            </td>

                            <td align="center" id="a-word">${textInfo.averageWordLength}</td>

                        </tr>
                        <tr>
                            <td>Средняя длина предложения: <a><b data-tooltip="Согласно статистике нормальная длина предложения
в тексте 10,38 слов">?</b></a>
                            </td>


                            <td align="center" id="a-sentence">${textInfo.averageSentenceLength}</td>
                        </tr>
                        <tr>
                            <td>Индекс удобочитаемости: <a><b data-tooltip="
                            Mера определения сложности восприятия текста читателем
<br>0-15 – очень трудные тексты;
<br>20-40 – трудные тексты;
<br>45-55 – достаточно трудные тексты;
<br>60 – средние тексты;
<br>70-75 – достаточно простые тексты;
<br>80-85 – простые тексты;
<br>90-100 – очень простые тексты.
<br>Стремитесь к тому чтобы ваши тексты были легко воспринимаемы читателем (Индекс > 55)">?</b></a></td>

                            <td id="index-flesh" align="center">${textInfo.indexFlesh}</td>
                        </tr>
                        <tr>
                            <td>Естественность Вашего текста:
                                <a><b data-tooltip="Определяет естественность текста по закону Ципфа.
Хороший уровень естественности - это от 50% и выше.">?</b></a>
                            </td>
                            <td align="center" id="percent-zipf">${textInfo.percentTextNaturalZipf}</td>
                        </tr>
                            <%--<tr>--%>
                            <%--<td>Количество слогов</td>--%>
                            <%--<td>${textInfo.numberOfSyllables}</td>--%>
                            <%--</tr>--%>
                        <tr>
                            <td>Количество повторов подряд</td>
                            <td align="center" id="dublicate">${textInfo.checkDuplicationWords()}</td>
                        </tr>
                    </table>
                </div>
                    <%--====================================================================================--%>
                <div style="width: 60%">
                    <h2>Естественность по Ципфу</h2>
                    <table id='zipf-table'>
                        <tr>
                            <th style="width: 5%">№</th>
                            <th style="width: 30%">Слово</th>
                            <th>Кол-во вхождений</th>
                            <th>Кол-во по Ципфу</th>
                            <th>% соответствия</th>
                            <th>Относительная частота</th>
                        </tr>
                        <c:forEach var="element" items="${listZipf}" varStatus="i">
                            <c:if test="${listZipf.get(i.index).frequency > 1}">
                                <tr>
                                    <td>${i.count}</td>
                                    <td class="td-left">${listZipf.get(i.index).value}</td>
                                    <td>${listZipf.get(i.index).wordRankZipf.currentFrequency}</td>
                                    <td>${listZipf.get(i.index).wordRankZipf.frequency}</td>
                                    <c:if test="${listZipf.get(i.index).wordRankZipf.percent < 50}">
                                        <td class="red-cell">${listZipf.get(i.index).wordRankZipf.percent} %</td>
                                    </c:if>
                                    <c:if test="${listZipf.get(i.index).wordRankZipf.percent >= 50}">
                                        <td class="green-cell">${listZipf.get(i.index).wordRankZipf.percent} %</td>
                                    </c:if>
                                    <c:if test="${listZipf.get(i.index).getNausea(textInfo.numberOfWord) > 5}">
                                        <td class="red-cell">${listZipf.get(i.index).getNausea(textInfo.numberOfWord)} %</td>
                                    </c:if>
                                    <c:if test="${listZipf.get(i.index).getNausea(textInfo.numberOfWord) <= 5}">
                                        <td class="green-cell">${listZipf.get(i.index).getNausea(textInfo.numberOfWord)} %</td>
                                    </c:if>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
        </c:if>
    </section>
    <footer class="main-footer">
        <p align="right">2019 г. МАИ.</p>
    </footer>
</div>
</body>
</html>
