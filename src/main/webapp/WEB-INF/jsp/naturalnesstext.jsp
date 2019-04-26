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

    <script type="text/javascript">
        function validateTextForm(textarea) {
            valid = true;
            if (textarea.value != 0) {
                var stringText = textarea.value.replace(/\n/, " ").replace(/[^а-яёА-ЯЁ]\W/gi, " ").replace(/\s{2,}/gi, " ").replace(/ $/, "").replace(/^ /, "");
                var text_array = stringText.split(" ");
                if (text_array.length > 100) {
                    return valid;
                }
                else {
                    document.getElementById('non-valid-form').style.display = "block";
                    document.getElementById('non-valid-form').innerText = 'Ваш текст должен быть больше 100 слов!';
                    return false;
                }
            } else {
                document.getElementById('non-valid-form').style.display = "block";
                document.getElementById('non-valid-form').innerText = 'Поле не может быть пустым!';
                valid = false;
                return valid;
            }
        };
    </script>

</head>

<body>
<div class="container">
    <header>

        <nav>
            <ul>
                <li><a href="/keywords">Выделение ключевых слов</a></li>
                <li><a href="/wordstat">Яндекс вордстат</a></li>
                <li class="active"><a href="/naturalnesstext">Естественность текста</a></li>
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
                        <%--<label for="reset" class="btn">Очистить</label><input id="reset" type="reset" class="btn"--%>
                                                                              <%--onclick="alert(document.getElementById('fileOutput').innerHTML);--%>
                                                                              <%--document.getElementById('fileOutput').innerHTML='';"/>--%>
                        <label for="send-txt" class="btn">Анализировать</label><input id="send-txt" type="submit"
                                                                                      onclick="return validateTextForm(document.getElementById('fileOutput'));"/>
                    </p>
                </form:form>


                <c:if test="${textInfo != null}">

                    <div class="box">

                            <%--====================================================================================--%>

                        <div style="width: 40%">
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
                                    <td>Средняя длина предложения: <a
                                            title="Согласно статистике нормальная длина предложения
в тексте 10,38 слов"><b>?</b></a>
                                    </td>
                                    <td align="center">${textInfo.averageSentenceLength}</td>
                                </tr>
                                <tr>
                                    <td>Средняя длина слова: <a
                                            title="Согласно статистике нормальная длина слова 5,28 символа"><b>?</b></a>
                                    </td>
                                    <td align="center">${textInfo.averageWordLength}</td>
                                </tr>
                                <tr>
                                    <td>Индекс удобочитаемости: <a title="
                            Mера определения сложности восприятия текста читателем
0-15 – очень трудные тексты;
20-40 – трудные тексты;
45-55 – достаточно трудные тексты;
60 – средние тексты;
70-75 – достаточно простые тексты;
80-85 – простые тексты;
90-100 – очень простые тексты.
Стремитесь к тому чтобы ваши тексты были
легко воспринимаемы читателем
(Индекс > 55)"><b>?</b></a></td>
                                    <td align="center">${textInfo.indexFlesh}</td>
                                </tr>
                                <tr>
                                    <td>Естественность Вашего текста:
                                        <a title="Определяет естественность текста по закону Ципфа.
Хороший уровень естественности - это от 50% и выше."><b>?</b></a>
                                    </td>
                                    <td align="center">${textInfo.percentTextNaturalZipf}</td>
                                </tr>
                            </table>
                        </div>
                            <%--====================================================================================--%>
                        <div style="width: 60%">
                            <h2>Естественность по Ципфу </h2>
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
                                            <td>${listZipf.get(i.index).wordRankZipf.percent} %</td>
                                            <td>${listZipf.get(i.index).getNausea(textInfo.numberOfWord)} %</td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </c:if>
            </div>
    </section>
    <footer class="main-footer">
        <p align="right">2019 г. МАИ.</p>
    </footer>
</div>
</body>
</html>
