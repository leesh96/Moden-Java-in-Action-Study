package chap05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimitiveStreamPythagorasSample {

    public static void main(String[] args) {

        Stream<List<Integer>> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed() // a 만들기
                .flatMap(a -> IntStream.rangeClosed(1, 100) // b 만들기
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0) // 그 중에서 a * a + b * b의 제곱근이 정수인지 필터링
                        .mapToObj(b -> Arrays.asList(a, b, (int) Math.sqrt(a * a + b * b))) // IntStream을 다시 객체 스트림으로
                );
        pythagoreanTriples.limit(10)
                .forEach(t -> System.out.printf("%d, %d, %d%n", t.get(0), t.get(1), t.get(2)));

        // 위의 연산은 제곱근 계산을 두 번 수행한다.

        Stream<double[]> pythagoreanTriples2 = IntStream.range(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(1, 100)
                        .mapToObj(b -> new double[] { a, b, Math.sqrt(a * a + b * b) }))
                .filter(t -> t[2] % 1 == 0);

    }
}
