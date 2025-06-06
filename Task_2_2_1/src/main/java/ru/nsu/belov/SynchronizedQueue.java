package ru.nsu.belov;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Class to implement a synchronized queue.
 *
 * @param <T> type of parameter
 */
public class SynchronizedQueue<T> {
    private final Queue<T> queue = new ArrayDeque<>();
    private final Integer maxLen;

    /**
     * Constructor.
     *
     * @param maxLen maximum allowed queue length
     */
    public SynchronizedQueue(Integer maxLen) {
        this.maxLen = maxLen;
    }

    /**
     * Adding an element to a synchronized queue.
     *
     * @param elem the element to add
     */
    public synchronized void add(T elem) throws InterruptedException {
        while (queue.size() == maxLen) {
            wait();
        }
        queue.add(elem);
        notifyAll();
    }

    /**
     * Get an element from a synchronized queue.
     *
     * @return the received element
     */
    public synchronized T get() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T elem = queue.poll();
        notifyAll();
        return elem;
    }

    /**
     * Getting the size of a synchronized queue.
     *
     * @return the size of a synchronized queue
     */
    public synchronized int size() {
        return queue.size();
    }
}