package pro.filaretov.telegram.bdgb.consumer;

import java.util.Collections;
import java.util.UUID;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultGame;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * Action representing the inline query response
 */
public class InlineQueryAction implements Consumer<Update> {

    private static final Logger LOG = LoggerFactory.getLogger(InlineQueryAction.class);

    private MessageSender sender;

    public InlineQueryAction(MessageSender sender) {
        this.sender = sender;
    }

    @Override
    public void accept(Update update) {
        InlineQuery inlineQuery = update.getInlineQuery();

        InlineQueryResultGame aghResult = new InlineQueryResultGame();
        aghResult.setId(UUID.randomUUID().toString());
        aghResult.setGameShortName(AliensGoHomeGameAction.SHORT_NAME);

        AnswerInlineQuery answer = new AnswerInlineQuery();
        answer.setInlineQueryId(inlineQuery.getId());
        answer.setResults(Collections.singletonList(aghResult));

        try {
            sender.execute(answer);
        } catch (TelegramApiRequestException e) {
            LOG.error("Cannot send inline query response. Error code: {}, API response: {}",
                e.getErrorCode(), e.getApiResponse());
        } catch (TelegramApiException e) {
            LOG.error("Cannot send inline query response", e);
        }
    }

}
