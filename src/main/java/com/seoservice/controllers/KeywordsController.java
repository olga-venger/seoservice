package com.seoservice.controllers;

import com.pullenti.PulentiSDK;
import com.seoservice.model.KeywordsForm;
import com.seoservice.model.TextForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//Вывод окна для ввода текста для выделения ключевых слов
@Controller
public class KeywordsController {

    @RequestMapping(value = "/keywords", method = RequestMethod.GET)
    public String doGet(Model model) {
        model.addAttribute("textForm", new TextForm());
        return "keywords";
    }

    @RequestMapping(value = "/getKeywords", method = RequestMethod.POST)
    public String doPost(@ModelAttribute TextForm textForm, Model model) throws Exception {
        model.addAttribute("content", PulentiSDK.getKeywords(textForm.getTextString()));
        model.addAttribute("keywordsForm", new KeywordsForm());
        return "result";
    }

    @RequestMapping(value = "/getKeywords", method = RequestMethod.GET)
    public String getKeywords(Model model){
        model.addAttribute("keywordsForm", new KeywordsForm());
        return "result";
    }
}