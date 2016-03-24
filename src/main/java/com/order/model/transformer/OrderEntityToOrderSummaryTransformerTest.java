package com.order.model.transformer;

import com.google.common.collect.ImmutableList;
import com.order.model.domain.OrderSummary;
import com.order.model.entity.OrderEntity;
import com.order.model.entity.OrderItemEntity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderEntityToOrderSummaryTransformerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private OrderEntityToOrderSummaryTransformer _target = null;

    @Before
    public void setup() {
	_target = new OrderEntityToOrderSummaryTransformer();
    }

    @Test
    public void transform_success() {
	String orderNumberFixture = "1";
	OrderItemEntity itemFixture1 = getOrderItemEntity(1, 10.00);
	OrderItemEntity itemFixture2 = getOrderItemEntity(2, 1.50);

	OrderEntity orderEntityFixture = new OrderEntity();
	orderEntityFixture.setOrderNumber(orderNumberFixture);
	orderEntityFixture.setOrderItemList(ImmutableList.of(itemFixture1, itemFixture2));

	OrderSummary result = _target.transform(orderEntityFixture);

	assertNotNull(result);
	assertEquals(orderNumberFixture, result.getOrderNumber());
	assertEquals(3, result.getItemCount());
	assertEquals(BigDecimal.valueOf(13.00), result.getTotalAmount());
    }

    private OrderItemEntity getOrderItemEntity(int quantity, double sellingPrice) {
	OrderItemEntity orderItem = new OrderItemEntity();
	orderItem.setQuantity(quantity);
	orderItem.setSellingPrice(BigDecimal.valueOf(sellingPrice));
	return orderItem;
    }

    @Test
    public void transform_orderEntityIsNull_throwsIllegalArgumentException() {
	thrown.expect(IllegalArgumentException.class);
	thrown.expectMessage("Order entity should not be null");
	_target.transform(null);
    }

    @Test
    public void transform_noItemsInOrder_returnsZeroItems() {
	OrderEntity orderEntityFixture = new OrderEntity();
	OrderSummary result = _target.transform(orderEntityFixture);

	assertEquals(0, result.getItemCount());
	assertEquals(BigDecimal.ZERO, result.getTotalAmount());
    }
}