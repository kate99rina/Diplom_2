package org.example.model.order;

import lombok.Getter;

@Getter
public class Order {
    private Integer number;

    public Order(Integer number) {
        this.number = number;
    }
}
