package com.shop.dto;

import com.shop.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    private String itemNm;

    private int count;

    private int orderPrice;

    private int orderShippingFee;

    private String imgUrl;

    private String comment;

    private Long orderItemId;

    private String reviewYn;

    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.orderShippingFee = orderItem.getItem().getShippingFee();
        this.imgUrl = imgUrl;
        this.reviewYn = orderItem.getReviewYn();
        this.orderItemId = orderItem.getId();
        this.comment = orderItem.getComment();
    }

}