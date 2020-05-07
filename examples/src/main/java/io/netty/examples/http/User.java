package io.netty.examples.http;

import lombok.Data;

import java.util.Date;

/**
 * description: User <br>
 * date: 2020/5/7 17:25 <br>
 * author: EDZ <br>
 * version: 1.0 <br>
 */
@Data
public class User {
    private String userName;
    private String method;
    private Date date;
}
