package com.gpt.result;

import lombok.Data;

@Data
public class CodeMsg {
    private int code;
    private String msg;

    // 通用的错误码
    public static CodeMsg SUCCESS = new CodeMsg(200, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500104, "访问太频繁！");

    public static CodeMsg AUTH_ERROR = new CodeMsg(500210, "用户授权失败，输入正确的key和secret！");
    public static CodeMsg WORD_ERROR = new CodeMsg(400210, "获取单词解释失败，请输入存在的单词");


    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        // 改造msg即可,message=msg拼接args
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }
}
