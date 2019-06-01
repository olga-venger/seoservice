//чтение текстового файла
function processFiles(files) {
    var file = files[0];
    var reader = new FileReader();
    reader.readAsText(file);
    reader.onload = function (e) {
        // Когда это событие активируется, данные готовы.
        // Вставляем их в страницу в элемент <div>
        var output = document.getElementById('fileOutput');
        output.value = e.target.result;
    };
};

//проверка текстового поля, на количество символов
function validateTextForm(textarea, minValue, maxValue) {
    if (textarea.value != 0) {
        var stringText = textarea.value.replace(/\n/, " ").replace(/[^а-яёА-ЯЁ]\W/gi, " ").replace(/\s{2,}/gi, " ").replace(/ $/, "").replace(/^ /, "");
        var text_array = stringText.split(" ");
        if (text_array.length > minValue && text_array.length < maxValue) {
            return true;
        }
        else {
            //alert("Ваш текст должен быть больше 10 слов!");

            if (text_array.length < minValue) {
                document.getElementById('non-valid-form').innerText = 'Ваш текст должен быть больше ' + minValue + ' слов!';
            }
            else if (text_array.length > maxValue) {
                document.getElementById('non-valid-form').innerText = 'Ваш текст должен быть меньше ' + maxValue + ' слов!';
            }
            document.getElementById('non-valid-form').style.display = "block";
            return false;
        }
    } else {
        document.getElementById('non-valid-form').style.display = "block";
        document.getElementById('non-valid-form').innerText = 'Поле не может быть пустым!';
        return false;
    }
};


var windowOnloadAdd = function (event) {
    if (window.onload) {
        window.onload = window.onload + event;
    } else {
        window.onload = event;
    }
    ;
};
//проверка значений в таблице
windowOnloadAdd(function () {
    var red = "rgba(178, 34, 34, 0.5)";
    var green = "rgba(34, 139, 34, 0.5)";
    var yellow = "rgba(266, 165, 0, 0.5)";

    var message = "";
    var aWord = document.getElementById('a-word');
    var aSentences = document.getElementById('a-sentence');
    var indexFlesh = document.getElementById('index-flesh');
    var percentZipf = document.getElementById('percent-zipf');
    var dublicate = document.getElementById('dublicate');

    //========= Ср. кол. слов ========================
    if (aWord.innerText < 4 || aWord.innerText > 8) {
        aWord.style.background = red;
        message += "Слова в тексте слишком ";
        if (aWord.innerText < 4) {
            message += "короткие.\n";
        }
        else {
            message += "длинные.\n";
        }
    }
    else {
        aWord.style.background = green;
    }

    //========= Ср. кол. предложений ========================
    if (aSentences.innerText < 6 || aSentences.innerText > 17) {
        aSentences.style.background = red;
        message += "Предложения в тексте слишком ";
        if (aSentences.innerText < 6) {
            message += "короткие.\n";
        }
        else {
            message += "длинные.\n";
        }
    }
    else {
        aSentences.style.background = green;
    }

    //========= Флеш ========================
    if (indexFlesh.innerText < 55) {
        indexFlesh.style.backgroundColor = red;
        message += "Возможно, ваш текст слишком сложно читать.\n";
    }
    else {
        indexFlesh.style.backgroundColor = green;
    }

    //========= Ципф ========================
    if (percentZipf.innerText < 50) {
        percentZipf.style.backgroundColor = red;
        message += "Поисковая система может счесть ваш текст неестественным.";
    }
    else {
        percentZipf.style.backgroundColor = green;
    }

    //========= пары одинаковых слов ========================
    if (dublicate.innerText < 1) {
        dublicate.style.backgroundColor = green;
    }
    else if (dublicate.innerText < 5) {
        dublicate.style.backgroundColor = yellow;
        message += "В тексте встречаются дубликаты слов подряд, постарайтесь убрать.";
    }
    else {
        dublicate.style.backgroundColor = red;
        message += "В тексте слишком много слов идущих подряд, постарайтесь убрать.";
    }

    //============= Относительная частота слова


    var table;

    //========= Вывод предупреждающего сообщения ========================
    if (message != "") {
        document.getElementById('message').innerText = message;
        document.getElementById('message').style.display = "none";
        document.getElementById('attention').style.display = 'inline-block';
    }
    else {
        document.getElementById('attention').style.display = "none";
        document.getElementById('message').style.display = "none";
    }
});

//всплывающая подсказка
var showingTooltip;

document.onmouseover = function (e) {
    var target = e.target;

    var tooltip = target.getAttribute('data-tooltip');
    if (!tooltip) return;

    var tooltipElem = document.createElement('div');
    tooltipElem.className = 'tooltip';
    tooltipElem.innerHTML = tooltip;
    document.body.appendChild(tooltipElem);

    var coords = target.getBoundingClientRect();

    var left = coords.left + (target.offsetWidth - tooltipElem.offsetWidth) / 2;
    if (left < 0) left = 0; // не вылезать за левую границу окна

    var top = coords.top - tooltipElem.offsetHeight - 5;
    if (top < 0) { // не вылезать за верхнюю границу окна
        top = coords.top + target.offsetHeight + 5;
    }

    tooltipElem.style.left = left + 'px';
    tooltipElem.style.top = top + 'px';

    showingTooltip = tooltipElem;
};

document.onmouseout = function (e) {

    if (showingTooltip) {
        document.body.removeChild(showingTooltip);
        showingTooltip = null;
    }

};
function validateTextFormWS(textarea) {
    alert("call");
    if (textarea.value != 0) {
         var reg = /[^a-яА-Яa-zA-Z0-9 ]\n]/m;
         if(reg.test(textarea.value)){
             document.getElementById('non-valid-form').style.display = "block";
             document.getElementById('non-valid-form').innerText = 'Допустимы только символы русского и латинского алфавита и цифры!';
             return false;
         }
        return true;
    } else {
        document.getElementById('non-valid-form').style.display = "block";
        document.getElementById('non-valid-form').innerText = 'Поле не может быть пустым!';
        return false;
    }
};
