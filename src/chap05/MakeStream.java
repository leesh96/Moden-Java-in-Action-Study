package chap05;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MakeStream {

    public static void main(String[] args) {

        // 스트림을 만드는 다양한 방식

        // 1. 값으로 스트림 만들기
        Stream<String> bookTitleWords = Stream.of("Modern", "Java", "in", "Action");
        bookTitleWords.map(String::toUpperCase).forEach(System.out::println);

        // 빈 스트림 얻기
        Stream<String> emptyStream = Stream.empty();

        // 2. null이 될 수 있는 객체로 스트림 만들기 (자바 9)
        // 자바 9 이전
        String homeValue = System.getProperty("home");
        Stream<String> homeValueStream = homeValue == null ? Stream.empty() : Stream.of(homeValue);

        // 자바 9 이후
        Stream<String> homeValueStream2 = Stream.ofNullable(System.getProperty("home"));

        // 3. 배열로 스트림 만들기
        int[] numbers = {1, 3, 5, 7, 9};
        int sum = Arrays.stream(numbers).sum(); // IntStream 반환

        // 4. 파일로 스트림 만들기
        // I/O 연산에 사용하는 자바의 NIO API(non-blocking IO)도 스트림 API를 활용할 수 있다.

        // 5. 함수로 무한 스트림 만들기
        // 스트림 API는 함수에서 스트림을 만들 수 있는 두 정적 메소드 Stream.iterate와 Stream.generate를 제공한다.
        // 두 연산을 이용해서 무한 스트림을 만들 수 있다.

        // 짝수 스트림 만들기
        Stream.iterate(0, n -> n + 2)       // 초기값과 람다를 인수로 받아 새로운 값을 만들어낸다.
                .limit(10)
                .forEach(System.out::println);

        // 도전. 피보나치 수열 만들기
        Stream.iterate(new int[]{0, 1}, p -> new int[]{p[1], p[0] + p[1]})
                .limit(20)
                .forEach(p -> System.out.printf("(%d, %d)%n", p[0], p[1]));

        // 자바 9의 iterate 메소드는 프레디케이트를 지원한다. 프레디케이트를 인수로 받아 언제까지 값을 생성할지 기준으로 사용한다.
        // 100 이하의 피보나치 수열 만들기
        Stream.iterate(new int[]{0, 1}, p -> p[1] <= 100, p -> new int[]{p[1], p[0] + p[1]})
                .forEach(p -> System.out.printf("(%d, %d)%n", p[0], p[1]));

        // 위 코드를 filter로 똑같이 작성할 수 있을거라고 생각할 수 있다.
        // filter는 언제 작업을 중단해야 하는지 모르기 때문에 불가능하다. takeWhile을 사용해야 한다.

        // generate 메소드
        // iterate는 생산된 값을 가지고 연속적으로 계산해서 새로운 값을 생산한다.
        // generate는 Supplier<T>를 인수로 받아 새로운 값을 생산한다.
        // 로또 번호 생성기
        List<Integer> lottoNumbers = Stream.generate(() -> (int)(Math.random() * 45 + 1))
                .distinct()
                .limit(6)
                .sorted()
                .collect(Collectors.toList());
        System.out.println(lottoNumbers);

        // 값을 생성하는 Supplier<T>는 상태가 없는, 즉 다음 계산에 사용할 어떤 값도 저장해두지 않는 메소드이다.
        // 발행자가 상태를 갖도록 할 수도 있지만, 병렬 코드에서 안전하지 않다. -> 7장
        // generate를 사용해서 피보나치 수열 만들기
        IntStream.generate(new IntSupplier() {
            private int prev = 0;
            private int cur = 1;

            @Override
            public int getAsInt() {
                int oldPrev = this.prev;
                int next = this.prev + this.cur;
                this.prev = this.cur;
                this.cur = next;
                return oldPrev;
            }
        }).takeWhile(n -> n <= 100).forEach(System.out::println);

        // 상태를 갖는 발행자(만든 익명 클래스)를 사용했다.
        // 만들어진 객체는 기존 피보나치 값과 두 인스턴스 변수에 어떤 피보나치 요소가 들어있는지 추적하기 떄문에 가변 상태 객체이다.
        // getAsInt를 호출하면 객체의 상태가 바뀌고 새로운 값을 생산한다.
        // iterate를 사용했을 때는 각 과정에서 새로운 값을 생산해도 기존 상태를 바꾸지 않는 순수한 불변 상태를 유지했다.
        // 스트림을 병렬로 처리하면서 올바른 결과를 얻으려면 불변 상태 기법을 고수해야 한다.
    }
}
