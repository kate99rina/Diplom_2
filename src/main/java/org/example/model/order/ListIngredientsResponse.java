package org.example.model.order;

import lombok.Getter;

import java.util.List;

@Getter
public class ListIngredientsResponse {
    private boolean success;
    private List<Ingredient> data;
}
