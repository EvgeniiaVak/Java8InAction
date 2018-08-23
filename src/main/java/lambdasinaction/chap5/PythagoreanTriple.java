package lambdasinaction.chap5;

import net.jcip.annotations.Immutable;

import java.util.stream.IntStream;

@Immutable
public class PythagoreanTriple {
    private final int a, b, c;

    public static void main(String[] args) {
        int begin = 1, end = 10;

        IntStream.rangeClosed(begin, end)
                .boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(a, end)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .mapToObj(b -> new PythagoreanTriple(a, b, (int)Math.sqrt(a * a + b * b))))
                .forEach(System.out::println);
    }

    public PythagoreanTriple(int a, int b, int c) {

        double calculatedC = Math.sqrt(a * a + b * b);
        if (calculatedC % 1 != 0 || calculatedC != c)
            throw new IllegalArgumentException();

        this.a = a;
        this.b = b;
        this.c = c;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    @Override
    public String toString() {
        return String.format("{%d, %d, %d}", a, b, c);
    }
}