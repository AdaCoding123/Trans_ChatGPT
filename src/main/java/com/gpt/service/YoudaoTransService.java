package com.gpt.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gpt.config.YoudaoTransConfig;
import com.gpt.result.CodeMsg;
import com.gpt.utils.AuthV3Util;
import com.gpt.utils.YoudaoHttpUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:lcx
 * @Date:
 * @Description:
 */

@Service
public class YoudaoTransService {

    @Resource
    YoudaoTransConfig youdaoTransConfig;

    public JSONObject getWordExplainByYoudao(String word) {
        // 添加请求参数
        Map<String, String[]> params = createRequestParams(word, youdaoTransConfig.getFrom(), youdaoTransConfig.getTo());
        // System.out.println(params);
        JSONObject res = new JSONObject();
        try {
            AuthV3Util.addAuthParams(youdaoTransConfig.getAppKey(), youdaoTransConfig.getAppSecret(), params);
            byte[] totalRes = YoudaoHttpUtil.doPost("https://openapi.youdao.com/api", null, params, "application/json");
            JSONObject result_in = JSONObject.parseObject(new String(totalRes, StandardCharsets.UTF_8));
            JSONArray jsonArray = result_in.getJSONObject("basic").getJSONArray("explains");
            StringBuilder str = new StringBuilder();
            for (Object i : jsonArray) {
                String NEWLINE = "\n\n";
                String BOLD = "**";
                str.append(BOLD).append(i).append(BOLD).append(NEWLINE);
            }
            res.put("data", str.toString());
            res.put("msg", CodeMsg.SUCCESS.getMsg());
            res.put("code", CodeMsg.SUCCESS.getCode());
        } catch (Exception e) {
            res.put("data", null);
            res.put("msg", CodeMsg.AUTH_ERROR.getMsg());
            res.put("code", CodeMsg.AUTH_ERROR.getCode());
        }
        return res;
    }


    private static Map<String, String[]> createRequestParams(String q, String from, String to) {
        // String vocabId = "您的用户词表ID";

        return new HashMap<>() {{
            put("q", new String[]{q});
            put("from", new String[]{from});
            put("to", new String[]{to});
            // put("vocabId", new String[]{vocabId});
        }};

    }

    // public static void main(String[] args) {
    //     JSONObject res = getWordExplainByYoudao("important");
    //     System.out.println(res);
    // }

}
