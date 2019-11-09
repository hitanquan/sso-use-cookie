package com.sso.vip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转逻辑
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    /**
     * 跳转到首页面
     * @return String
     */
    @GetMapping("/index")
    public String  toIndex() {
        return "index";
    }
}
