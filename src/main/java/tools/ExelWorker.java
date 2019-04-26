package tools;

import DirectApi.Keywords.WordstatItem;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.List;

public class ExelWorker {
    // создание самого excel файла в памяти
    private HSSFWorkbook workbook = new HSSFWorkbook();
    private String filePath;

    public void addSheet(String nameOfSheet, List<WordstatItem> list){
        //Добавить к названию дату и время
        HSSFSheet sheet = workbook.createSheet(nameOfSheet);
        int rows = 0;
        Row row = sheet.createRow(rows);
        row.createCell(0).setCellValue("Статистика для слов:");
        row.createCell(1).setCellValue("Количество показов в месяц");

        for (WordstatItem item: list
             ) {
            createRows(sheet, ++rows, item);
        }
    }

    private void createRows(HSSFSheet sheet, int i, WordstatItem item){
        Row rows = sheet.createRow(i);
        rows.createCell(0).setCellValue(item.getPhrase());
        rows.createCell(1).setCellValue(item.getShows());
    }

    //переписать
    public File saveFile(String nameOfFile) throws IOException {
        // записываем созданный в памяти Excel документ в файл
        File file = new File(nameOfFile + ".xls");
        try (FileOutputStream out = new FileOutputStream(file)) {
            workbook.write(out);
            System.out.println("Excel файл успешно создан!");
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

