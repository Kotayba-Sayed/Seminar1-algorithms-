import java.io.*;
import java.util.*;

public class InsertionSort {
    public static void main(String[] args) {
        // Read random numbers from file
        String filePath = "Seminar 1 - File with random numbers (1).txt";
        List<Integer> numbers = readNumbersFromFile(filePath);

        if (numbers != null) {
            int[] array = numbers.stream().mapToInt(i -> i).toArray();

            // Print the original array
            System.out.println("Original Array: " + Arrays.toString(array));

            // Perform Iterative Insertion Sort
            int[] iterativeArray = array.clone();
            insertionSortIterative(iterativeArray);
            System.out.println("Sorted Array (Iterative): " + Arrays.toString(iterativeArray));

            // Perform Recursive Insertion Sort
            int[] recursiveArray = array.clone();
            insertionSortRecursive(recursiveArray, recursiveArray.length);
            System.out.println("Sorted Array (Recursive): " + Arrays.toString(recursiveArray));
        } else {
            System.out.println("Failed to read numbers from file.");
        }
    }

    // Iterative Insertion Sort
    public static void insertionSortIterative(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            // Move elements of array[0..i-1] that are greater than key
            // to one position ahead of their current position
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    // Recursive Insertion Sort
    public static void insertionSortRecursive(int[] array, int n) {
        // Base case: If the array size is 1 or 0, it's already sorted
        if (n <= 1) {
            return;
        }

        // Sort the first n-1 elements
        insertionSortRecursive(array, n - 1);

        // Insert the nth element in the correct position
        int key = array[n - 1];
        int j = n - 2;

        while (j >= 0 && array[j] > key) {
            array[j + 1] = array[j];
            j--;
        }
        array[j + 1] = key;
    }

    // Read numbers from the file
    public static List<Integer> readNumbersFromFile(String filePath) {
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                for (String numStr : line.split("\\s+")) {
                    try {
                        numbers.add(Integer.parseInt(numStr));
                    } catch (NumberFormatException e) {
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
        return numbers;
    }
}
