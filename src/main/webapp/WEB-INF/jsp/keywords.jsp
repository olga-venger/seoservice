<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Выделение ключевых слов</title>

    <c:url value="/js/form.js" var="jstlJs"/>
    <script type="text/javascript" src="${jstlJs}"></script>

    <c:url value="/css/style.css" var="jstlCss"/>
    <link href="${jstlCss}" rel="stylesheet"/>

</head>

<body>
<div class="container">
    <div>
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
            <h1>Выделение ключевых слов</h1>

            <div id="non-valid-form" class="error">Недопустимое значение</div>
            <form:form method="post" modelAttribute="textForm" action="getKeywords" class="text-form">
                <p><form:textarea type="text" path="textString" placeholder="Введите текст или загрузите файл в формате .txt (UTF-8)"
                                  id="fileOutput"/></p>
                <p>
                    <label for="choose-file" class="btn">Выбрать файл</label><input id="choose-file" type="file"
                                                                                    onchange="processFiles(this.files)"/>
                    <%--<label for="reset" class="btn">Очистить</label><input id="reset" type="reset" class="btn"--%>
                                                                          <%--onclick="document.getElementById('fileOutput').value='';"/>--%>
                    <label for="send-kw" class="btn">Выделить ключевые слова</label><input id="send-kw" type="submit"
                                                                                           onclick="return validateTextForm(document.getElementById('fileOutput'), 10, 40000)"/>
                </p>
            </form:form>
        </div>
    </div>
    <footer class="main-footer"><p align="right">2019 г. МАИ.</p></footer>
</div>
</body>
</html>
