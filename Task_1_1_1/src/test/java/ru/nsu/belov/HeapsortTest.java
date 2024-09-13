package ru.nsu.belov;

import static org.junit.jupiter.api.Assertions.*;

class HeapsortTest {
    public void testSort() {
        int[] arr = {-1, 1, 0, 5, -50, 0};
        int[] res = {-50, -1, 0, 0, 1, 5};

        Heapsort.sort(arr);
        assertEquals(res, arr);
    }

    public void testSortPositive() {
        int[] arr = {10, 9, 8, 4, 5, 2};
        int[] res = {2, 4, 5, 8, 9, 10};

        Heapsort.sort(arr);
        assertEquals(res, arr);
    }

    public void testSortNegative() {
        int[] arr = {-1 , -12, -10, -5, -2};
        int[] res = {-12, -10, -5, -2, -1};

        Heapsort.sort(arr);
        assertEquals(res, arr);
    }

    public  void testSortEmpty() {
        int[] arr = {};
        int[] res = {};

        Heapsort.sort(arr);
        assertEquals(res, arr);
    }
}