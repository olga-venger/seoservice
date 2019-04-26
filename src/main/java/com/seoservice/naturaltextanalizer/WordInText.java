    package com.seoservice.naturaltextanalizer;

    import java.math.BigDecimal;
    import java.math.RoundingMode;

    public class WordInText{
        private String value;               // значение слова
        private WordRankZipf wordRankZipf;  // рекомендуемое количество употреблений по Ципфу

        WordInText(String word, WordRankZipf wordRankZipf){
            value = word;
            this.wordRankZipf = wordRankZipf;
        }

        public void setWordRankZipf(WordRankZipf wordRankZipf) {
            this.wordRankZipf = wordRankZipf;
        }

        public WordRankZipf getWordRankZipf() {
            return wordRankZipf;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        /**
         * Частота употребления слова в тексте
         * @return
         */
        public Integer getFrequency(){
            return wordRankZipf.getCurrentFrequency();
        }

        /**
         * Определить тошноту слова в тексте
         * @param textSize - размер текста в словах
         * @return
         */
        public double getNausea(Integer textSize) {
            return new BigDecimal(100.0 * wordRankZipf.getCurrentFrequency()/textSize).setScale(1, RoundingMode.HALF_EVEN).doubleValue();
        }

        /**
         * Количество слогов в слове
         * @param word
         * @return
         */
        public static Integer numberOfSyllables(String word){
            char [] letters = word.toLowerCase().toCharArray();
            int numberOfSyllables = 0;
            for (char letter: letters
            ) {
                //проверка что буква гласная
                if(     //для русского языка
                        letter == 'а' || letter == 'о' || letter == 'э' || letter == 'и' ||
                                letter == 'у' || letter == 'ы' || letter == 'е' || letter == 'ё' ||
                                letter == 'ю' || letter == 'я' ||
                                //для английского языка
                                letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o'||
                                letter == 'u'){
                    numberOfSyllables++;
                }
            }
            return numberOfSyllables;
        }
        public Integer numberOfSyllables(){
            return numberOfSyllables(value);
        }

    }

