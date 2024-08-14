package com.gpt.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.gpt.service.WeishiTransService;
import jakarta.annotation.Resource;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/home")
public class WeishiWordTransController {

    @Resource
    WeishiTransService weishiTransService;

    @RequestMapping(value = "/weishiWordExplanation", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject WeishiWordExplanation(@RequestParam String word) throws JSONException, JsonProcessingException {
        return weishiTransService.getExplainByWord(word);
    }

}
