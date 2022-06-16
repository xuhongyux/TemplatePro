package com.xiayu.springboot_demo.config;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuhongyu
 * @create 2022-06-16 9:04 下午
 */

@Data
@Component
public class CommonConfig {

    public Map<String,String> verifyCode;


    public CommonConfig(){
        this.verifyCode = new HashMap<>();
    }


}
