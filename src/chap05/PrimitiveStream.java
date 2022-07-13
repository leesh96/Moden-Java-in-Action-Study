package chap05;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimitiveStream {

    public static void main(String[] args) {

        // 자바 8에서는 세 가지 기본형 특화 스트림(primitive stream specialization)을 제공 -> IntStream, DoubleStream, LongStream
        // 각각의 인터페이스에는 숫자 관련 리듀싱 연산 수행 메소드를 제공한다. 필요할 때 다시 객체 스트림으로 복원하는 기능도 제공
        // 기본형 특화 스트림은 오직 박싱에서 일어나는 효율성에만 관련이 있다.

        // 숫자 스트림으로 변환하기
        List<Student> students = Student.getSampleList();
        int sumOfAge = students.stream()    // Stream<Student>
                .mapToInt(Student::getAge)  // IntStream
                .sum();
        System.out.println(sumOfAge);

        System.out.println();

        // 객체 스트림으로 복원하기
        IntStream intStream = students.stream().mapToInt(Student::getAge);
        Stream<Integer> stream = intStream.boxed();

        // sum은 빈 스트림의 경우 0을 반환한다. 최댓값, 최솟값과 같은 연산에서는 기본값 0 때매 다른 결과과 반환될 수 있다.
        // 이러한 경우 OptionalInt를 반환한다. OptionalInt는 Optional 컨테이너 클래스의 기본형 특화 스트림 버전이다.
        OptionalInt maxOfAge = students.stream().mapToInt(Student::getAge).max();
        System.out.println(maxOfAge.orElse(-1));

        System.out.println();

        // 숫자 범위 만들기
        // 자바 8의 IntStream과 LongStream에는 range와 rangeClosed의 두 가지 static 메소드를 제공한다. 각각 반열린 범위와 닫힌 범위를 만든다.
        IntStream rangeOfTen1 = IntStream.range(0, 11);         // (0, 11)
        IntStream rangeOfTen2 = IntStream.rangeClosed(1, 10);   // [1, 10]
        rangeOfTen1.forEach(System.out::println);
        rangeOfTen2.forEach(System.out::println);

        System.out.println();

        IntStream evenNumbers = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0);
        evenNumbers.forEach(System.out::println);

        System.out.println();
    }
}
