public class BinarySearch {
    public boolean binarySearchRecursive(int[] array, int target, int low, int high) {
        if (low > high) {
            return false; // Base case: target not found
        }

        int mid = low + (high - low) / 2;

        if (array[mid] == target) {
            return true; // Base case: target found
        } else if (array[mid] > target) {
            return binarySearchRecursive(array, target, low, mid - 1); // Search in the left half
        } else {
            return binarySearchRecursive(array, target, mid + 1, high); // Search in the right half
        }
    }
}
