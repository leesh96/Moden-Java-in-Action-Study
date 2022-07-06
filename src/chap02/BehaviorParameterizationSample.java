package chap02;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static chap02.AppleColor.GREEN;
import static chap02.AppleColor.RED;

public class BehaviorParameterizationSample {

    public static void main(String[] args) {
        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple(8, GREEN));
        apples.add(new Apple(5, RED));
        apples.add(new Apple(4, GREEN));
        apples.add(new Apple(9, RED));

        // 사과 리스트를 필터링하는 요구사항에 대응하기.
        // 1단계 호출
        List<Apple> greenApples = filterGreenApples(apples);
        System.out.println(greenApples);

        // 2단계 호출
        List<Apple> redApples = filterApplesByColor(apples, RED);
        System.out.println(redApples);

        // 3단계 호출
        List<Apple> weightEqualsFiveApples = filterApplesByWeight(apples, 5);
        System.out.println(weightEqualsFiveApples);

        // 동작 파라미터화: 변화하는 요구사항에 유연하게 대응하기
        // 메소드의 동작이 파라미터화 된다. 즉 메소드가 어떤 동작을 할것인지 나중에 코드가 실행될 때 정해진다.

        // 4단계 호출
        // 동작 파라미터화된 필터링 메소드 호출
        List<Apple> heavyApples = filterApples(apples, new HeavyApplePredicate());
        System.out.println(heavyApples);

        greenApples = filterApples(apples, new GreenColorApplePredicate());
        System.out.println(greenApples);

        // ApplePredicate를 구현하고 인스턴스화하는 과정도 번거롭고 시간이 걸리는 작업이다.

        // 개선 1. 익명 클래스 사용 - 클래스 선언과 인스턴스화를 동시에 할 수 있다.
        redApples = filterApples(apples, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return apple.getColor().equals(RED);
            }
        });
        System.out.println(redApples);

        // 익명 클래스는 클래스의 선언과 인스턴스화를 동시에 했을 뿐 코드는 그대로 많다. 또한, this 참조와 관련된 문제가 발생할 수 있다.

        // 개선 2. 람다 사용
        List<Apple> lightApples = filterApples(apples, (Apple apple) -> apple.getWeight() <= 5);
        System.out.println(lightApples);

        // 예제 1. 스트림 API - 람다 사용
        List<Apple> redAndHeavyApples = apples.stream()
                .filter(apple -> apple.getColor().equals(RED) && apple.getWeight() > 5)
                .collect(Collectors.toList());
        System.out.println(redAndHeavyApples);

        // 예제 2. Comparator로 정렬하기 - 람다 사용
        apples.sort((a1, a2) -> a1.getWeight() - a2.getWeight());
        System.out.println(apples);
    }

    // 1단계. 녹색 사과를 필터링
    public static List<Apple> filterGreenApples(List<Apple> apples) {
        List<Apple> ret = new ArrayList<>();

        for (Apple apple : apples) {
            if (apple.getColor().equals(GREEN)) {
                ret.add(apple);
            }
        }

        return ret;
    }

    // 만약 녹색이 아닌 다른 색으로 사과를 필터링하고 싶다면?
    // 2단계. 색을 파라미터화
    public static List<Apple> filterApplesByColor(List<Apple> apples, AppleColor color) {
        List<Apple> ret = new ArrayList<>();

        for (Apple apple : apples) {
            if (apple.getColor().equals(color)) {
                ret.add(apple);
            }
        }

        return ret;
    }

    // 색이 아닌 다른 조건으로 사과를 필터링하고 싶다면? - 해당 조건으로 필터링하는 함수를 하나하나 만들어야 한다.
    // 문제점: 매개변수로 전달한 무게보다 큰 사과, 작은 사과, 동일한 사과에 대한 모든 필터링 함수를 만들어야 함.
    // 즉, 조건이 많아질수록 번거로운 작업, 내부 동작만 다른 중복 코드 발생
    public static List<Apple> filterApplesByWeight(List<Apple> apples, int weight) {
        List<Apple> ret = new ArrayList<>();

        for (Apple apple : apples) {
            if (apple.getWeight() == weight) {
                ret.add(apple);
            }
        }

        return ret;
    }

    // 조건이 많아진다면?
    // 3단계. 가능한 모든 조건을 매개변수로 받는다. 색과 무게 중에 어떤 조건을 사용할 것인지 나타내는 flag 매개변수도 받는다.
    public static List<Apple> filterApples(List<Apple> apples, AppleColor color, int weight, boolean flag) {
        List<Apple> ret = new ArrayList<>();

        for (Apple apple : apples) {
            if ((flag && apple.getColor().equals(color)) || (!flag && apple.getWeight() == weight)) {
                ret.add(apple);
            }
        }

        return ret;
    }
    /*
    이 메소드는 나중에 조건이 더 추가되면 유연하게 대응할 수 없다.
    또한 코드 가독성 측면에서, 호출시에 마지막 인자로 true 또는 false가 전달됨이 자명한데, 뭘 의미하는지 알 수가 없다.
    조건이 복잡해지면 질수록 여러 if로 구성된 복잡한 필터링 또는 여러 조건의 중복된 필터링 함수를 만들어내야 한다.
    */

    // ApplePredicate를 구현해서 다양한 프레디케이트를 만들 수 있다.
    static class GreenColorApplePredicate implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return apple.getColor().equals(GREEN);
        }
    }

    static class HeavyApplePredicate implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 5;
        }
    }

    /*
    어떤 조건을 사용하는지에 따라 filter 메소드가 다르게 동작할 것을 예상할 수 있다.
    여기서 사용된 디자인 패턴이 전략 디자인 패턴이다.
    전략 디자인 패턴? 각 알고리즘(전략)을 캡슐화하는 인터페이스를 정의한 다음 런타임에 알고리즘(전략)을 선택하는 방법이다.
    ApplePredicate의 구현 클래스가 런타임에 선택되는 전략이다.
    */

    // 4단계. 추상적 조건으로 필터링 = 동작 파라미터화
    // 필터링 메소드가 다양한 동작을 받아서 내부적으로 다양한 동작을 수행할 수 있다. -> ApplePredicate를 사용하자
    // 프레디케이트 객체로 사과 필터링 선택 조건을 캡슐화
    public static List<Apple> filterApples(List<Apple> apples, ApplePredicate p) {
        List<Apple> ret = new ArrayList<>();

        for (Apple apple : apples) {
            if (p.test(apple)) {
                ret.add(apple);
            }
        }

        return ret;
    }
    // 변화에 유연하게 대응할 수 있고, 가독성이 뛰어난 코드가 만들어졌다.
    // 조건이 추가되면 ApplePredicate의 구현 클래스를 만들어서 전달하면 된다.

    // 도전. 사과 리스트를 인수로 받아 다양한 방법으로 문자열을 생성할 수 있도록 파라미터화된 prettyPrintApple 메소드 구현하기
    // AppleFormatter 인터페이스 참조.
    static class AppleWeightFormatter implements AppleFormatter {

        @Override
        public String format(Apple apple) {
            return String.format("This apple is %dg.", apple.getWeight());
        }
    }

    static class SimpleAppleFormatter implements AppleFormatter {

        @Override
        public String format(Apple apple) {
            return String.format("Apple{weight=%d, color=%s}", apple.getWeight(), apple.getColor());
        }
    }

    public String prettyPrintApple(List<Apple> apples, AppleFormatter formatter) {
        StringBuilder ret = new StringBuilder();

        for (Apple apple : apples) {
            ret.append(formatter.format(apple));
            ret.append("\n");
        }

        return ret.toString();
    }

    // 개선 3. 모든 리스트에 대해 필터링이 가능하도록 리스트 형식으로 추상화 -> 제네릭 인터페이스 Predicate<T>를 사용하자
    static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> ret = new ArrayList<>();

        for (T e : list) {
            if (p.test(e)) {
                ret.add(e);
            }
        }

        return ret;
    }
}
