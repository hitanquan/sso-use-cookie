package com.sso.login.controller;

import com.sso.login.pojo.User;
import com.sso.login.utils.LoginCacheUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/loginController")
public class LoginController {
    // 定义一个保存用户信息的set集合，模拟数据库用户数据
    private static Set<User> dbUser;
    // 初始化一些用户数据
    static {
        dbUser = new HashSet<>();
        dbUser.add(new User(1,"zhangsan","zhangsan"));
        dbUser.add(new User(2,"lisi","lisi"));
        dbUser.add(new User(3,"wangwu","wangwu"));
    }

    // @RequestMapping("/login")
    @PostMapping("/login")
    public String doLogin(User user, HttpSession session, HttpServletResponse response) {
        // 拿到target网址
        String target = (String) session.getAttribute("target");
        // 非空判断
        if (StringUtils.isEmpty(user.getUsername())) {
            session.setAttribute("errorMsg", "用户名不能为空！");
            return "login";
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            session.setAttribute("errorMsg", "密码不能为空！");
            return "login";
        }
        // 对用户名、密码做校验
        Optional<User> first = dbUser.stream().filter(dbUser -> dbUser.getUsername().equals(user.getUsername())
                                                             && dbUser.getPassword().equals(user.getPassword())).findFirst();
        // 若用户名、密码正确
        if (first.isPresent()) {
            // 保存用户登录信息
            // token就是用于标识用户的一个凭证，就像人的身份证号一样
            // session.setAttribute("user",user);
            String token = UUID.randomUUID().toString();
            // 用token创建cookie对象
            Cookie cookie = new Cookie("TOKEN", token);
            // 确保域名一致
            cookie.setDomain("codeshop.com");
            response.addCookie(cookie);
            // 向登录用户对象存放用户信息
            LoginCacheUtil.loginUser.put(token, first.get());
        }else {
            // 保存错误提示信息，返回登陆页面
            session.setAttribute("errorMsg", "用户名或密码错误！");
            return "login";
        }
        // 重定向到target地址
        return "redirect:" + target;
    }

    /**
     * 提供一个供其他子系统获取用户信息的方法，用来判断用户是否已经登录
     * @param token 获取用户信息的标识
     * @return ResponseEntity<User>
     */
    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity<User> getUserInfo(String token) {
        // 若token不为空，则根据token拿到用户信息并响应
        if (!StringUtils.isEmpty(token)) {
            User user = LoginCacheUtil.loginUser.get(token);
            return ResponseEntity.ok(user);
        }else {
            // 否则，响应为坏的请求
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
