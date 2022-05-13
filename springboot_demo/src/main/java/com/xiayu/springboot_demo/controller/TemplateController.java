package com.xiayu.springboot_demo.controller;


import com.xiayu.springboot_demo.vo.TestVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xuhongyu
 * @describe
 * @create 2021-08-10-2:17 下午
 */
@Slf4j
@RestController
@Api(value="/template", tags="Controller模板")
public class TemplateController {


    @ApiOperation(value = "hello", notes = "欢迎接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "query", dataType = "String", example = "XiaYu")})
    @GetMapping("/hello")
    public String hello(@RequestParam String userName){
        return "hello"+userName;
    }


    @PostMapping("/testPost")
    public String testPost(@RequestBody TestVo testVo){
        List<List<String>> header = testVo.getHeader();
        System.out.println(testVo.toString());
        return testVo.toString();
    }
}