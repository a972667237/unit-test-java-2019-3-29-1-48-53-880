package tw.core;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;
import tw.GuessNumberModule;
import tw.core.generator.RandomIntGenerator;
import tw.core.model.GuessResult;

import javax.inject.Inject;

import static junit.framework.TestCase.assertTrue;
import static tw.core.GameStatus.CONTINUE;
import static tw.core.GameStatus.SUCCESS;

/**
 * 在GameTest文件中完成Game中对应的单元测试
 */


// TODO: using Jukito?
public class GameTest {
    String gameRandomString = new RandomIntGenerator().generateNums(9, 4);

    @Inject
    private Game game;

    @Before
    public void setUp() {
        Guice.createInjector(new GuessNumberModule(),
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(RandomIntGenerator.class).toInstance(new RandomIntGenerator(){
                            @Override
                            public String generateNums(Integer digitmax, Integer numbersOfNeed) {
                                return gameRandomString;
                            }
                        });
                    }
                })
                .injectMembers(this);
    }

    @Test
    public void TestGuess() {
        // test guess result append into tail
        GuessResult append_result = game.guess(Answer.createAnswer(gameRandomString));
        assertTrue(append_result.equals(game.guessHistory().get(0)));
        assertTrue(append_result instanceof GuessResult);
    }

    @Test
    public void TestCheckStatus() {
        StringBuffer gameRandomStringBuffer = new StringBuffer(gameRandomString);
        char firstNum = gameRandomStringBuffer.charAt(0);
        char secondNum = gameRandomStringBuffer.charAt(2);
        String wrongString = gameRandomString.replace(secondNum, firstNum).replace(firstNum, secondNum);
        game.guess(Answer.createAnswer(wrongString));
        assertTrue(game.checkStatus() == CONTINUE);
        game.guess(Answer.createAnswer(gameRandomString));
        assertTrue(game.checkStatus() == SUCCESS);
    }

}
