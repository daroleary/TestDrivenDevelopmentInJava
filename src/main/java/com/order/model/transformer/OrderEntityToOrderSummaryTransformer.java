package com.order.model.transformer;

import com.order.model.domain.OrderSummary;
import com.order.model.entity.OrderEntity;
import com.order.model.entity.OrderItemEntity;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class OrderEntityToOrderSummaryTransformer {

    public OrderSummary transform(OrderEntity orderEntity) {
	if (orderEntity == null) {
	    throw new IllegalArgumentException("Order entity should not be null");
	}

	OrderSummary orderSummary = new OrderSummary();

	Integer totalQuantity = getOrderItemsStream(orderEntity)
		.map(OrderItemEntity::getQuantity)
		.reduce(0, Integer::sum);

	BigDecimal totalSellingPrice = getOrderItemsStream(orderEntity)
		.map(item -> {
		    BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
		    BigDecimal sellingPrice = item.getSellingPrice();
		    return sellingPrice.multiply(quantity);
		})
		.reduce(BigDecimal.ZERO, BigDecimal::add);

	orderSummary.setOrderNumber(orderEntity.getOrderNumber());
	orderSummary.setItemCount(totalQuantity);
	orderSummary.setTotalAmount(totalSellingPrice);

	return orderSummary;
    }

    private Stream<OrderItemEntity> getOrderItemsStream(OrderEntity orderEntity) {
	return orderEntity.getOrderItemList()
		.stream();
    }
}
