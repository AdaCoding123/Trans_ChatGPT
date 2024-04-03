package com.gpt.service;

import com.gpt.utils.BaiduHttpUtil;
import com.gpt.utils.GsonUtils;

import java.util.HashMap;
import java.util.Map;


public class BaiduTransService {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String textTrans(String from, String to, String query, String termIds) {
        // 请求url
        String url = "https://aip.baidubce.com/rpc/2.0/mt/texttrans-with-dict/v1";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("from", from);
            map.put("to", to);
            map.put("q", query);
            map.put("termIds", termIds);

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.976e001faa0508b15caa32310b484a93.2592000.1710839663.282335-51967771";  //[调用鉴权接口获取的token]
            // System.out.println(result);
            return BaiduHttpUtil.post(url, accessToken, "application/json", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String s = textTrans("auto", "cht", "important", "");
        System.out.println(s);
    }
}

