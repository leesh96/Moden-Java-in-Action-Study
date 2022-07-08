package chap05;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamReducing {

    public static void main(String[] args) {

        // 리듀싱 연산: 모든 스트림 요소를 처리해서 값으로 도출하는 연산

        // 1. 요소의 합
        // 1부터 10까지의 합을 구하는 방법
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // for-each 루프
        int sum = 0;
        for (int n : numbers) {
            sum += n;
        }
        System.out.println(sum);

        // sum 변수의 초기값 0, 리스트의 모든 요소를 조합하는 연산을 사용해서 1부터 10까지의 합을 구했다.
        // 모든 숫자를 곱하는 연산을 구현하려면 비슷한 코드를 다시 작성해야 한다.
        // reduce를 사용하면 애플리케이션의 반복된 패턴을 추상화할 수 있다.
        int sumOfNumbers = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sumOfNumbers);

        // reduce는 초기값과 두 요소를 조합해서 새로운 값을 만드는 BinaryOperator<T>를 인수로 갖는다. BinaryOperator<T>는 함수형 인터페이스
        // 1부터 10까지의 곱을 구해보자.
        int productOfNumbers = numbers.stream().reduce(1, (a, b) -> a * b);
        System.out.println(productOfNumbers);

        // 초기값 없는 reduce
        Optional<Integer> sum2 = numbers.stream().reduce((a, b) -> a + b);
        System.out.println(sum2.get());

        // 왜 Optional<T>?
        // 초기값이 없으면 스트림에 아무 요소가 없는 경우 값을 도출할 수 없다.

        // 2. 최대값과 최소값
        Optional<Integer> maxOfNumbers = numbers.stream().reduce(Integer::max);
        Optional<Integer> minOfNumbers = numbers.stream().reduce(Integer::min);
        System.out.printf("max: %d, min: %d%n", maxOfNumbers.get(), minOfNumbers.get());

        // 맵 리듀스 패턴
        // 스트림의 학생 수를 구해보자.
        List<Student> students = Student.getSampleList();
        int count = students.stream().map(student -> 1).reduce(0, (a, b) -> a + b);
        // map과 reduce를 연결하는 기법을 맵 리듀스 패턴이라고 한다. 쉽게 병렬화하는 특징이 있다.

        /*
        단계적 반복으로 합을 구하는 것과 reduce를 이용해 합을 구하는 것은 어떤 차이가 있는가?
        reduce를 이용하면 내부 반복이 추상화되면서 내부 구현에서 병렬로 실행할 수 있다.
        단계적 반복에서는 sum 변수를 공유해야 하기 때문에 병렬화가 어렵다. 강제적으로 동기화시키더라도 병렬화로 얻어지는 이득이 스레드 오버헤드 때문에 상쇄된다.
        stream을 parallelStream으로 바꾸면 병렬로 만들 수 있다.
        */

        /*
        상태 없는 연산과 상태 있는 연산
        map, filter 같은 연산은 입력 스트림에서 각 요소를 받아 0 또는 결과를 출력 스트림으로 보낸다. 따라서 내부 상태를 갖지 않는 연산이다. -> stateless operation
        (전달한 람다나 메소드 참조가 내부적인 가변 상태를 갖지 않아야 한다.)
        reduce, sum, max와 같은 연산은 중간 결과를 누적할 내부 상태가 필요하다. 스트림에서 처리하는 요소 수와 관계없이 내부 상태의 크기는 한정(bounded)되어 있다.
        sorted, distinct와 같은 연산은 스트림을 입력으로 받아 다른 스트림을 출력하기 때문에 stateless한 연산으로 보일 수 있다.
        하지만 스트림의 요소를 정렬하거나 중복을 제거하려면 과거의 이력을 알고 있어야 한다. 예를 들어 어떤 요소를 출력 스트림으로 추가하려면 모든 요소가 버퍼에 추가되어 있어야 한다.
        연산을 수행하는 데 필요한 저장소 크기는 정해져 있지 않다. 따라서 스트림의 크기가 크거나 무한대라면 문제가 발생한다. 이러한 연산을 내부 상태를 갖는 연산이라고 한다. -> stateful operation
        */
    }
}
