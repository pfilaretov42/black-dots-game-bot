package pro.filaretov.telegram.bdgb.bot;

import java.io.Serializable;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.games.GetGameHighScores;
import org.telegram.telegrambots.meta.api.methods.games.SetGameScore;
import org.telegram.telegrambots.meta.api.objects.games.GameHighScore;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * The manager for game score related stuff.
 */
@Component
public class ScoreManager {

    private static final Logger LOG = LoggerFactory.getLogger(ScoreManager.class);

    // TODO - is it thread safe!?
    private final MessageSender sender;

    public ScoreManager(BlackDotsGameAbility ability) {
        sender = ability.getSender();
    }

    public void setScore(Integer userId, String imId, Integer score) {
        SetGameScore gameScore = new SetGameScore();
        gameScore.setUserId(userId);
        gameScore.setInlineMessageId(imId);
        gameScore.setScore(score);

        try {
            sender.execute(gameScore);
        } catch (TelegramApiException e) {
            LOG.error("Cannot set game score", e);
        }
    }

    // TODO - do we need this?
    public void getHighScore(Integer userId, String imId) {
        GetGameHighScores getGameHighScores = new GetGameHighScores();
        getGameHighScores.setUserId(userId);
        getGameHighScores.setInlineMessageId(imId);

        try {
            Serializable result = sender.execute(getGameHighScores);
            if (result instanceof List) {
                List<GameHighScore> highScores = (List<GameHighScore>) result;
            }
        } catch (TelegramApiException e) {
            LOG.error("Cannot get game score", e);
        }
    }
}
