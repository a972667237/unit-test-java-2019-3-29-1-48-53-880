package tw.core.generator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;
import tw.GuessNumberModule;
import tw.core.Answer;
import tw.core.exception.OutOfRangeAnswerException;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 在AnswerGeneratorTest文件中完成AnswerGenerator中对应的单元测试
 */
public class AnswerGeneratorTest {
    @Inject
    private AnswerGenerator answerGenerator;

    @Before
    public void setUp() {
        Guice.createInjector(new GuessNumberModule())
                .injectMembers(this);
    }

    @Test
    public void generateTest() throws OutOfRangeAnswerException {
        assertDoesNotThrow(() -> answerGenerator.generate());
        assertTrue(answerGenerator.generate() instanceof Answer);
    }
}

