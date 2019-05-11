package tw.core;


import org.junit.Test;
import tw.core.generator.RandomIntGenerator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 在RandomIntGeneratorTest文件中完成RandomIntGenerator中对应的单元测试
 */
public class RandomIntGeneratorTest {
    private RandomIntGenerator randomIntGenerator = new RandomIntGenerator();

    @Test
    public void TestGenerateNums() {
        assertThrows(IllegalArgumentException.class, () -> randomIntGenerator.generateNums(4, 8));
        int numbersOfNeed = 4;
        int digitmax = 9;
        String generateResult = randomIntGenerator.generateNums(digitmax, numbersOfNeed);
        List<String> generateResultList = Arrays.stream(generateResult.split(" ")).collect(Collectors.toList());

        assertEquals(numbersOfNeed, generateResultList.size());
        generateResultList.forEach(item -> {
            assertTrue(Integer.parseInt(item) <= digitmax);
        });
        Set<String> generateResultSet = new HashSet<>(generateResultList);
        assertEquals(numbersOfNeed, generateResultSet.size());

    }

}