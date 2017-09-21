package com.xpay.pay.proxy.kekepay;

import com.xpay.pay.model.Bill;
import com.xpay.pay.proxy.IPaymentProxy;
import com.xpay.pay.proxy.PaymentRequest;
import com.xpay.pay.proxy.PaymentResponse;
import com.xpay.pay.proxy.PaymentResponse.OrderStatus;
import com.xpay.pay.util.AppConfig;
import com.xpay.pay.util.CryptoUtils;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class KekePayProxy implements IPaymentProxy {

  protected static final Logger logger = LogManager.getLogger("AccessLog");
  private static final AppConfig config = AppConfig.kekePayConfig;
  private static final String BASE_ENDPOINT = config.getProperty("provider.endpoint");
  private static final String PAY_ENDPOINT = BASE_ENDPOINT + config.getProperty("provider.pay");
  private static final String QUERY_ENDPOINT = BASE_ENDPOINT + config.getProperty("provider.query");
  private static final String appId = config.getProperty("provider.app.id");
  private static final String appSecret = config.getProperty("provider.app.secret");
  private static final String PRODUCT_TYPE = config.getProperty("provider.product.type");
  private static final String DEFAULT_JSAPI_URL = AppConfig.XPayConfig
      .getProperty("jsapi.endpoint");
  public static final String PAYED = "PAYED";
  public static final String TOPAY = "TOPAY";


  @Override
  public PaymentResponse unifiedOrder(PaymentRequest request) {
    String url = DEFAULT_JSAPI_URL + TOPAY + "/" + request.getOrderNo();
    PaymentResponse response = new PaymentResponse();
    response.setCode(PaymentResponse.SUCCESS);
    Bill bill = new Bill();
    bill.setCodeUrl(url);
    bill.setOrderStatus(OrderStatus.NOTPAY);
    response.setBill(bill);
    return response;
  }

  public String getJsUrl(PaymentRequest request) {
    KekePayRequest kekePayRequest = this.toKekePayRequest(PRODUCT_TYPE, request);
    MultiValueMap<String, String> keyPairs = this.getKeyPairs(kekePayRequest);
    String requestParam = this.signature(keyPairs, appSecret);
    return PAY_ENDPOINT + "?" + requestParam;
  }

  private String signature(MultiValueMap<String, String> keyPairs, String appSecret) {
    String[] keys = keyPairs.keySet().toArray(new String[keyPairs.size()]);
    Arrays.sort(keys, new Comparator<String>() {
      @Override
      public int compare(String x1, String x2) {
        return x1.compareTo(x2);
      }
    });

    UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
    builder.queryParams(keyPairs);
    builder.queryParam("paySecret", appSecret);
    String params = builder.build().toString().substring(1);
    logger.debug("sorted params: " + params);
    String md5 = CryptoUtils.md5(params);
    logger.debug("md5 upper: " + md5.toUpperCase());
    try {
      return builder.build().encode("UTF-8").getQuery() + "&sign=" + md5.toUpperCase();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  private MultiValueMap<String, String> getKeyPairs(KekePayRequest kekePayRequest) {
    if (kekePayRequest == null) {
      return null;
    }
    MultiValueMap<String, String> keyPairs = new LinkedMultiValueMap<>();

    if (StringUtils.isNotBlank(kekePayRequest.getNotifyUrl())) {
      keyPairs.add("notifyUrl", kekePayRequest.getNotifyUrl());
    }
    if (StringUtils.isNotBlank(kekePayRequest.getOrderIp())) {
      keyPairs.add("orderIp", kekePayRequest.getOrderIp());
    }
    if (StringUtils.isNotBlank(kekePayRequest.getOrderPrice())) {
      keyPairs.add("orderPrice", kekePayRequest.getOrderPrice());
    }
    if (StringUtils.isNotBlank(kekePayRequest.getOrderTime())) {
      keyPairs.add("orderTime", kekePayRequest.getOrderTime());
    }
    if (StringUtils.isNotBlank(kekePayRequest.getOutTradeNo())) {
      keyPairs.add("outTradeNo", kekePayRequest.getOutTradeNo());
    }
    if (StringUtils.isNotBlank(kekePayRequest.getPayBankAccountNo())) {
      keyPairs.add("payBankAccountNo", kekePayRequest.getPayBankAccountNo());
    }
    if (StringUtils.isNotBlank(kekePayRequest.getPayKey())) {
      keyPairs.add("payKey", kekePayRequest.getPayKey());
    }
    if (StringUtils.isNotBlank(kekePayRequest.getProductName())) {
      keyPairs.add("productName", kekePayRequest.getProductName());
    }
    if (StringUtils.isNotBlank(kekePayRequest.getProductType())) {
      keyPairs.add("productType", kekePayRequest.getProductType());
    }
    if (StringUtils.isNotBlank(kekePayRequest.getReturnUrl())) {
      keyPairs.add("returnUrl", kekePayRequest.getReturnUrl());
    }
    return keyPairs;
  }

  private KekePayRequest toKekePayRequest(String productType, PaymentRequest request) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    KekePayRequest kekePayRequest = new KekePayRequest();
    kekePayRequest.setNotifyUrl(request.getNotifyUrl());
    kekePayRequest.setOrderIp(request.getServerIp());
    kekePayRequest.setOrderPrice(request.getTotalFee());
    kekePayRequest.setOrderTime(simpleDateFormat.format(new Date()));
    kekePayRequest.setOutTradeNo(request.getOrderNo());
    kekePayRequest.setPayBankAccountNo("123456789");
    kekePayRequest.setProductName(request.getSubject());
    kekePayRequest.setPayKey(appId);
    kekePayRequest.setProductType(productType);
    kekePayRequest.setReturnUrl(DEFAULT_JSAPI_URL + PAYED + "/" + request.getOrderNo());
    return kekePayRequest;
  }

  @Override
  public PaymentResponse query(PaymentRequest request) {
    return null;
  }

  @Override
  public PaymentResponse refund(PaymentRequest request) {
    return null;
  }
}