package pro.filaretov.telegram.bdgb.consumer;

import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Action representing the game
 */
public class AliensGoHomeGameAction implements Consumer<Update> {

    private static final Logger LOG = LoggerFactory.getLogger(AliensGoHomeGameAction.class);

    public static final String SHORT_NAME = "agh";

    private MessageSender sender;
    private String baseUrl;

    public AliensGoHomeGameAction(MessageSender sender, String baseUrl) {
        this.sender = sender;
        this.baseUrl = baseUrl;
    }

    @Override
    public void accept(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String gameShortName = callbackQuery.getGameShortName();
        User from = callbackQuery.getFrom();

        LOG.debug("Starting game '{}' from '{}' with URL {}", gameShortName, from.getFirstName(), baseUrl);

        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackQuery.getId());
        // TODO - need to encrypt/encode it?
        answer.setUrl(baseUrl + String.format("?user=%s&imid=%s", from.getId(), callbackQuery.getInlineMessageId()));

        try {
            sender.execute(answer);
        } catch (TelegramApiException e) {
            LOG.error("Cannot send answer callback", e);
        }
    }

}
