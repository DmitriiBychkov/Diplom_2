package org.example.models;

import java.util.List;

public class Order {
    private List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");


    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Order() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
