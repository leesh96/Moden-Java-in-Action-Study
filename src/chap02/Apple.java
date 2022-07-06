package chap02;

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
}
