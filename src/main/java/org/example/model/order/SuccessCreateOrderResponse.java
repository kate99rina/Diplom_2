package org.example.model.order;

import lombok.Getter;

@Getter
public class SuccessCreateOrderResponse {
    private String name;
    private Order order;
    private boolean success;

    public SuccessCreateOrderResponse(String name, Order order, boolean success) {
        this.name = name;
        this.order = order;
        this.success = success;
    }
}
