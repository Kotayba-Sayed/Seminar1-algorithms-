import java.util.Random;

public class QuickSort {
    // Recursive QuickSort
    public void quickSortRecursive(int[] array, int low, int high, String pivotStrategy) {
        if (low < high) {
            int pivotIndex = partition(array, low, high, pivotStrategy);
            quickSortRecursive(array, low, pivotIndex - 1, pivotStrategy);
            quickSortRecursive(array, pivotIndex + 1, high, pivotStrategy);
        }
    }

    // Iterative QuickSort
    public void quickSortIterative(int[] array, String pivotStrategy) {
        int[] stack = new int[array.length];
        int top = -1;
        stack[++top] = 0;
        stack[++top] = array.length - 1;

        while (top >= 0) {
            int high = stack[top--];
            int low = stack[top--];
            int pivotIndex = partition(array, low, high, pivotStrategy);

            if (pivotIndex - 1 > low) {
                stack[++top] = low;
                stack[++top] = pivotIndex - 1;
            }

            if (pivotIndex + 1 < high) {
                stack[++top] = pivotIndex + 1;
                stack[++top] = high;
            }
        }
    }

    private int partition(int[] array, int low, int high, String pivotStrategy) {
        int pivot = getPivot(array, low, high, pivotStrategy);
        int i = low;
        int j = high;
        while (i < j) {
            while (i <= high && array[i] <= pivot) i++;
            while (j >= low && array[j] > pivot) j--;
            if (i < j) swap(array, i, j);
        }
        swap(array, low, j);
        return j;
    }

    private int getPivot(int[] array, int low, int high, String pivotStrategy) {
        switch (pivotStrategy) {
            case "median-of-three":
                int mid = low + (high - low) / 2;
                return medianOfThree(array[low], array[mid], array[high]);
            case "random":
                Random rand = new Random();
                return array[low + rand.nextInt(high - low + 1)];
            default: // first element
                return array[low];
        }
    }

    private int medianOfThree(int a, int b, int c) {
        if ((a > b) ^ (a > c)) return a;
        if ((b > a) ^ (b > c)) return b;
        return c;
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
