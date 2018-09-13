import com.sun.corba.se.impl.orb.ParserTable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class TestInteger implements Comparable<TestInteger> {

    public int value;
    public static long counter;

    public TestInteger(int v) {
        this.value = v;
    }

    @Override
    public String toString() {
        String str = Integer.toString(value);
        return str;
    }

    @Override
    public int compareTo(TestInteger o) {

        counter++;
        return this.value - o.value;
    }

    public static void isSorted(TestInteger[] arr) {
        for (int i = 0; i<arr.length-1; i++) {
            if (arr[i].compareTo(arr[i+1])>0) {
                System.out.println("This array is not sorted properly");
            }
        }
    }

    public static void quicksort(TestInteger[] arr, int first, int last) {

        if (last-first <= 1) {
            return;
        }

        int pivotIndex = first;
        TestInteger pivotValue = arr[first];
        int firstUnknown = pivotIndex+1;

        while(firstUnknown<last) {
            if (arr[firstUnknown].compareTo(pivotValue)<0) {
                pivotIndex++;
                TestInteger tmp = arr[pivotIndex];
                arr[pivotIndex] = arr[firstUnknown];
                arr[firstUnknown] = tmp;
            }
            firstUnknown++;
        }
        arr[first] = arr[pivotIndex];
        arr[pivotIndex] = pivotValue;

        quicksort(arr, first, pivotIndex);
        quicksort(arr, pivotIndex+1, last);

    }

    public static void main(String[] args) {

        Random random = new Random();

        TestInteger[] arr1 = new TestInteger[10000];
        TestInteger[] arr2;

        // put random values in the array

        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = new TestInteger(random.nextInt(1000000));
        }

        // make a second array that is a clone of the first

        arr2 = arr1.clone();

        // Sort with Tim Sort
        Arrays.sort(arr1);
        System.out.println("Number of comparisons using Tim Sort for random numbers: " + TestInteger.counter);
        TestInteger.counter = 0;

        // Sort with quicksort
        TestInteger.quicksort(arr2, 0, arr2.length);
        System.out.println("Number of comparisons using quicksort for random numbers: " + TestInteger.counter);
        isSorted(arr2);
        TestInteger.counter = 0;

        // sort an array that's already been sorted with Tim Sort
        Arrays.sort(arr1);
        System.out.println("Number of comparisons using Tim Sort for sorted numbers: " + TestInteger.counter);
        TestInteger.counter = 0;

        // Sort with quicksort on a sorted array
        TestInteger.quicksort(arr2, 0, arr2.length);
        System.out.println("Number of comparisons using quicksort for sorted numbers: " + TestInteger.counter);
        isSorted(arr2);
        TestInteger.counter = 0;

        // create an array with 10 sorted sequences of 1000 elements each
        arr1 = new TestInteger[10000];

        for (int i = 0; i < 10; i++) {

            arr1[i*1000] = new TestInteger(random.nextInt(1000000));

            for (int j = 1000 * i + 1; j < 1000 * (1 + i); j++) {
                arr1[j] = new TestInteger(arr1[j-1].value + 1);
            }
        }

        arr2 = arr1.clone();

        // Sort somewhat-sorted array with Tim Sort
        Arrays.sort(arr1);
        System.out.println("Number of comparisons using Tim Sort for 10 sorted sequences: " + TestInteger.counter);
        TestInteger.counter = 0;

        // Sort somewhat-sorted array with quicksort
        TestInteger.quicksort(arr2, 0, arr2.length);
        System.out.println("Number of comparisons using quicksort for 10 sorted sequences: " + TestInteger.counter);
        isSorted(arr2);
        TestInteger.counter = 0;

        // create an array with 100 sorted sequences of 100 elements each
        arr1 = new TestInteger[10000];

        for (int i = 0; i < 100; i++) {

            arr1[i*100] = new TestInteger(random.nextInt(1000000));

            for (int j = 100 * i + 1; j < 100 * (1 + i); j++) {
                arr1[j] = new TestInteger(arr1[j-1].value + 1);
            }
        }

        // Sort more-sorted array with Tim Sort
        arr2 = arr1.clone();
        Arrays.sort(arr1);
        System.out.println("Number of comparisons using Tim Sort for 100 sorted sequences: " + TestInteger.counter);
        TestInteger.counter = 0;

        // Sort more-sorted array with quicksort
        TestInteger.quicksort(arr2, 0, arr2.length);
        System.out.println("Number of comparisons using quicksort for 100 sorted sequences: " + TestInteger.counter);
        isSorted(arr2);
        TestInteger.counter = 0;

    }
}

/** Results of measurements **/
// Tim Sort for 10,000 random TestIntegers: 120376, 120411, 120430, 120424, 120335
// Quicksort for 10,000 random TestIntegers: 165791, 161507, 149945, 152829, 158246
// For random numbers, the two algorithms were close but Tim Sort was better

// Tim Sort for 10,000 sorted numbers: 9999
// Quicksort for 10,000 sorted numbers: 49995000
// For a sorted array, Tim Sort was extremely fast and quicksort was abysmal. This is because Tim Sort is stable and
// doesn't move already sorted numbers, while quicksort compares everything in the array many times. Part of the reason
// our quicksort is so bad is because we use the first item as the pivot every time.

// Tim Sort for 10 sorted sequences of 1,000 elements each: 10276, 10244, 10299, 10339, 10265
// Quicksort for 10 sorted sequences of 1,000 elements each: 20709868, 13617518, 15565391, 10572391, 14551230
// For these 10 sorted sequences, Tim Sort was again very fast, while quicksort made a lot of comparisons. It seems that
// quicksort is very bad at sorting already sorted arrays.

// Tim Sort for 100 sorted sequences of 100 elements each: 14407, 14235, 14698, 14716, 14210
// Quicksort for 100 sorted sequences of 100 elements each: 3464237, 3118949, 2660466, 1888684, 2871059
// Tim sort was slightly worse than with 10 sorted sequences and Quicksort was slightly better. It seems that the more
// random the array is the better quicksort is.