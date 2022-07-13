package chap06;

import java.util.*;

import static java.util.stream.Collectors.*;

public class GroupingExample {

    public static void main(String[] args) {

        List<Dish> menu = Dish.menu;

        // 그룹화
        // 메뉴를 요리 타입으로 그룹화하기
        Map<DishType, List<Dish>> dishesByType = menu.stream()
                .collect(groupingBy(Dish::getType));
        System.out.println(dishesByType);

        // 단순한 속성 접근자 대신 복잡한 분류 기준 사용하기 - 메뉴를 칼로리 정도에 따라 분류하기
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream()
                .collect(groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));
        System.out.println(dishesByCaloricLevel);

        // 그룹화된 요소 조작하기 -> 두 번째 인수로 또 다른 Collector를 받아 그룹화된 요소를 조작
        // 위에서는 단순히 그룹화된 요소를 리스트에 담아 반환했다. 그룹화된 요소를 조작하는 연산이 필요하다.
        // 그룹화된 요소들에서 500 칼로리가 넘는 요리만 필터링
        Map<DishType, List<Dish>> caloricDishedByType = menu.stream()
                .filter(dish -> dish.getCalories() > 500)
                .collect(groupingBy(Dish::getType));
        System.out.println(caloricDishedByType);

        // 문제점: FISH 요리 중에서 500 칼로리가 넘는 요리가 없기 때문에 FISH 요리 타입이 맵의 키에서 사라진다.

        // Collectors 클래스에는 일반적인 분류 함수에 Collector 형식의 두 번째 인수를 갖도록 groupingBy 팩토리 메소드를 오버로드해서 문제점을 해결한다.
        caloricDishedByType = menu.stream()
                .collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));
                // 여기서 filtering 메소드는 프레디케이트로 각 그룹의 요소와 필터링된 요소를 재그룹화 한다.
        System.out.println(caloricDishedByType);

        Map<String, List<String>> dishTags = Dish.dishTags; // 태그 목록을 가진 각 요리로 구성된 맵

        // flatMapping
        // 각 타입의 요리의 태그 집합을 추출해보자.
        Map<DishType, Set<String>> dishTagsByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                        flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));
        System.out.println(dishTagsByType);

        // 2. 다수준 그룹화
        // 두 인수를 받는 groupingBy는 분류 함수와 Collector를 인수로 받는다.
        // Collector에 groupingBy를 전달하면 스트림의 항목을 분류할 두 번째 기준을 정의하는 분류 함수로 사용할 수 있다.
        Map<DishType, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream()
                .collect(groupingBy(Dish::getType,
                        groupingBy(dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        })));
        System.out.println(dishesByTypeCaloricLevel);

        // 3. 서브그룹으로 데이터 수집
        // 두 번째 인수로 받는 Collector의 형식은 제한이 없다.

        // 메뉴를 요리 타입으로 그룹화하고 그룹의 각 요리 이름을 목록으로 반환
        Map<DishType, List<String>> dishNamesByType = menu.stream()
                .collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
        System.out.println(dishNamesByType);

        // 메뉴를 요리 타입으로 그룹화하고 그룹화된 요소의 갯수를 계산
        Map<DishType, Long> dishCountsByType = menu.stream()
                .collect(groupingBy(Dish::getType, counting()));
        System.out.println(dishCountsByType);

        // 메뉴를 요리 타입으로 그룹화하고 각 그룹의 칼로리 합계를 계산
        Map<DishType, Integer> sumOfCaloriesByType = menu.stream()
                .collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
        System.out.println(sumOfCaloriesByType);

        // 메뉴를 요리 타입으로 그룹화하고 가장 높은 칼로리를 가진 요리를 찾아 반환
        Map<DishType, Optional<Dish>> maxCalorieDishesByType = menu.stream()
                .collect(groupingBy(Dish::getType, maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println(maxCalorieDishesByType);

        // maxBy의 반환 타입에 따라서 맵의 값 형식이 Optional이 되었는데, 실제 메뉴의 요리 중 Optional.empty()를 값으로 갖는 요리는 존재하지 않는다.
        // 처음부터 존재하지 않는 요리의 키는 맵에 추가되지 않는다. groupingBy는 스트림의 첫 번째 요소를 찾은 이후에 그룹화 맵에 새로운 키를 lazy하게 추가한다.
        // 따라서 Optional로 래핑할 필요가 없음.
        Map<DishType, Dish> maxCalorieDishesByType2 = menu.stream()
                .collect(groupingBy(Dish::getType, collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(maxCalorieDishesByType2);

        // 각 요리 타입에 존재하는 모든 CaloricLevel 값을 알아보자.
        Map<DishType, Set<CaloricLevel>> caloricLevelsByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                        mapping(dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }, toSet())));
        System.out.println(caloricLevelsByType);
    }

    static enum CaloricLevel {

        DIET, NORMAL, FAT
    }
}
