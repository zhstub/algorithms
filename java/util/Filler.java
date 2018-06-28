package util;

public class Filler {
    public static <T> void fill(T[] a, Generator<T> g) {
        for (int i = 0; i < a.length; i++) {
            a[i] = g.next();
        }
    }
}