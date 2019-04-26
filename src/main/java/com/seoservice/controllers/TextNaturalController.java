package com.seoservice.controllers;

import com.seoservice.naturaltextanalizer.TextAnalizer;
import com.seoservice.model.TextForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TextNaturalController {
    @RequestMapping(value = "/naturalnesstext", method = RequestMethod.GET)
    public String showTextRank(Model model) {
        TextAnalizer textAnalizer = new TextAnalizer();
        model.addAttribute("textForm", new TextForm());
        model.addAttribute("listZipf", textAnalizer.getTextAsListWords());
        return "naturalnesstext";
    }

    @RequestMapping(value = "/naturalnesstext", method = RequestMethod.POST)
    public String getTextRank(@ModelAttribute TextForm textForm, Model model) throws Exception {

        TextAnalizer textAnalizer = new TextAnalizer(textForm.getTextString());
        model.addAttribute("textInfo", textAnalizer);
        model.addAttribute("listZipf", textAnalizer.getTextAsListWords());

        /*
        TextNatural textNatural = new TextNatural(textForm.getTextString());
        TextAnalizer textAnalizer = new TextAnalizer(textForm.getTextString());
        System.out.println(textNatural.getPercentTextNatural());
        model.addAttribute("textNatural", textNatural);
        model.addAttribute("listZipf", textNatural.getListZipf());
        model.addAttribute("textInfo", textAnalizer);
        model.addAttribute("FlashIndex", textAnalizer.getFleshIndex());
        */
        return "naturalnesstext";
    }
}
