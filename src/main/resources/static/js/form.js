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
