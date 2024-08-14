package com.gpt.service;

import com.alibaba.fastjson.JSONObject;
import com.gpt.result.CodeMsg;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */

@Service
public class ChatgptTransService {

    @Resource
    OpenAiService service;

    public JSONObject getWordExplainByChatgpt(String word) {
        JSONObject res = new JSONObject();
        final String BasePrompt = "我在学习英语词汇，请给出单词详细的解释，包括Pronunciation，Part of Speech，meaning，etymology，word roots，derived word，common phrases，related example sentences等。这个单词是：";
        List<ChatMessage> messages = buildChatMessage(BasePrompt + word);
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .temperature(0.8)
                .messages(messages)  // prompt
                .build();
        ChatCompletionResult completionresult = service.createChatCompletion(completionRequest);

        String str = completionresult.getChoices().get(0).getMessage().getContent();
        if (str != null) {
            res.put("data", str);
            res.put("msg", CodeMsg.SUCCESS.getMsg());
            res.put("code", CodeMsg.SUCCESS.getCode());
        } else {
            res.put("data", null);
            res.put("msg", CodeMsg.WORD_ERROR.getMsg());
            res.put("code", CodeMsg.WORD_ERROR.getCode());
        }
        return res;
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
    //     JSONObject res = getContentCompletion("hello;world");
    //     System.out.println(res);
    // }
}
