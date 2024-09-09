package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void sampleTest(){
        assertEquals(3, Main.sum(1, 2));
    }
}