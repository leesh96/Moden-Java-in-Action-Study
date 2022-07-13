package chap06;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class PartitioningExample {

    public static void main(String[] args) {

        List<Dish> menu = Dish.menu;

        // 분할: 분할 함수라고 불리는 프레디케이트를 분류 함수로 사용하는 특별한 그룹화
        // 맵의 키 형식이 Boolean이다.

        // 메뉴의 요리를 채식 요리와 채식이 아닌 요리로 분류해야 한다.
        Map<Boolean, List<Dish>> dishesByIsVegetarian = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian));
        System.out.println(dishesByIsVegetarian);
        List<Dish> vegetarianDishes = dishesByIsVegetarian.get(true);
        System.out.println(vegetarianDishes);

        // 1. 분할의 장점 - 참, 거짓 두 가지 요소의 스트림 리스트를 모두 유지한다.
        // 채식 요리가 채식이 아닌 요리보다 대체로 칼로리가 낮은가?
        Map<Boolean, Double> avgOfCaloriesPartitionedByVegetarian = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian, averagingInt(Dish::getCalories)));
        System.out.println(avgOfCaloriesPartitionedByVegetarian.get(true) < avgOfCaloriesPartitionedByVegetarian.get(false));

        // 채식 요리와 채식이 아닌 요리를 요리 타입에 따라 그룹화하기
        Map<Boolean, Map<DishType, List<Dish>>> vegetarianDishesByType = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
        System.out.println(vegetarianDishesByType);

        // 분할된 두 그룹에서 칼로리가 최소인 음식 찾기
        Map<Boolean, Dish> minCaloricPartitionedByVegetarian = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian, collectingAndThen(minBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(minCaloricPartitionedByVegetarian);

        // 다수준 분할하기
        Map<Boolean, Map<Boolean, List<Dish>>> dishesPartitionedByCaloricAndVegetarian = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        partitioningBy(dish -> dish.getCalories() <= 700)));
        System.out.println(dishesPartitionedByCaloricAndVegetarian);

        // 도전. 숫자를 소수와 비소수로 분할하기
        Map<Boolean, List<Integer>> partitionedByPrime =
                IntStream.rangeClosed(1, 100).boxed().collect(partitioningBy(n -> isPrime(n)));
        System.out.println(partitionedByPrime);
    }

    public static boolean isPrime(int n) {
        return IntStream.range(2, (int) Math.floor(Math.sqrt(n))).noneMatch(divider -> n % divider == 0);
    }
}
