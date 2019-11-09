package com.sso.login.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data   // 添加set、get方法
@NoArgsConstructor // 添加无参构造方法
@AllArgsConstructor // 添加全参构造方法
@Accessors(chain = true) // 添加链式调用
public class User {
    private Integer id;
    private String username;
    private String password;
}
