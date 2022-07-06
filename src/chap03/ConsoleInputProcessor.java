package chap03;

import java.io.BufferedReader;
import java.io.IOException;

// 2단계: 람다를 사용하려면? 함수형 인터페이스 자리에 람다를 사용할 수 있다.
// BufferedReader -> String과 IOException을 던질 수 있는 시그니처와 일치하는 함수형 인터페이스 만들기
@FunctionalInterface
public interface ConsoleInputProcessor {

    String process(BufferedReader br) throws IOException;
}
