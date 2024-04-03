package com.gpt.controller;

import com.alibaba.fastjson.JSONObject;
import com.gpt.result.CodeMsg;
import com.gpt.service.YoudaoTransService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author:lcx
 * @Date:
 * @Description:
 */
@CrossOrigin
@Controller
@RequestMapping("/home")
public class YoudaoWordTransController {

    @Resource
    YoudaoTransService youdaoTransService;

    @RequestMapping(value = "/youdaoWordExplanation", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject YoudaoWordExplanation(@RequestParam String word) {
        JSONObject result = new JSONObject();
        JSONObject res = youdaoTransService.getWordExplainByYoudao(word);
        // System.out.println("oriRes**" + res);
        if (res != null) {
            result = res;
        } else {
            result.put("data", null);
            result.put("msg", CodeMsg.WORD_ERROR.getMsg());
            result.put("code", CodeMsg.WORD_ERROR.getCode());
        }
        return result;
    }
}
