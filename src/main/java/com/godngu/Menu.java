package com.godngu;

import static com.godngu.Dish.Type.FISH;
import static com.godngu.Dish.Type.MEAT;
import static com.godngu.Dish.Type.OTHER;
import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {

    public static List<Dish> list = List.of(
        new Dish("pork", false, 800, MEAT),
        new Dish("beef", false, 700, MEAT),
        new Dish("chicken", false, 400, MEAT),
        new Dish("french fries", true, 530, OTHER),
        new Dish("rice", true, 350, OTHER),
        new Dish("season fruit", true, 120, OTHER),
        new Dish("pizza", true, 550, OTHER),
        new Dish("prawns", false, 300, FISH),
        new Dish("salmon", false, 450, FISH)
    );

    public static Map<String, List<String>> dishTags() {
        HashMap<String, List<String>> dishTags = new HashMap<>();
        dishTags.put("pork", asList("greasy", "salty"));
        dishTags.put("beef", asList("salty", "roasted"));
        dishTags.put("chicken", asList("fried", "crisp"));
        dishTags.put("french fries", asList("greasy", "fried"));
        dishTags.put("rice", asList("light", "natural"));
        dishTags.put("season fruit", asList("fresh", "natural"));
        dishTags.put("pizza", asList("tasty", "salty"));
        dishTags.put("prawns", asList("tasty", "roasted"));
        dishTags.put("salmon", asList("delicious", "fresh"));
        return dishTags;
    }
}
