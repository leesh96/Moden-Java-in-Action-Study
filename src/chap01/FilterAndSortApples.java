package chap01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static chap01.AppleColor.GREEN;
import static chap01.AppleColor.RED;

public class FilterAndSortApples {

    public static void main(String[] args) {
        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple(8, GREEN));
        apples.add(new Apple(5, RED));
        apples.add(new Apple(4, GREEN));
        apples.add(new Apple(9, RED));

        // 자바 8 이전의 정렬
        Collections.sort(apples, new Comparator<Apple>() {
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });
        System.out.println(apples);

        // 자바 8 이후의 정렬 - 메소드 참조
        apples.sort(Comparator.comparing(Apple::getWeight));
        System.out.println(apples);

        // 자바 8 이전의 필터링
        List<Apple> heavyApples = filterGreenApples(apples);
        System.out.println(heavyApples);

        // 자바 8 이후의 필터링 - 메소드 참조
        heavyApples = filterApples(apples, Apple::isHeavy);
        System.out.println(heavyApples);

        // 자바 8 이후의 필터링 - 람다
        List<Apple> greenApples = filterApples(apples, (Apple a) -> a.isGreen()); // 밑줄은 람다를 메소드 참조로 대체할 수 있기 때문
        System.out.println(greenApples);

        // 스트림 API 사용하기 - 메소드 참조와 람다 모두 사용 가능
        heavyApples = apples.stream().filter((Apple a) -> a.isHeavy())
                .collect(Collectors.toList());
        System.out.println(heavyApples);

        long startTime = System.nanoTime();
        greenApples = apples.stream().filter(Apple::isGreen)
                .collect(Collectors.toList());
        long endTime = System.nanoTime();
        System.out.println("수행시간: " + (endTime - startTime));
        System.out.println(greenApples);

        // 병렬 스트림(위는 순차 스트림)
        startTime = System.nanoTime();
        greenApples = apples.parallelStream().filter(Apple::isGreen)
                .collect(Collectors.toList());
        endTime = System.nanoTime();
        System.out.println("수행시간: " + (endTime - startTime));
        System.out.println(greenApples);

        /*
        실제 측정 시간은 병렬 스트림이 더 오래걸리는 것을 볼 수 있다.
        왜? 병렬 스트림에서 발생하는 스레드 오버헤드 때문에 소량의 데이터에 대해선 순차 스트림이 더 뛰어난 성능을 보여준다.
        */
    }

    // 자바 8 이전의 필터링
    public static List<Apple> filterGreenApples(List<Apple> apples) {
        List<Apple> ret = new ArrayList<>();

        for (Apple apple : apples) {
            if (apple.getColor().equals("Green")) {
                ret.add(apple);
            }
        }

        return ret;
    }

    public static List<Apple> filterHeavyApples(List<Apple> apples) {
        List<Apple> ret = new ArrayList<>();

        for (Apple apple : apples) {
            if (apple.getWeight() > 5) {
                ret.add(apple);
            }
        }

        return ret;
    }

    // 자바 8 이후의 필터링 - 메소드 참조와 람다
    public static List<Apple> filterApples(List<Apple> apples, Predicate<Apple> p) {
        List<Apple> ret = new ArrayList<>();

        for (Apple apple : apples) {
            if (p.test(apple)) {
                ret.add(apple);
            }
        }

        return ret;
    }
}