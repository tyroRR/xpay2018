package com.xpay.pay.model;

import com.xpay.pay.proxy.PaymentResponse.OrderStatus;



public class Bill {
	private String orderNo;
	private String refundOrderNo;
	private String refundTime;
	private String gatewayOrderNo;
	private String gatewayRefundOrderNo;
	private String targetOrderNo;
	private String prepayId;
	private String codeUrl;
	private String tokenId;
	private String payInfo;
	private OrderStatus orderStatus;
	private Order order;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getGatewayOrderNo() {
		return gatewayOrderNo;
	}
	public void setGatewayOrderNo(String gatewayOrderNo) {
		this.gatewayOrderNo = gatewayOrderNo;
	}
	public String getTargetOrderNo() {
		return targetOrderNo;
	}
	public void setTargetOrderNo(String targetOrderNo) {
		this.targetOrderNo = targetOrderNo;
	}
	public String getPrepayId() {
		return prepayId;
	}
	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getPayInfo() {
		return payInfo;
	}
	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		if(order!=null) {
			this.orderNo = order.getOrderNo();
		}
		this.order = order;
	}
	public String getRefundOrderNo() {
		return refundOrderNo;
	}
	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}
	public String getGatewayRefundOrderNo() {
		return gatewayRefundOrderNo;
	}
	public void setGatewayRefundOrderNo(String gatewayRefundOrderNo) {
		this.gatewayRefundOrderNo = gatewayRefundOrderNo;
	}
	public String getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}
}
