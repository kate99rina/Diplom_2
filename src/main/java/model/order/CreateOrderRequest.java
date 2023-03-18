package model.order;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrderRequest {
    private List<String> ingredients;

    public CreateOrderRequest(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
