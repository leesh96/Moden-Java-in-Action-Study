package chap06;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReducingAndSummarizingExample {

    public static void main(String[] args) {

        List<Dish> menu = Dish.menu;

        // 컬렉션에서 요리 수 계산하기
        long howManyDishes = menu.stream().collect(Collectors.counting());
        System.out.println(howManyDishes);
        howManyDishes = menu.stream().count();
        System.out.println(howManyDishes);

        // 스트림 값에서 최댓값과 최솟값 검색
        Optional<Dish> maxCalorieDish = menu.stream()
                .collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))); // max()와 동일
        maxCalorieDish.ifPresent(System.out::println);
        Optional<Dish> minCalorieDish = menu.stream().min(Comparator.comparingInt(Dish::getCalories));
        minCalorieDish.ifPresent(System.out::println);

        // 요약 연산 - 스트림에 있는 객체의 숫자 필드의 합계나 평균 등을 반환하는 연산
        // 칼로리의 합계 구하기
        int totalCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories)); // mapToInt().sum()과 동일
        System.out.println(totalCalories);

        // 칼로리로 매핑된 각 요리의 값을 탐색하면서, 초깃값이 0으로 설정되어 있는 누적자에 칼로리를 더한다.

        // 칼로리의 평균 구하기
        double avgCalories = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
        System.out.println(avgCalories);

        // 합계, 평균, 최댓값 최솟값 등을 계산해보자.
        IntSummaryStatistics menuCaloriesStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println(menuCaloriesStatistics);

        // 요약 연산은 int, long, double 형에 대응하는 연산을 갖고 있다.

        // 문자열로 연결하기
        String nameOfMenus = menu.stream().map(Dish::getName).collect(Collectors.joining());
        System.out.println(nameOfMenus);

        // 내부적으로 StringBuilder를 이용해서 요소를 하나의 문자열로 만든다.
        // 구분자나 접두사, 접미사를 인수로 받는 오버로딩된 joining도 있다.
        nameOfMenus = menu.stream().map(Dish::getName).collect(Collectors.joining(", "));
        System.out.println(nameOfMenus);

        // 위의 예제에서 map을 사용하지 않는 경우 스트림 요소 객체의 toString 메소드를 사용해서 문자열로 변환한다.

        // 범용 리듀싱 요약 연산
        // 위의 컬렉터들을 reducing 팩토리 메소드로도 정의할 수 있다. 편의성과 가독성을 위해 특화된 컬렉터를 사용
        totalCalories = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (a, b) -> a + b));
        System.out.println(totalCalories);

        // 메뉴에서 채식 요리를 가져와 칼로리 순으로 정렬한 다음 가장 마지막 요소를 가져와보자. -> reduce를 활용할 수 있다.
        // 첫 번째 방식: filter와 sorted 연산을 적용한다음, toList로 수집하여 인덱스로 접근하기
        List<Dish> dishesForVegetarianSortedByCalories = menu.stream()
                .filter(Dish::isVegetarian)
                .sorted(Comparator.comparingInt(Dish::getCalories))
                .collect(Collectors.toList());
        Dish maxCalorieDishForVegetarian = dishesForVegetarianSortedByCalories.get(dishesForVegetarianSortedByCalories.size() - 1);
        System.out.println(maxCalorieDishForVegetarian);

        // 두 번째 방식: reduce 사용하기
        Optional<Dish> maxCalorieDishForVegetarian2 = menu.stream()
                .filter(Dish::isVegetarian)
                .sorted(Comparator.comparingInt(Dish::getCalories))
                .reduce((a, b) -> b);
        maxCalorieDishForVegetarian2.ifPresent(System.out::println);

        maxCalorieDishForVegetarian2 = menu.stream()
                .filter(Dish::isVegetarian)
                .sorted(Comparator.comparingInt(Dish::getCalories))
                .collect(Collectors.reducing((a, b) -> b));
        maxCalorieDishForVegetarian2.ifPresent(System.out::println);

        // 세 번째 방식: 정렬 없이 reducing 하기 -> 다음 값으로 칼로리가 더 높은 요리 선택
        maxCalorieDishForVegetarian2 = menu.stream()
                .filter(Dish::isVegetarian)
                .reduce((a, b) -> a.getCalories() > b.getCalories() ? a : b);
        maxCalorieDishForVegetarian2.ifPresent(System.out::println);

        // 최적의 해법 선택하기
        // 전체 요리의 칼로리 합계를 구하는 여러 방법
        totalCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        System.out.println(totalCalories);
        totalCalories = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
        System.out.println(totalCalories);
        totalCalories = menu.stream().map(Dish::getCalories).reduce(0, Integer::sum);
        System.out.println(totalCalories);
        totalCalories = menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
        // get 보다는 orElse나 orThrow을 사용해서 값을 가져오는 것이 좋다.(null 가능성 때문) 하지만 빈 스트림이 아님이 확실하기 때문에 get 사용
        System.out.println(totalCalories);
        totalCalories = menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println(totalCalories);

        // 마지막 방법이 최적의 해법이다. 가독성, 편의성, 성능 모두!

        // 도전. joining을 reducing 컬렉터로 구현해보기
        nameOfMenus = menu.stream().map(Dish::getName).collect(Collectors.reducing((s1, s2) -> s1 + s2)).get();
        System.out.println(nameOfMenus);
        nameOfMenus = menu.stream().collect(Collectors.reducing("", Dish::getName, (s1, s2) -> s1 + s2));
        System.out.println(nameOfMenus);
    }
}
