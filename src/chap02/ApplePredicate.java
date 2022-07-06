package chap02;

/*
필터링할 때 사과를 선택할 조건을 결정해야 한다. 사과의 어떤 속성에 기초해서 불리언 값을 반환하는 방법이 있다.
선택 조건을 결정하는 인터페이스를 정의

Predicate? - 직역하면 술어인데, 인수로 값을 받아 참, 거짓을 반환하는 함수를 말한다.
*/
public interface ApplePredicate {

    boolean test(Apple apple);
}
