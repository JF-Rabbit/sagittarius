package org.sagittarius.common;

/**
 * @author JasonZhang 2018 - 04 - 03 - 17:40
 */
public class StopWatch implements AutoCloseable {
    private final long startTime = System.currentTimeMillis();

    @Override
    public void close() {
        System.out.println("cost: " + (System.currentTimeMillis() - startTime) + "millis");
    }

    public static void main(String[] args) {
        try (StopWatch stopWatch = new StopWatch()) {
            // do something
        }
    }
}
