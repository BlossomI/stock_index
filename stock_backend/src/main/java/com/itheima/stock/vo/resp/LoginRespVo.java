package com.itheima.stock.vo.resp;

import lombok.Data;

/**
 * @Description
 * @Author Harry
 * @Date 5/23/2022 9:56 PM
 * @Version 1.0
 **/
@Data
public class LoginRespVo {
    private String id;
    private String phone;
    private String username;
    private String nickname;
}
