package com.godngu;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class MapTest {

    private Map<String, String> map() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "A");
        map.put("b", "B");
        map.put("c", "C");
        map.put("z", null);
        return map;
    }

    @Test
    void test1() {
        var map = map();

        String result1 = map.putIfAbsent("a", "A1");
        System.out.println("result1 = " + result1);
        System.out.println("map = " + map);

        String result2 = map.putIfAbsent("d", "D1");
        System.out.println("result2 = " + result2);
        System.out.println("map = " + map);

        String result3 = map.putIfAbsent("z", "Z1");
        System.out.println("result3 = " + result3);
        System.out.println("map = " + map);
    }

    @Test
    void test2() {
        var map = map();

        String result1 = map.computeIfAbsent("a", k -> "A1");
        System.out.println("result1 = " + result1);
        System.out.println("map = " + map);

        String result2 = map.computeIfAbsent("d", k -> "D1");
        System.out.println("result2 = " + result2);
        System.out.println("map = " + map);

        String result3 = map.computeIfAbsent("z", k -> "Z1");
        System.out.println("result3 = " + result3);
        System.out.println("map = " + map);
    }

    private String getAppleOrNull(int id) {

        int appleId = 100;

        if (id == appleId) {
            return "apple";
        } else {
            return "others";
        }
    }
}
