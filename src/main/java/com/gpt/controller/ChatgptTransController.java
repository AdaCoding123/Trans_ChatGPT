package com.gpt.controller;

import com.alibaba.fastjson.JSONObject;
import com.gpt.service.ChatgptTransService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Chatgpt生成单词定义
 */

@CrossOrigin
@Controller
@RequestMapping("/home")
public class ChatgptTransController {

    @Resource
    ChatgptTransService chatgptTransService;

    @RequestMapping(value = "/chatgptWordExplanation", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ChatgptWordExplanation(@RequestParam String word) {

        return chatgptTransService.getWordExplainByChatgpt(word);
    }
}
