package com.xiayu.springboot_demo.controller;

import com.xiayu.springboot_demo.config.CommonConfig;
import com.xiayu.springboot_demo.util.VerifyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 参考文章地址
 * https://juejin.cn/post/6844903962018070536
 *
 * @author xuhongyu
 * @create 2022-06-16 8:55 下午
 */

@Slf4j
@RestController
@Api(value = "/verify", tags = "验证码")
public class VerifyController {
    @Autowired
    CommonConfig commonConfig;


    @ApiOperation(value = "getVersify", notes = "获取验证码图片")
    @GetMapping("/getVersify")
    public String getVersify(HttpServletResponse response, HttpServletRequest request) throws Exception {
        // 获取到session
        HttpSession session = request.getSession();
        // 取到sessionid
        String id = session.getId();

        // 利用图片工具生成图片
        // 返回的数组第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.newBuilder()
                //设置图片的宽度
                .setWidth(120)
                //设置图片的高度
                .setHeight(35)
                //设置字符的个数
                .setSize(4)
                //设置干扰线的条数
                .setLines(5)
                //设置字体的大小
                .setFontSize(25)
                //设置是否需要倾斜
                .setTilt(true)
                //设置验证码的背景颜色
                .setBackgroundColor(Color.LIGHT_GRAY)
                //构建VerifyUtil项目
                .build()
                //生成图片
                .createImage();
        // 将验证码存入Session
        session.setAttribute("SESSION_VERIFY_CODE_" + id, objs[0]);
        // 打印验证码
        System.out.println(objs[0]);

        //   commonConfig.getVerifyCode().put(("VERIFY_CODE_" + id), objs[0].toString());


//        // 设置redis值的序列化方式
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        // 在redis中保存一个验证码最多尝试次数
//        redisTemplate.opsForValue().set(("VERIFY_CODE_" + id), "3", 5 * 60, TimeUnit.SECONDS);


        // 将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
//        response.setContentType("image/png");
//        OutputStream os = response.getOutputStream();
//        ImageIO.write(image, "png", os);

        return bufferedImageToBase64(image);
    }

    /**
     * 业务接口包含了验证码的验证
     *
     * @param code    前端传入的验证码
     * @param request Request对象
     * @return
     */
    @GetMapping("/checkCode")
    public String checkCode(String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = session.getId();

        // 将redis中的尝试次数减一
        String verifyCodeKey = "VERIFY_CODE_" + id;


        //  long num = redisTemplate.opsForValue().decrement(verifyCodeKey);

        // 如果次数次数小于0 说明验证码已经失效
        //        if (num < 0) {
        //            return "验证码失效!";
        //        }

        // 将session中的取出对应session id生成的验证码
        String serverCode = (String) session.getAttribute("SESSION_VERIFY_CODE_" + id);
        // 校验验证码
        if (null == serverCode || null == code || !serverCode.toUpperCase().equals(code.toUpperCase())) {
            return "验证码错误!";
        }

        session.removeAttribute("SESSION_VERIFY_CODE_" + id);

        // 验证通过之后手动将验证码失效
        // redisTemplate.delete(verifyCodeKey);

        // 这里做具体业务相关

        return "验证码正确!";
    }


    private String bufferedImageToBase64(BufferedImage bufferedImage) {
        //io流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            //写入流中
            ImageIO.write(bufferedImage, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //转换成字节
        byte[] bytes = baos.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        //转换成base64串
        String png_base64 = encoder.encodeBuffer(bytes).trim();
        //删除 \r\n
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");
        return "data:image/jpg;base64," + png_base64;
    }

}
