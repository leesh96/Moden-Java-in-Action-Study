package chap01;

public class Apple {

    private final int weight;

    private final AppleColor color;

    public Apple(int weight, AppleColor color) {
        this.weight = weight;
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public AppleColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return String.format("Apple{weight=%d, color=%s}", weight, color);
    }

    // 자바 8 이후의 필터링 - 메소드 참조에서 사용하기 위한 함수
    public boolean isGreen() {
        return this.color.equals(AppleColor.GREEN);
    }

    public boolean isHeavy() {
        return this.weight > 5;
    }
}
