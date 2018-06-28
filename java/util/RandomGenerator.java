package util;

import java.util.Random;

public class RandomGenerator {
    private static Random r;

    static {
        long seed = System.currentTimeMillis();
        r = new Random(seed);
    }

    public static final Generator<Double> DOUBLE = () -> r.nextDouble();
    public static final Generator<String> INT_STRING =
        () -> Integer.valueOf(r.nextInt(1000)).toString();
    public static final Generator<String> STRING = new Generator<String>() {
        @Override
        public String next() {
            String base = "abcdefghijklmnopqrstuvwxyz";
            StringBuilder sb = new StringBuilder();
            int length = r.nextInt(5) + 1;

            for (int i = 0; i < length; i++) {
                int number = r.nextInt(base.length());
                sb.append(base.charAt(number));
            }

            return sb.toString();
        }
    };
}
