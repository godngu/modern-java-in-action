package com.godngu.chapter6;

import static com.godngu.CaloricLevel.DIET;
import static com.godngu.CaloricLevel.FAT;
import static com.godngu.CaloricLevel.NORMAL;
import static com.godngu.Dish.Type.FISH;
import static com.godngu.Dish.Type.MEAT;
import static com.godngu.Dish.Type.OTHER;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

import com.godngu.CaloricLevel;
import com.godngu.Dish;
import com.godngu.Dish.Type;
import com.godngu.Menu;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MenuTest {

    private final List<Dish> menu = Menu.list;
    private final int totalCalories = 800 + 700 + 400 + 530 + 350 + 120 + 550 + 300 + 450;
    private final int totalCount = menu.size();

    @Test
    @DisplayName("스트림 최대 칼로리")
    void stream_max() {
        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish = menu.stream().max(dishCaloriesComparator);
        assertThat(mostCalorieDish.get().getName()).isEqualTo("pork");
    }

    @Test
    @DisplayName("총 칼로리 계산")
    void stream_total_calories() {
//        Integer sum = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        Integer sum = menu.stream().mapToInt(Dish::getCalories).sum();
        assertThat(sum).isEqualTo(totalCalories);
    }

    @Test
    @DisplayName("평균 칼로리 계산")
    void stream_average_calories() {
        Double avg = menu.stream().collect(Collectors.averagingDouble(Dish::getCalories));
        assertThat(avg).isEqualTo((double) totalCalories / (double) totalCount);
    }

    @Test
    @DisplayName("칼로리 요약 연산")
    void menu_statistics() {
        IntSummaryStatistics statistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println("statistics = " + statistics);
        assertThat(statistics.getCount()).isEqualTo(totalCount);
    }

    @Test
    @DisplayName("문자열 연결")
    void string_joining() {
        String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println("shortMenu = " + shortMenu);
    }

    @Test
    @DisplayName("타입으로 그룹화")
    void dishes_by_type() {
        Map<Type, List<Dish>> group = menu.stream().collect(Collectors.groupingBy(Dish::getType));
        System.out.println("group = " + group);
        assertThat(group.size()).isEqualTo(3);
        assertThat(group.get(FISH)).hasSize(2);
        assertThat(group.get(OTHER)).hasSize(4);
        assertThat(group.get(MEAT)).hasSize(3);
    }

    @Test
    @DisplayName("칼로리로 그룹화")
    void dishes_by_caloric_level() {
        Map<CaloricLevel, List<Dish>> group = menu.stream().collect(
            groupingBy(dish -> {
                if (dish.getCalories() <= 400) {
                    return DIET;
                } else if (dish.getCalories() <= 700) {
                    return NORMAL;
                } else {
                    return FAT;
                }
            })
        );
        System.out.println("group = " + group);
        assertThat(group.get(FAT)).hasSize(1);
    }

    @Test
    @DisplayName("타입별로 칼로리가 500 넘는 음식만 그룹화")
    void dishes_by_caloric_greater_then_500() {
        Map<Type, List<Dish>> group = menu.stream().collect(
            groupingBy(
                Dish::getType,
                filtering(dish -> dish.getCalories() > 500, toList())
            )
        );
        System.out.println("group = " + group);
        assertThat(group).hasSize(3);
        assertThat(group.get(FISH)).hasSize(0);
    }

    @Test
    @DisplayName("타입별로 이름 목록을 그룹화")
    void dish_names_by_type_by_list() {
        Map<Type, List<String>> group = menu.stream()
            .collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
        System.out.println("group = " + group);
    }

    @Test
    @DisplayName("타입별 태그목록 추출, 중복 제거(Set)")
    void dish_tags_by_type_by_set() {
        Map<String, List<String>> dishTags = Menu.dishTags();
        Map<Type, Set<String>> group = menu.stream().collect(
            groupingBy(
                Dish::getType,
                flatMapping(
                    dish -> dishTags.get(dish.getName()).stream(),
                    toSet()
                )
            )
        );
        System.out.println("group = " + group);
        assertThat(group.get(FISH)).hasSize(4);
    }

    @Test
    @DisplayName("다수준 그룹화: 타입별로 분류하고, 칼로리 레벨로 분류한다.")
    void name() {
        Map<Type, Map<CaloricLevel, List<Dish>>> group = menu.stream().collect(
            groupingBy(
                Dish::getType,
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400)
                        return DIET;
                    else if (dish.getCalories() <= 700)
                        return NORMAL;
                    else
                        return FAT;
                })
            )
        );
        System.out.println("group = " + group);
        assertThat(group.get(MEAT).get(DIET)).hasSize(1);
        assertThat(group.get(MEAT).get(DIET).get(0).getName()).isEqualTo("chicken");
    }
}
