package com.greenfoxacademy.baloghdominik.authentication.services;

import org.springframework.stereotype.Service;

/**
 * Created by baloghdominik on 2018. 04. 26..
 */
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

@Service
public class TranslateString {

    public String translateIt(String toTranslate) throws Exception {
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        String text = toTranslate;

        Translation translation =
                translate.translate(
                        text,
                        TranslateOption.sourceLanguage("en"),
                        TranslateOption.targetLanguage("hu"));


       return translation.getTranslatedText();
    }
}
