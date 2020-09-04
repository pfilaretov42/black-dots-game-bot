package pro.filaretov.telegram.bdgb.consumer;

import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Action representing the game
 */
public class GameAction implements Consumer<Update> {

    private static final Logger LOG = LoggerFactory.getLogger(GameAction.class);

    private final SilentSender silent;
    private final MessageSender sender;
    private final String baseUrl;

    public GameAction(SilentSender silent, MessageSender sender, String baseUrl) {
        this.silent = silent;
        this.sender = sender;
        this.baseUrl = baseUrl;
    }

    @Override
    public void accept(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String gameShortName = callbackQuery.getGameShortName();
        User from = callbackQuery.getFrom();

        LOG.warn("Starting game {} from {}", gameShortName, from.getFirstName());

        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackQuery.getId());
        answer.setUrl(baseUrl);

        //SetGameScore setGameScore = new SetGameScore();

        try {
            sender.execute(answer);
        } catch (TelegramApiException e) {
            LOG.error("Cannot send answer callback", e);
        }
    }

}
