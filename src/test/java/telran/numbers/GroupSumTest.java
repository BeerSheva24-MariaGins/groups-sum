package telran.numbers;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class GroupSumTest {
    private static final long N_GROUPS = 100000;
    private static final long N_NUMBERS_PER_GROUP = 1000;
    int[][] groups = {
            { 1, 2 }, { 3, 4 }, { 5, 6 }
    };
    static int[][] groupsPerformance = Stream.generate(() -> getRandomArray()).limit(N_GROUPS)
            .toArray(int[][]::new);

    @Test
    void treadsPoolGroupSumTest() {
        runFunctionalTest(new ThreadsPoolGroupSum(groups));
    }

    @Test
    void performanceThreadsPoolGroupSumTest() {
        runPerformanceTest(new ThreadsPoolGroupSum(groupsPerformance));
    }

    @Test
    void treadsGroupSumTest() {
        runFunctionalTest(new ThreadsGroupSum(groups));
    }

    @Test
    void performanceThreadsGroupSumTest() {
        runPerformanceTest(new ThreadsGroupSum(groupsPerformance));
    }

    private void runFunctionalTest(GroupSum groupSum) {
        assertEquals(21, groupSum.computeSum());

    }

    private void runPerformanceTest(GroupSum groupSum) {
        groupSum.computeSum();
    }

    static int[] getRandomArray() {
        return new Random().ints(N_NUMBERS_PER_GROUP).toArray();
    }

    @Test
    void comparePerformanceTest() {
        long timeThreadsGroupSum = measurePerformance(new ThreadsGroupSum(groupsPerformance));
        long timeThreadsPoolGroupSum = measurePerformance(new ThreadsPoolGroupSum(groupsPerformance));
        System.out.println("ThreadsGroupSum execution time: " + timeThreadsGroupSum + " ms");
        System.out.println("ThreadsPoolGroupSum execution time: " + timeThreadsPoolGroupSum + " ms");
        assertTrue(timeThreadsPoolGroupSum < timeThreadsGroupSum);
    }

    private long measurePerformance(GroupSum groupSum) {
        long startTime = System.currentTimeMillis();
        groupSum.computeSum();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}