package com.gpt.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class GoogleTransService {

    private final Translate translate;

    public GoogleTransService() {
        translate = TranslateOptions.getDefaultInstance().getService();
    }

    public String translateText(String text, String sourceLanguage, String targetLanguage) {
        Translation translation = translate.translate(
                text,
                Translate.TranslateOption.sourceLanguage(sourceLanguage),
                Translate.TranslateOption.targetLanguage(targetLanguage));

        return translation.getTranslatedText();
    }

    // public static void main(String[] args) {
    //     String text = "Hello, world!";
    //     String sourceLanguage = "en";
    //     String targetLanguage = "zh-CN";
    //
    //     System.out.println("*****");
    //     String translatedText = translateText(text, sourceLanguage, targetLanguage);
    //     System.out.println(translatedText);
    // }
}
