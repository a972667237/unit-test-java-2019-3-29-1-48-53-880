package tw.core;

import org.junit.Test;
import tw.validator.InputValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 在InputValidatorTest文件中完成InputValidator中对应的单元测试
 */
public class InputValidatorTest {
    private InputValidator valid = new InputValidator();

    @Test
    public void TestValidate() {
        String RepeatNumString = "1 1 2 3";
        String LackNumString = "1 2 3";
        String CorrectNumString = "1 2 3 4";
        String InputErrorString = "A";
        assertFalse(valid.validate(RepeatNumString));
        assertFalse(valid.validate(LackNumString));
        assertFalse(valid.validate(InputErrorString));
        assertTrue(valid.validate(CorrectNumString));
    }

}
