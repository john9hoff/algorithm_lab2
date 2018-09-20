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

    /**
     * @param arr the array to be partitioned
     * @param first
     * @param last
     * @return returns the pivot index. Elements from first to last-1 will be partitioned, so that everything less than
     * the pivot is before the pivot, and everything greater than the pivot is after
     */
    private static int partition(TestInteger[] arr, int first, int last) {
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
        return pivotIndex;
    }

    /**
     * @param arr the array to be sorted
     * @param first
     * @param last
     * all values from first to last will be sorted. Quicksort partitions the array, and then calls quicksort on each
     * of the partitions
     */
    public static void quicksort(TestInteger[] arr, int first, int last) {

        if (last-first <= 1) {
            return;
        }

        int pivotIndex = partition(arr, first, last);

        quicksort(arr, first, pivotIndex);
        quicksort(arr, pivotIndex+1, last);

    }

    /**
     * @param arr the array to be sorted
     * @param first
     * @param last
     * quicksort, but the pivot will be a random value
     */
    public static void randomQuicksort(TestInteger[] arr, int first, int last) {

        if (last-first <= 1) {
            return;
        }

        Random random = new Random();
        // pick a random pivot between first and last
        int i = random.nextInt(last-first) + first;
        // put the pivot value in the first spot
        TestInteger pivotValue = arr[i];
        arr[i] = arr[first];
        arr[first] = pivotValue;

        int pivotIndex = partition(arr, first, last);

        randomQuicksort(arr, first, pivotIndex);
        randomQuicksort(arr, pivotIndex+1, last);

    }

    /**
     * @param arr the array to be sorted
     * @param first
     * @param last
     * quicksort, but the pivot will be the median of 3 random values
     */
    public static void medianQuicksort(TestInteger[] arr, int first, int last) {

        if (last-first <= 1) {
            return;
        }

        int k = 10;
        if (last-first <= k) {
            quicksort(arr, first, last);
            return;
        }

        medianPivot(arr, first, last);

        int pivotIndex = partition(arr, first, last);

        medianQuicksort(arr, first, pivotIndex);
        medianQuicksort(arr, pivotIndex+1, last);

    }

    /**
     * @param arr
     * @param first
     * @param last
     * chooses 3 random values between first and last, then compares them to find the median. Then swaps the median with
     * the value at the first index
     */
    public static void medianPivot (TestInteger[] arr, int first, int last) {
        Random random = new Random();
        // pick a 3 random pivots between first and last
        int i = random.nextInt(last-first) + first;
        int j = random.nextInt(last-first) + first;
        int k = random.nextInt(last-first) + first;

        int medianIndex = 0;

        if (arr[i].compareTo(arr[j]) > 0) {
            if (arr[j].compareTo(arr[k]) > 0) {
                // j is the median
                medianIndex = j;
            } else if (arr[i].compareTo(arr[k]) > 0) {
                // k is the median
                medianIndex = k;
            } else {
                // i is the median
                medianIndex = i;
            }
        } else {
            if (arr[i].compareTo(arr[k]) > 0) {
                // i is the median
                medianIndex = i;
            } else if (arr[j].compareTo(arr[k]) > 0) {
                // k is the median
                medianIndex = k;
            } else {
                // j is the median
                medianIndex = j;
            }
        }

        // put the pivot value in the first spot
        TestInteger pivotValue = arr[medianIndex];
        arr[medianIndex] = arr[first];
        arr[first] = pivotValue;
    }

    /**
     * @param arr the array to be sorted
     * @param first
     * @param last
     * median quicksort, but changes to insertion sort for the last 10 elements of each partition
     */
    public static void quicksertionSort(TestInteger[] arr, int first, int last) {

        if (last-first <= 10) {
            insertionSort(arr, first, last);
            return;
        }

        medianPivot(arr, first, last);

        int pivotIndex = partition(arr, first, last);

        quicksertionSort(arr, first, pivotIndex);
        quicksertionSort(arr, pivotIndex+1, last);

    }

    /**
     * @param arr
     * @param first
     * @param last
     * implements the books version of insertion sort
     */
    public static void insertionSort(TestInteger[] arr, int first, int last) {
        for (int j = first + 1; j < last; j++) {
            TestInteger key = arr[j];
            int i = j-1;

            while (i >= first && (arr[i].compareTo(key) > 0)) {
                arr[i+1] = arr[i];
                i--;
            }
            arr[i+1] = key;
        }
    }


    public static void main(String[] args) {

        Random random = new Random();

        TestInteger[] arr1 = new TestInteger[10000];
        TestInteger[] arr2;
        TestInteger[] arr3;
        TestInteger[] arr4;
        TestInteger[] arr5;

        // put random values in the array

        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = new TestInteger(random.nextInt(1000000));
        }

        // make a second array that is a clone of the first

        arr2 = arr1.clone();
        arr3 = arr1.clone();
        arr4 = arr1.clone();
        arr5 = arr1.clone();

        // Sort with Tim Sort
        Arrays.sort(arr1);
        System.out.println("Number of comparisons using Tim Sort for random numbers: " + TestInteger.counter);
        TestInteger.counter = 0;

        // Sort with quicksort
        TestInteger.quicksort(arr2, 0, arr2.length);
        System.out.println("Number of comparisons using quicksort for random numbers: " + TestInteger.counter);
        isSorted(arr2);
        TestInteger.counter = 0;

        // Sort with random quicksort
        TestInteger.randomQuicksort(arr3, 0, arr2.length);
        System.out.println("Number of comparisons using random quicksort for random numbers: " + TestInteger.counter);
        isSorted(arr3);
        TestInteger.counter = 0;

        // Sort with median quicksort
        TestInteger.medianQuicksort(arr4, 0, arr2.length);
        System.out.println("Number of comparisons using median quicksort for random numbers: " + TestInteger.counter);
        isSorted(arr4);
        TestInteger.counter = 0;

        // Sort with quicksertionsort
        TestInteger.quicksertionSort(arr5, 0, arr2.length);
        System.out.println("Number of comparisons using quicksertionsort for random numbers: " + TestInteger.counter);
        isSorted(arr5);
        TestInteger.counter = 0;

        System.out.println();

        // sort an array that's already been sorted with Tim Sort
        Arrays.sort(arr1);
        System.out.println("Number of comparisons using Tim Sort for sorted numbers: " + TestInteger.counter);
        TestInteger.counter = 0;

        // Sort with quicksort on a sorted array
        TestInteger.quicksort(arr2, 0, arr2.length);
        System.out.println("Number of comparisons using quicksort for sorted numbers: " + TestInteger.counter);
        isSorted(arr2);
        TestInteger.counter = 0;

        // Sort with random quicksort on a sorted array
        TestInteger.randomQuicksort(arr3, 0, arr2.length);
        System.out.println("Number of comparisons using random quicksort for sorted numbers: " + TestInteger.counter);
        isSorted(arr3);
        TestInteger.counter = 0;

        // Sort with median quicksort on a sorted array
        TestInteger.medianQuicksort(arr4, 0, arr2.length);
        System.out.println("Number of comparisons using median quicksort for sorted numbers: " + TestInteger.counter);
        isSorted(arr4);
        TestInteger.counter = 0;

        // Sort with quicksertionsort on a sorted array
        TestInteger.quicksertionSort(arr5, 0, arr2.length);
        System.out.println("Number of comparisons using quicksertionsort for sorted numbers: " + TestInteger.counter);
        isSorted(arr5);
        TestInteger.counter = 0;

        System.out.println();

        // create an array with 10 sorted sequences of 1000 elements each
        arr1 = new TestInteger[10000];

        for (int i = 0; i < 10; i++) {

            arr1[i*1000] = new TestInteger(random.nextInt(1000000));

            for (int j = 1000 * i + 1; j < 1000 * (1 + i); j++) {
                arr1[j] = new TestInteger(arr1[j-1].value + 1);
            }
        }

        arr2 = arr1.clone();
        arr3 = arr1.clone();
        arr4 = arr1.clone();
        arr5 = arr1.clone();

        // Sort somewhat-sorted array with Tim Sort
        Arrays.sort(arr1);
        System.out.println("Number of comparisons using Tim Sort for 10 sorted sequences: " + TestInteger.counter);
        TestInteger.counter = 0;

        // Sort somewhat-sorted array with quicksort
        TestInteger.quicksort(arr2, 0, arr2.length);
        System.out.println("Number of comparisons using quicksort for 10 sorted sequences: " + TestInteger.counter);
        isSorted(arr2);
        TestInteger.counter = 0;

        // Sort somewhat-sorted array with random quicksort
        TestInteger.randomQuicksort(arr3, 0, arr2.length);
        System.out.println("Number of comparisons using random quicksort for 10 sorted sequences: " + TestInteger.counter);
        isSorted(arr3);
        TestInteger.counter = 0;

        // Sort somewhat-sorted array with median quicksort
        TestInteger.medianQuicksort(arr4, 0, arr2.length);
        System.out.println("Number of comparisons using median quicksort for 10 sorted sequences: " + TestInteger.counter);
        isSorted(arr4);
        TestInteger.counter = 0;

        // Sort somewhat-sorted array with quicksertionsort
        TestInteger.quicksertionSort(arr5, 0, arr2.length);
        System.out.println("Number of comparisons using quicksertionsort for 10 sorted sequences: " + TestInteger.counter);
        isSorted(arr5);
        TestInteger.counter = 0;

        System.out.println();

        // create an array with 100 sorted sequences of 100 elements each
        arr1 = new TestInteger[10000];

        for (int i = 0; i < 100; i++) {

            arr1[i*100] = new TestInteger(random.nextInt(1000000));

            for (int j = 100 * i + 1; j < 100 * (1 + i); j++) {
                arr1[j] = new TestInteger(arr1[j-1].value + 1);
            }
        }

        arr2 = arr1.clone();
        arr3 = arr1.clone();
        arr4 = arr1.clone();
        arr5 = arr1.clone();

        // Sort more-sorted array with Tim Sort
        Arrays.sort(arr1);
        System.out.println("Number of comparisons using Tim Sort for 100 sorted sequences: " + TestInteger.counter);
        TestInteger.counter = 0;

        // Sort more-sorted array with quicksort
        TestInteger.quicksort(arr2, 0, arr2.length);
        System.out.println("Number of comparisons using quicksort for 100 sorted sequences: " + TestInteger.counter);
        isSorted(arr2);
        TestInteger.counter = 0;

        // Sort more-sorted array with random quicksort
        TestInteger.randomQuicksort(arr3, 0, arr2.length);
        System.out.println("Number of comparisons using random quicksort for 100 sorted sequences: " + TestInteger.counter);
        isSorted(arr3);
        TestInteger.counter = 0;

        // Sort more-sorted array with median quicksort
        TestInteger.medianQuicksort(arr4, 0, arr2.length);
        System.out.println("Number of comparisons using median quicksort for 100 sorted sequences: " + TestInteger.counter);
        isSorted(arr4);
        TestInteger.counter = 0;

        // Sort more-sorted array with quicksertionSort
        TestInteger.quicksertionSort(arr5, 0, arr2.length);
        System.out.println("Number of comparisons using quicksertionsort for 100 sorted sequences: " + TestInteger.counter);
        isSorted(arr5);
        TestInteger.counter = 0;

    }
}

/** Lab 2 measurements and observations **/
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

/** Lab 3 Observations **/
// random quicksort performed the same on every kind of array, but was never the best. It was better than regular
// quicksort, especially for sorted arrays because it usually doesn't pick the first item as the pivot and trigger the
// worst case.

// median quicksort was the best quicksort for random numbers, and was better than random quicksort at everything. This
// because it never picks the highest or lowest number as a pivot, so the partitions are more likely to be equal.

// quicksertion sort was the best quicksort for everything but totally random numbers, where it was slightly worse than
// median quicksort. This shows that insertion sort is faster for small arrays than quicksort, and is better for nearly
// sorted arrays in general.