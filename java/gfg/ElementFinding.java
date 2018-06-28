package gfg;


class ElementFinding {
    /*
    Find the element that appears once
        Given an array where every element occurs three times, except one element
        which occurs only once. Find the element that occurs once. Expected time
        complexity is O(n) and O(1) extra space.

    Examples :
        Input: a[] = {12, 1, 12, 3, 12, 1, 1, 2, 3, 3}
        Output: 2

    Solution:
        Run a loop for all elements in array. At the end of every iteration,
        maintain following two values.

        once: The bits that have appeared 1st time or 4th time or 7th time .. etc.
        twice: The bits that have appeared 2nd time or 5th time or 8th time .. etc.

        Finally, we return the value of once
    */

    static int getSingle(int[] a) {
        int once = 0;
        int twice = 0;
        int thrice;

        for (int i = 0; i < a.length; i++) {
            // Get all bits appearing 2nd time(mod 3)
            twice |= once & a[i];

            // Get all bits appearing 1st time(mod 3)
            once ^= a[i];

            // The bits that appear third time
            thrice = once & twice;

            // Remove the bits that appear third time
            once &= ~thrice;
            twice &= ~thrice;
        }

        return once;
    }


    // Add the numbers bitwise mod 3
    // sum the bits in same positions for all the numbers and take modulo with 3
    // The bits for which sum is not multiple of 3, are the bits of number with
    // single occurrence.
    // Time Complexity : O(n)
    // Auxiliary Space : O(1)
    static int getSingle2(int[] a) {
        int result = 0;
        int x, sum;

        for (int i = 0; i < 32; i++) {
            sum = 0;
            x = 1 << i;

            // Count the number of the ith bit with value 0 in all elements
            for (int j = 0; j < a.length; j++) {
                if ((a[j] & x) == 0) {
                    sum++;
                }
            }

            // The bits with sum not multiple of 3, are the
            // bits of element with single occurrence.
            if ((sum % 3) == 0) {
                result |= x;
            }
        }

        return result;
    }


    public static void main(String args[]) {
        int a[] = {12, 1, 12, 3, 12, 1, 1, 2, 3, 3};

        System.out.println(getSingle(a));
        System.out.println(getSingle2(a));
    }
}
