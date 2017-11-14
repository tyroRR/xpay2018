package com.xpay.pay.proxy;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xpay.pay.BaseSpringJunitTest;
import com.xpay.pay.proxy.upay.ActiviateResponse;
import com.xpay.pay.proxy.upay.UPayProxy;

public class UPayProxyTest  extends BaseSpringJunitTest {
	@Autowired 
	private UPayProxy uPayProxy;
	
	@Test
	public void testQuery() {
		PaymentRequest request = new PaymentRequest();
		request.setOrderNo("X003006320171017163053738411");
		PaymentResponse response = uPayProxy.query(request);
		System.out.println("response code: "+ response.getCode()+" "+response.getMsg());
		
		System.out.println(response.getBill().getTokenId());
	}
	
	@Test
	public void testRefund() {
		PaymentRequest request = new PaymentRequest();
		request.setOrderNo("X003006320171017163053738411");
		request.setTotalFee("0.01");
		request.setExtStoreId("36694908,100003690002293222,0690940a8521e01b438b45c053cc4397");
		PaymentResponse response = uPayProxy.refund(request);
		System.out.println("response code: "+ response.getCode()+" "+response.getMsg());
		
		System.out.println(response.getBill().getTokenId());
	}
	
	@Test
	public void testActiviate() {
		String code = "85351813";
		String deviceId = "192.0.0.12";
		
		ActiviateResponse response = uPayProxy.activiate(code, deviceId);
		
		System.out.println(code+","+response.getBiz_response().getTerminal_sn()+"," + response.getBiz_response().getTerminal_key());
	}
}
