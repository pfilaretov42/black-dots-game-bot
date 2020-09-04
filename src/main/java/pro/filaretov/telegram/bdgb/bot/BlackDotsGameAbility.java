package pro.filaretov.telegram.bdgb.bot;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import pro.filaretov.telegram.bdgb.consumer.GameAction;

/**
 * Black Dots Game Ability
 */
@Component
public class BlackDotsGameAbility extends AbilityBot {

    private static final Logger LOG = LoggerFactory.getLogger(BlackDotsGameAbility.class);
    private static final String BOT_USER_NAME = "BlackDotsBot";
    // TODO - update
    public static final String USAGE_MESSAGE = "Play my games";

    @Value("${BLACK_DOTS_GAME_BOT_CREATOR_ID}")
    private int creatorId;
    @Value("${BLACK_DOTS_GAME_BOT_BASE_URL}")
    private String baseUrl;

    public BlackDotsGameAbility(@Value("${BLACK_DOTS_GAME_BOT_TOKEN}") String token) {
        super(token, BOT_USER_NAME);
    }

    @Override
    public int creatorId() {
        return creatorId;
    }

    // TODO - manage standard ability commands like /commands, /report, etc

    /**
     * Prints usage information on /start command
     */
    public Ability printUsage() {
        return Ability
            .builder()
            .name("start")
            .info("prints usage information")
            .input(0)
            .locality(ALL)
            .privacy(PUBLIC)
            .action(ctx -> silent.send(USAGE_MESSAGE, ctx.chatId()))
            .build();
    }

    /**
     * A game callback
     */
    public Reply gameCallback() {
        GameAction action = new GameAction(silent, sender, baseUrl);
        return Reply.of(action, update ->
            Flag.CALLBACK_QUERY.test(update)
                && update.getCallbackQuery().getGameShortName() != null
                && !update.getCallbackQuery().getGameShortName().isEmpty()
        );
    }

}
