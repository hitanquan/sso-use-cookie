package com.sso.login.controller;

import com.sso.login.pojo.User;
import com.sso.login.utils.LoginCacheUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * 页面跳转逻辑
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    /**
     * 跳转到登录页面
     * @param target 发起登录请求页面带过来的参数，非必要，默认值为空
     * @param session session域，用来保存target的
     * @param cookie cookie,用来保存用户信息和验证用户信息是否已存在
     * @return String
     */
    @GetMapping("/login")
    public String  toLogin(@RequestParam(required = false, defaultValue = "") String target, HttpSession session,
                           @CookieValue(required = false, value = "TOKEN") Cookie cookie) {
        // 若target为空，让其值为系统首页地址
        // 什么时候会为空呢？就是直接在浏览器访问该login，并且不带target的时候
        if (StringUtils.isEmpty(target)) {
            target = "http://www.codeshop.com:9010/view/index";
        }
        // 判断用户是否已登录
        if (cookie != null) {
            String value = cookie.getValue();
            User user = LoginCacheUtil.loginUser.get(value);
            // 若user不为空，说明已经有用户登录，重定向到首页
            if (user != null) {
                return "redirect:" + target;
            }
        }
        // TODO: 这里要做target合法性校验
        // 若target不为空，将其保存到session中
         session.setAttribute("target",target);
        return "login";
    }
}
