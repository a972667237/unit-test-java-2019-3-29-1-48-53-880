package tw.core;

import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;
import tw.GuessNumberModule;
import tw.core.exception.OutOfRangeAnswerException;
import tw.core.generator.RandomIntGenerator;
import tw.core.model.Record;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.*;


/**
 * 在AnswerTest文件中完成Answer中对应的单元测试
 */
public class AnswerTest {
    @Inject
    private RandomIntGenerator randomIntGenerator;

    @Before
    public void setUp() {
        // for test
        Guice.createInjector(new GuessNumberModule())
                .injectMembers(this);
    }

    @Test
    public void testCreateAnswer(){
        String RandomNumStr = this.randomIntGenerator.generateNums(9, 4);
        Answer answer = Answer.createAnswer(RandomNumStr);
        assertTrue(answer instanceof Answer,
                "result after create must instance of Class Answer");
    }

    @Test
    public void testValid() {
        String RepeatStr = "1 1 1 2";
        String ExceedingSingleDigits = "10 1 2 3";
        String Pass = this.randomIntGenerator.generateNums(9, 4);

        Answer repeatAnswer = Answer.createAnswer(RepeatStr);
        Answer exceedingSingleDigitsAnswer = Answer.createAnswer(ExceedingSingleDigits);
        Answer passAnswer = Answer.createAnswer(Pass);

        assertThrows(OutOfRangeAnswerException.class, () -> repeatAnswer.validate());
        assertThrows(OutOfRangeAnswerException.class, () -> exceedingSingleDigitsAnswer.validate());
        assertDoesNotThrow(() -> passAnswer.validate());
    }

    @Test
    public void testGetIndexOfNum() {
        String RandomStr = this.randomIntGenerator.generateNums(9, 4);
        Answer randomAnswer = Answer.createAnswer(RandomStr);
        String NotExistNum = "10";

        List<String> inputList = Arrays.stream(RandomStr.split(" ")).collect(Collectors.toList());
        assertAll("getIndexOfNum",
                () -> assertEquals(randomAnswer.getIndexOfNum(inputList.get(0)), 0),
                () -> assertEquals(randomAnswer.getIndexOfNum(inputList.get(1)), 1),
                () -> assertEquals(randomAnswer.getIndexOfNum(inputList.get(2)), 2),
                () -> assertEquals(randomAnswer.getIndexOfNum(inputList.get(3)), 3),
                () -> assertEquals(randomAnswer.getIndexOfNum(NotExistNum), -1)
        );
    }

    @Test
    public void testCheck() {
        String RandomStr = this.randomIntGenerator.generateNums(9, 4);
        Answer randomAnswer = Answer.createAnswer(RandomStr);
        List<String> inputList = Arrays.stream(RandomStr.split(" ")).collect(Collectors.toList());

        List<String> singalDigitList = range(0, 10)
                .boxed()
                .map(digit -> String.valueOf(digit))
                .collect(Collectors.toList());


        singalDigitList.removeAll(inputList);

        String TrueAnswer = RandomStr;
        String AllPositionWrong = new StringBuffer(RandomStr)
                .reverse()
                .toString();
        String HaltTrueAndHaltWrong = String.join(" ", Arrays.asList(inputList.get(0),
                inputList.get(1), singalDigitList.get(0), singalDigitList.get(1)));

        Record trueRecord = randomAnswer.check(Answer.createAnswer(TrueAnswer));
        Record allPositionWrongRecord = randomAnswer.check(Answer.createAnswer(AllPositionWrong));
        Record haltTrueAndHaltWrongRecord = randomAnswer.check(Answer.createAnswer(HaltTrueAndHaltWrong));

        assertTrue(trueRecord.getValue().equals("4A0B"));
        assertTrue(haltTrueAndHaltWrongRecord.getValue().equals("2A0B"));
        assertTrue(allPositionWrongRecord.getValue().equals("0A4B"));

    }


}