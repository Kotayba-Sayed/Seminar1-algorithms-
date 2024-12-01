import java.io.*;
import java.util.*;
// Code inspired by freeCodeCamp.org's YouTube tutorial:
// "Data Structures and Algorithms with Visualizations – Full Course (Java)" (https://www.youtube.com/watch?v=2ZLl8GAk1X4)
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuickSort quickSort = new QuickSort();
        InsertionSort insertionSort = new InsertionSort();
        BinarySearch binarySearch = new BinarySearch();

        try {
            // Load random numbers from file
            List<Integer> numbers = loadNumbersFromFile("Seminar 1 - File with random numbers (1).txt");

            // Main menu
            while (true) {
                System.out.println("\n==== Sorting & Searching Menu ====");
                System.out.println("1. Set array size (e.g., 100, 10000, 1000000)");
                System.out.println("2. Choose sorting algorithm (QuickSort (1)/InsertionSort (2))");
                System.out.println("3. RunAll (Run all methods with all inputs)");
                System.out.println("4. BinarySearch (Search for a number)");
                System.out.println("5. Exit");
                System.out.print("Select an option: ");

                int choice = getValidIntegerInput(scanner, "Enter a valid option (1-5): ", 1, 5);

                if (choice == 5) {
                    System.out.println("Exiting...");
                    break;
                }

                // Array size selection
                System.out.print("Enter array size: ");
                int arraySize = getValidIntegerInput(scanner, "Enter a valid array size (e.g., 100, 1000): ", 1, Integer.MAX_VALUE);

                // Initialize array with random numbers
                int[] array = new int[arraySize];
                for (int i = 0; i < arraySize; i++) {
                    array[i] = numbers.get(i % numbers.size());
                }

                if (choice == 3) { // RunAll Option
                    System.out.print("How many times do you want to repeat the process? ");
                    int repetitions = getValidIntegerInput(scanner, "Enter a valid number of repetitions: ", 1, Integer.MAX_VALUE);

                    runAll(array, quickSort, insertionSort, repetitions);

                } else if (choice == 4) { // Binary Search Option
                    System.out.print("Enter the number to search for: ");
                    int target = getValidIntegerInput(scanner, "Enter a valid number: ", Integer.MIN_VALUE, Integer.MAX_VALUE);

                    // Sort the array before binary search
                    Arrays.sort(array);

                    System.out.print("How many times do you want to repeat the binary search? ");
                    int repetitions = getValidIntegerInput(scanner, "Enter a valid number of repetitions: ", 1, Integer.MAX_VALUE);

                    measureBinarySearchPerformance(binarySearch, array, target, repetitions);

                } else { // Single sorting algorithm
                    System.out.print("Choose sorting algorithm (QuickSort (1)/InsertionSort (2)): ");
                    int algorithmChoice = getValidIntegerInput(scanner, "Enter 1 for QuickSort or 2 for InsertionSort: ", 1, 2);

                    if (algorithmChoice == 1) {
                        // QuickSort Options
                        System.out.println("\n==== QuickSort Options ====");
                        System.out.println("1. Median-of-Three Pivot");
                        System.out.println("2. Random Pivot");
                        System.out.println("3. First-Element Pivot");
                        System.out.print("Choose QuickSort pivot strategy: ");
                        int pivotChoice = getValidIntegerInput(scanner, "Enter a valid pivot strategy (1-3): ", 1, 3);

                        String pivotStrategy = switch (pivotChoice) {
                            case 1 -> "median-of-three";
                            case 2 -> "random";
                            case 3 -> "first-element";
                            default -> "";
                        };

                        System.out.print("Choose sorting method (Iterative (1)/Recursive (2)): ");
                        int sortingMethodChoice = getValidIntegerInput(scanner, "Enter 1 for Iterative or 2 for Recursive: ", 1, 2);

                        long startTime = System.nanoTime();
                        if (sortingMethodChoice == 2) { // Recursive
                            quickSort.quickSortRecursive(array.clone(), 0, array.length - 1, pivotStrategy);
                        } else { // Iterative
                            quickSort.quickSortIterative(array.clone(), pivotStrategy);
                        }
                        long endTime = System.nanoTime();

                        // Display results
                        System.out.println("Sorting completed in " + (endTime - startTime) / 1e6 + " ms");

                    } else if (algorithmChoice == 2) {
                        // InsertionSort Options
                        System.out.print("Choose sorting method (Iterative (1)/Recursive (2)): ");
                        int sortingMethodChoice = getValidIntegerInput(scanner, "Enter 1 for Iterative or 2 for Recursive: ", 1, 2);

                        long startTime = System.nanoTime();
                        if (sortingMethodChoice == 2) { // Recursive
                            insertionSort.insertionSortRecursive(array.clone(), array.length);
                        } else { // Iterative
                            insertionSort.insertionSortIterative(array.clone());
                        }
                        long endTime = System.nanoTime();

                        // Display results
                        System.out.println("Sorting completed in " + (endTime - startTime) / 1e6 + " ms");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static void measureBinarySearchPerformance(BinarySearch binarySearch, int[] array, int target, int repetitions) {
        long totalTime = 0;
        boolean found = false;

        for (int i = 0; i < repetitions; i++) {
            long startTime = System.nanoTime();
            found = binarySearch.binarySearchRecursive(array, target, 0, array.length - 1);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }

        double averageTime = totalTime / (double) repetitions;

        // Report whether the number was found
        if (found) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        // Report average runtime
        System.out.printf("BinarySearch for target %d: Average Time = %.2f ns (over %d repetitions)%n", target, averageTime, repetitions);
    }

    private static void runAll(int[] array, QuickSort quickSort, InsertionSort insertionSort, int repetitions) {
        String[] pivotStrategies = {"median-of-three", "random", "first-element"};
        String[] sortingMethods = {"Iterative", "Recursive"};

        System.out.println("\n==== Running All Sorting Methods ====");

        for (String pivotStrategy : pivotStrategies) {
            for (String method : sortingMethods) {
                long totalTime = 0;
                for (int i = 0; i < repetitions; i++) {
                    int[] arrayCopy = array.clone();
                    long startTime = System.nanoTime();
                    if (method.equals("Recursive")) {
                        quickSort.quickSortRecursive(arrayCopy, 0, arrayCopy.length - 1, pivotStrategy);
                    } else {
                        quickSort.quickSortIterative(arrayCopy, pivotStrategy);
                    }
                    long endTime = System.nanoTime();
                    totalTime += (endTime - startTime);
                }
                System.out.printf("QuickSort (%s, %s): Average Time = %.2f ms%n",
                        pivotStrategy, method, (totalTime / (double) repetitions) / 1e6);
            }
        }

        for (String method : sortingMethods) {
            long totalTime = 0;
            for (int i = 0; i < repetitions; i++) {
                int[] arrayCopy = array.clone();
                long startTime = System.nanoTime();
                if (method.equals("Recursive")) {
                    insertionSort.insertionSortRecursive(arrayCopy, arrayCopy.length);
                } else {
                    insertionSort.insertionSortIterative(arrayCopy);
                }
                long endTime = System.nanoTime();
                totalTime += (endTime - startTime);
            }
            System.out.printf("InsertionSort (%s): Average Time = %.2f ms%n",
                    method, (totalTime / (double) repetitions) / 1e6);
        }
    }

    // method to load numbers from the file
    private static List<Integer> loadNumbersFromFile(String fileName) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            for (String num : line.split("\\s+")) {
                numbers.add(Integer.parseInt(num));
            }
        }
        reader.close();
        return numbers;
    }

    // method to validate integer input within a range
    private static int getValidIntegerInput(Scanner scanner, String errorMessage, int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.print(errorMessage);
            } catch (InputMismatchException e) {
                System.out.print(errorMessage);
                scanner.next(); // Clear invalid input
            }
        }
    }
}
