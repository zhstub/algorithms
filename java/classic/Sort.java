package classic;

import util.Filler;
import util.RandomGenerator;

import java.util.Arrays;
import java.util.Comparator;


public class Sort {
    public static <T extends Comparable<? super T>> void shellSort(T[] a) {
        shellSort(a, T::compareTo);
    }

    public static <T> void shellSort(T[] a, Comparator<? super T> c) {
        int n = a.length;

        // 3x+1 increment sequence:  1, 4, 13, 40, 121, 364, 1093, ...
        int h = 1;
        while (h < n / 3) h = 3 * h + 1;

        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && c.compare(a[j], a[j - h]) < 0; j -= h) {
                    swap(a, j, j - h);
                }
            }

            assert isHSorted(a, h, c);
            h /= 3;
        }

        assert isSorted(a, c);
    }


    private static void swap(Object[] a, int i, int j) {
        Object tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static <T> boolean isSorted(T[] a, Comparator<? super T> c) {
        for (int i = 1; i < a.length; i++) {
            if (c.compare(a[i], a[i - 1]) < 0) {
                return false;
            }
        }

        return true;
    }

    private static <T> boolean isHSorted(T[] a, int h, Comparator<? super T> c) {
        for (int i = h; i < a.length; i++) {
            if (c.compare(a[i], a[i - h]) < 0) {
                return false;
            }
        }

        return true;
    }


    public static void main(String[] args) {
        String a[] = new String[]{"just", "a", "what", "fifth", "ok", "two", "one", "three"};
        shellSort(a, Comparator.comparingInt(String::length).thenComparing(String::compareTo));
        System.out.println(Arrays.toString(a));

        String a2[] = new String[10];
        Filler.fill(a2, RandomGenerator.STRING);
        shellSort(a2, Comparator.comparingInt(String::length).thenComparing(String::compareTo));
        System.out.println(Arrays.toString(a2));
    }
}
