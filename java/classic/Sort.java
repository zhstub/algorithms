package classic;

import util.Filler;
import util.RandomGenerator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;


public class Sort {
    private static long cmpCount = 0;
    private static long swapCount = 0;

    public static <T extends Comparable<? super T>> void bubbleSort(T[] a) {
        bubbleSort(a, T::compareTo);
    }

    // Reducing unnecessary swaps, bubbleSort is equivalent to selectionSort
    public static <T> void bubbleSort(T[] a, Comparator<? super T> c) {
        int n = a.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 1; j < n - i; j++) {
                if (less(a[j], a[j - 1], c)) {
                    swap(a, j, j - 1);
                }
            }
        }

        assert isSorted(a, c);
    }


    public static <T extends Comparable<? super T>> void selectionSort(T[] a) {
        selectionSort(a, T::compareTo);
    }

    public static <T> void selectionSort(T[] a, Comparator<? super T> c) {
        int n = a.length;

        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (less(a[j], a[min], c)) {
                    min = j;
                }
            }

            swap(a, i, min);
        }

        assert isSorted(a, c);
    }


    public static <T extends Comparable<? super T>> void insertionSort(T[] a) {
        insertionSort(a, T::compareTo);
    }

    public static <T> void insertionSort(T[] a, Comparator<? super T> c) {
        int n = a.length;

        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1], c); j--) {
                swap(a, j, j - 1);
            }
        }

        assert isSorted(a, c);
    }

    public static <T> void insertionSort(T[] a, int lo, int hi, Comparator<? super T> c) {
        for (int i = lo + 1; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1], c); j--) {
                swap(a, j, j - 1);
            }
        }

        assert isSorted(a, lo, hi, c);
    }


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
                for (int j = i; j >= h && less(a[j], a[j - h], c); j -= h) {
                    swap(a, j, j - h);
                }
            }

            h /= 3;
        }

        assert isSorted(a, c);
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *  Helper functions
     * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private static <T> boolean less(T v, T w, Comparator<? super T> c) {
        cmpCount++;
        return c.compare(v, w) < 0;
    }

    private static <T> void swap(T[] a, int i, int j) {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;

        swapCount++;
    }

    private static <T> boolean isSorted(T[] a, Comparator<? super T> c) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1], c)) {
                return false;
            }
        }

        return true;
    }

    private static <T> boolean isSorted(T[] a, int lo, int hi, Comparator<? super T> c) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i - 1], c)) {
                return false;
            }
        }

        return true;
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *  Test
     * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public static long measureTime(Consumer<Double[]> alg, int n, int trials) {
        long total = 0;
        Double[] a = new Double[n];

        for (int t = 0; t < trials; t++) {
            Filler.fill(a, RandomGenerator.DOUBLE);

            long start = System.nanoTime();
            alg.accept(a);
            long now = System.nanoTime();
            total += now - start;
        }

        return total / 1000000;
    }

    public static void compareSort(Consumer<Double[]> alg1, Consumer<Double[]> alg2,
                                   int n, int trials) {
        System.out.format("%d random doubles, %d trials\n", n, trials);

        cmpCount = 0;
        swapCount = 0;

        long time1 = measureTime(alg1, n, trials);

        System.out.format("alg1, time: %4dms, cmp: %d, swap: %d\n",
            time1, cmpCount, swapCount);

        long tmpCmpCount = cmpCount;
        long tmpSwapCount = swapCount;
        cmpCount = 0;
        swapCount = 0;

        long time2 = measureTime(alg2, n, trials);

        System.out.format("alg2, time: %4dms, cmp: %d, swap: %d\n",
            time2, cmpCount, swapCount);

        System.out.format("ratio, time: %.2f, cmp: %.2f, swap: %.2f\n",
            (double)time1 / time2,
            (double)tmpCmpCount / cmpCount,
            (double)tmpSwapCount / swapCount);
    }

    public static void compareSort2(Consumer<Double[]> alg1, Consumer<Double[]> alg2,
                                    int n, int trials) {
        long time1 = 0;
        long time2 = 0;
        Double[] a = new Double[n];

        for (int t = 0; t < trials; t++) {
            Filler.fill(a, RandomGenerator.DOUBLE);

            long start = System.nanoTime();
            alg1.accept(a);
            long now = System.nanoTime();
            time1 += now - start;

            Filler.fill(a, RandomGenerator.DOUBLE);

            start = System.nanoTime();
            alg2.accept(a);
            now = System.nanoTime();
            time2 += now - start;
        }

        time1 /= 1000000;
        time2 /= 1000000;

        System.out.format("%d random doubles, %d trials\n", n, trials);
        System.out.format("alg1: %dms, alg2: %dms, ratio: %.2f\n",
            time1, time2, (double)time1 / time2);
    }


    public static void main(String[] args) {
        String a[] = new String[]{"just", "a", "what", "fifth", "ok", "two", "one", "three"};
        shellSort(a, Comparator.comparingInt(String::length).thenComparing(String::compareTo));
        System.out.println(Arrays.toString(a));

        String a2[] = new String[10];
        Filler.fill(a2, RandomGenerator.STRING);
        selectionSort(a2, Comparator.comparingInt(String::length).thenComparing(String::compareTo));
        System.out.println(Arrays.toString(a2));

        System.out.println();
        compareSort(Sort::selectionSort, Sort::insertionSort, 2000, 100);
        System.out.println();
        compareSort2(Sort::selectionSort, Sort::insertionSort, 2000, 100);
    }
}
