package cn.mb.wxpaydemo;

import com.github.binarywang.wxpay.config.WxPayConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WxPayDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxPayDemoApplication.class, args);
    }

}
