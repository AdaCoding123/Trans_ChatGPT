package com.gpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpt.result.CodeMsg;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import com.alibaba.fastjson.JSONObject;

/**
 *
 */

@Service
public class WeishiTransService {

    public JSONObject getExplainByWord(String word) throws JsonProcessingException, JSONException {
        // https://www.dictionaryapi.com/api/v3/references/collegiate/json/important?key=67613274-3af4-4247-aa61-e2d690dfc50c
        String ref1 = "collegiate";
        String key1 = "";
        JsonNode res1 = getAPI(ref1, key1, word);
        System.out.println(res1);
        JsonNode fl = res1.get(0).get("fl");
        System.out.println("fl*" + fl);
        JsonNode shortdef = res1.get(0).get("shortdef");
        System.out.println("shortdef*" + shortdef);

        // https://www.dictionaryapi.com/api/v3/references/thesaurus/json/important?key=8ef7bb8d-c94e-42b3-b054-44081824083f
        String ref2 = "thesaurus";
        String key2 = "";
        JsonNode res2 = getAPI(ref2, key2, word);
        JsonNode sseq = res2.get(0).get("def").get(0).get("sseq");
        StringBuilder syn_words = new StringBuilder();
        StringBuilder ant_words = new StringBuilder();
        for (JsonNode node : sseq) {
            JsonNode syn_list = node.get(0).get(1).get("syn_list").get(0);
            JsonNode ant_list = node.get(0).get(1).get("ant_list").get(0);
            syn_words.append(syn_list.get(0).get("wd")).append(",");
            ant_words.append(ant_list.get(0).get("wd")).append(",");
        }
        JSONObject finalRes1 = new JSONObject();
        finalRes1.put("fl", fl);
        finalRes1.put("shortdef", shortdef);
        finalRes1.put("syn_words", syn_words);
        finalRes1.put("ant_words", ant_words);

        JSONObject res = new JSONObject();
        res.put("data", finalRes1);
        res.put("msg", CodeMsg.SUCCESS.getMsg());
        res.put("code", CodeMsg.SUCCESS.getCode());
        System.out.println("res*" + res);
        return res;
    }

    public JsonNode getAPI(String ref, String key, String word) throws JsonProcessingException {
        String baseURL = "https://www.dictionaryapi.com/api/v3/references/";
        String jsonResponse = null;
        try {
            URI uri = new URI(baseURL + ref + "/json/" + word + "?key=" + key);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                // 这里可以根据接口返回的数据类型选择相应的读取方式
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                inputStream.close();
                jsonResponse = response.toString();
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        // 使用Jackson库解析JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        return jsonNode;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {

        URI uri = new URI("https://www.dictionaryapi.com/api/v3/references/collegiate/json/important?key=67613274-3af4-4247-aa61-e2d690dfc50c");
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        String jsonResponse = null;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            inputStream.close();
            jsonResponse = response.toString();
        } else {
            // 处理请求失败的情况
            System.out.println("请求失败！！");
        }

        // 使用Jackson库解析JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        // 根据JSON对象获取相应的数据
        // String value = jsonNode.get("key").asText();
        System.out.println(jsonNode);
        // return value;

    }
}
