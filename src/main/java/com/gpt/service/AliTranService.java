package com.gpt.service;

import com.aliyun.alimt20181012.Client;
import com.aliyun.alimt20181012.models.TranslateGeneralResponse;
import com.aliyun.tea.*;
import com.aliyun.teaopenapi.models.Config;


/**
 *
 **/
public class AliTranService {

    static String accessKeyId = "LTAI5tKZnqEGDbeMkHtQeqTj";
    static String accessKeySecret = "Xh98JKpCZGkGwtLyn33osjBpcC08XP";

    /**
     * 使用AK&SK初始化账号Client
     */
    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/alimt
        config.endpoint = "mt.cn-hangzhou.aliyuncs.com";
        return new Client(config);
    }

    public static void getWordExplainByAli(String content) throws Exception {
        // 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        Client client = createClient(accessKeyId, accessKeySecret);
        com.aliyun.alimt20181012.models.TranslateGeneralRequest translateGeneralRequest = new com.aliyun.alimt20181012.models.TranslateGeneralRequest()
                .setFormatType("text")
                .setSourceLanguage("en")
                .setTargetLanguage("zh")
                .setSourceText(content)
                .setScene("general");
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            com.aliyun.alimt20181012.models.TranslateGeneralResponse resp = client.translateGeneralWithOptions(translateGeneralRequest, runtime);
            com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(resp));
        } catch (TeaException error) {
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }


    public static void main(String[] args) throws Exception {
        String content = "important";
        getWordExplainByAli(content);
    }
}
