package com.gpt.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 通过API调用谷歌翻译
 */
public class GoogleAPITransService {

    public static String translate(String langFrom, String langTo, String word) throws Exception {

        String url = "https://translate.googleapis.com/translate_a/single?" +
                "client=gtx&" +
                "sl=" + langFrom +
                "&tl=" + langTo +
                "&dt=t&q=" + URLEncoder.encode(word, StandardCharsets.UTF_8);

        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(

                new InputStreamReader(con.getInputStream()));

        String inputLine;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {

            response.append(inputLine);

        }
        in.close();
        System.out.println(response);
        // return parseResult(response.toString());

        return "";
    }


    public static void main(String[] args) throws Exception {
        // https://translation.googleapis.com/language/translate/v2?source=en&target=zh-CN&q=big&key=pub_41281cbcdcbb1fb59c45b43b869d9355
        String translate = translate("zh", "en", "卧槽！");
        System.out.println(translate);
    }
}
