package com.order.model.domain;

import java.math.BigDecimal;

public class OrderSummary {

    private String _orderNumber;
    private int _itemCount;
    private BigDecimal _totalAmount;

    public String getOrderNumber() {
	return _orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
	_orderNumber = orderNumber;
    }

    public int getItemCount() {
	return _itemCount;
    }

    public void setItemCount(int itemCount) {
	_itemCount = itemCount;
    }

    public BigDecimal getTotalAmount() {
	return _totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
	_totalAmount = totalAmount;
    }
}
