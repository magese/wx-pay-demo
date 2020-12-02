package cn.mb.wxpaydemo.controller;

import cn.hutool.core.io.FileUtil;
import cn.mb.wxpaydemo.constant.WxPayConstant;
import cn.mb.wxpaydemo.service.PayService;
import cn.mb.wxpaydemo.util.WxPayUtil;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>
 *  支付接口
 * </p>
 *
 * @author: guohaibin
 * @createDate: 2020/12/1
 */
@Slf4j
@RestController
public class PayController {

    private final PayService payService;
    private final WxPayUtil wxPayUtil;

    public PayController(PayService payService, WxPayUtil wxPayUtil) {
        this.payService = payService;
        this.wxPayUtil = wxPayUtil;
    }

    /**
     * <p>
     *  下单
     * </p>
     * @param outTradeNo    订单号
     * @return com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult
     * @author guohaibin
     * @date 2020-12-02 08:59:11
     */
    @GetMapping("/prepay")
    public WxPayMpOrderResult prepay(String outTradeNo) throws Exception {
        //  TODO openId参数需要修改为appId下的支付用户的openId
        return wxPayUtil.createOrderJSAPI(
                WxPayConstant.APP_ID, WxPayConstant.MCH_ID, WxPayConstant.MCH_KEY,
                "测试", BigDecimal.valueOf(0.01), "127.0.0.1",
                outTradeNo, "oH-4D5Xyc3Csg3yKC3Bv_8fq4qjI"
        );
    }

    /**
     * <p>
     *  支付回调
     * </p>
     * @param xmlData   微信发送的数据
     * @return java.lang.String
     * @author guohaibin
     * @date 2020-12-02 08:59:59
     */
    @PostMapping("/payNotify")
    public String payNotify(@RequestBody String xmlData) {
        //  需要考虑恶意调用，非法调用可以不考虑，因为有签名验证，但是要避免多次恶意调用
        return payService.payNotify(xmlData);
    }

    /**
     * <p>
     *  退款
     * </p>
     * @param outTradeNo    订单号
     * @return com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult
     * @author guohaibin
     * @date 2020-12-02 08:59:11
     */
    @GetMapping("/refund")
    public WxPayRefundResult refund(String outTradeNo) throws Exception {
        return wxPayUtil.refund(
                WxPayConstant.APP_ID, WxPayConstant.MCH_ID, WxPayConstant.MCH_KEY,
                FileUtil.readBytes(WxPayConstant.CERT_PATH), outTradeNo, outTradeNo,
                BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.01)
        );
    }

    /**
     * <p>
     *  退款回调
     * </p>
     * @param xmlData   微信发送的数据
     * @return java.lang.String
     * @author guohaibin
     * @date 2020-12-02 08:59:59
     */
    @PostMapping("/refundNotify")
    public String refundNotify(@RequestBody String xmlData) {
        //  需要考虑恶意调用，非法调用可以不考虑，因为有签名验证，但是要避免多次恶意调用
        return payService.refundNotify(xmlData);
    }

}