package com.greenfoxacademy.baloghdominik.authentication.services;

/**
 * Created by baloghdominik on 2018. 04. 26..
 */

import org.springframework.stereotype.Service;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

@Service
public class TranslateString {

    public String translateIt(String toTranslate, String lang) throws Exception {
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        String text = toTranslate;

        Translation translation =
                translate.translate(
                        text,
                        TranslateOption.sourceLanguage("en"),
                        TranslateOption.targetLanguage(lang));

        String translated = new String(translation.getTranslatedText().getBytes(), "UTF8");

        String replace = translated.replace("&quot;", "\"");

       return replace;
    }
}
