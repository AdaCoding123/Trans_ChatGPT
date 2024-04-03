package com.gpt.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpt.config.OpenAiConfig;
import com.theokanning.openai.client.OpenAiApi;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.ImageResult;
import com.theokanning.openai.service.OpenAiService;
import jakarta.annotation.Resource;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.theokanning.openai.service.OpenAiService.defaultObjectMapper;

@Component
public class AiServiceFactory {

    @Resource
    OpenAiConfig openAiConfig;

    public static OpenAiService createProxyService() {
        final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10L);
        String token = "";
        String proxyHost = "127.0.0.1";
        int proxyPort = 7890;
        ObjectMapper mapper = defaultObjectMapper();
        // 设置代理
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        OkHttpClient client = OpenAiService.defaultClient(token, DEFAULT_TIMEOUT).newBuilder()
                .proxy(proxy)
                .build();
        Retrofit retrofit = OpenAiService.defaultRetrofit(client, mapper);

        return new OpenAiService(retrofit.create(OpenAiApi.class), client.dispatcher().executorService());
    }

    // 注入返回的对象
    @Bean
    public OpenAiService createService() {
        String token = openAiConfig.getKeys();
        ObjectMapper mapper = defaultObjectMapper();
        OkHttpClient client = OpenAiService.defaultClient(token, Duration.ofSeconds(10L));
        // 这里重新设置了一下请求地址,官方原地址:https://api.openai.com/
        // https://api.openai-proxy.com   https://flag.smarttrot.com   https://api.op-enai.com
        Retrofit retrofit = OpenAiService.defaultRetrofit(client, mapper).newBuilder().baseUrl("https://api.openai-proxy.com").build();
        OpenAiApi api = retrofit.create(OpenAiApi.class);
        OpenAiService service = new OpenAiService(api);

        // 列出所有模型实例
        System.out.println(service.listModels() + "*****");
        return service;
    }

    public Map<String, String> getCompletion(OpenAiService service, String words) {
        final String BASEWORD = "我在学习雅思词汇，请给我生成一个大约30个词的英文小故事，包含英文故事本身，中文翻译，词汇表。请先给我英文故事，然后是对应的中文翻译，并且故事中的英文词汇和对应的中文词汇需要加粗显示，最后请给我词汇表，该表以表格形式展示，表格包括英文，中文翻译，词性，例句。这是我需要的单词：";
        List<ChatMessage> messages = buildChatMessage(BASEWORD + words);
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .temperature(0.8)
                .messages(messages)  // prompt
                .build();
        ChatCompletionResult completionresult = service.createChatCompletion(completionRequest);
        // System.out.println(result);

        Map<String, String> res = new HashMap<>();
        String text = completionresult.getChoices().get(0).getMessage().getContent();
        res.put("生成结果", text);
        return res;

    }

    public Map<String, String> getNewImage(OpenAiService service, String information) {
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                // 所需图像的文本描述。最大长度为 1000 个字符。
                .prompt(information)
                // 生成图像的大小。必须是256x256、512x512或1024x1024
                .size("1024x1024")
                // 响应格式,生成的图像返回的格式。必须是url或b64_json,默认为url,url将在一小时后过期。
                .responseFormat("url")
                // 要生成的图像数。必须介于 1 和 10 之间。
                .n(1)
                .build();
        ImageResult result = service.createImage(createImageRequest);
        Map<String, String> r = new HashMap<>();
        String url = result.getData().get(0).getUrl();
        r.put("图片链接", url);
        return r;
    }

    private List<ChatMessage> buildChatMessage(String prompt) {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMsg = new ChatMessage();
        systemMsg.setRole("user");
        systemMsg.setContent("我是一名JAVA程序员，可以回答任何JAVA相关的问题。");
        messages.add(systemMsg);

        ChatMessage userMsg = new ChatMessage();
        userMsg.setRole("user");
        userMsg.setContent(prompt);
        messages.add(userMsg);
        return messages;
    }

    // public static void main(String[] args) {
    //     OpenAiService service = createService();
    //     Map<String, String> res = getCompletion(service, "hello;world");
    //     // Map<String, String> res2 = getNewImage(service, "未来世界");
    //     System.out.println(res);
    // }
}
