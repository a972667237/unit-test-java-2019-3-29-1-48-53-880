package tw.controllers;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;
import tw.GuessNumberModule;
import tw.commands.InputCommand;
import tw.core.Answer;
import tw.core.exception.OutOfRangeAnswerException;
import tw.core.generator.AnswerGenerator;

import javax.inject.Inject;
import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * 在GameControllerTest文件中完成GameController中对应的单元测试
 */
public class GameControllerTest {
    @Inject
    public GameController gameController;
    @Inject
    public AnswerGenerator answerGenerator;

    @Before
    public void setUp() {
        Guice.createInjector(new GuessNumberModule(),
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(GameController.class).toInstance(mock(GameController.class));
                    }
                })
                .injectMembers(this);
    }

    @Test
    public void TestBeginGame() throws IOException {
        gameController.beginGame();
        verify(gameController).beginGame();
    }

    @Test
    public void TestPlay() throws IOException {
        gameController.play(new InputCommand() {
            private String DefaultAnswer = "1 2 3 4";
            @Override
            public Answer input() throws IOException {
                try {
                    return answerGenerator.generate();
                } catch (OutOfRangeAnswerException e) {
                    e.printStackTrace();
                }
                return Answer.createAnswer(DefaultAnswer);
            }
        });
        verify(gameController, atLeastOnce()).play(any(InputCommand.class));

    }
}