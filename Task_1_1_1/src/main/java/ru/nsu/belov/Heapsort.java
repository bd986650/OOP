package ru.nsu.belov;

public class Heapsort {
    public static void sort(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            siftDown(arr, i, 0);
        }
    }

    private static void siftDown(int[] heap, int n, int i) {
        int largest = i;
        
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && heap[l] > heap[largest]) {
            largest = l;
        }

        if (r < n && heap[r] > heap[largest]) {
            largest = r;
        }

        if (largest != i) {
            swap(heap, i, largest);
            siftDown(heap, n, largest);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
