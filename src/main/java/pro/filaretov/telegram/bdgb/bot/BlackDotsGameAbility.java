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
import org.telegram.abilitybots.api.sender.MessageSender;
import pro.filaretov.telegram.bdgb.consumer.AliensGoHomeGameAction;

/**
 * Black Dots Game Ability
 */
@Component
public class BlackDotsGameAbility extends AbilityBot {

    private static final Logger LOG = LoggerFactory.getLogger(BlackDotsGameAbility.class);
    private static final String BOT_USER_NAME = "BlackDotsBot";
    // TODO - update
    public static final String USAGE_MESSAGE = "Play my games";

    // TODO - reply with game to inline query

    @Value("${BLACK_DOTS_GAME_BOT_CREATOR_ID}")
    private int creatorId;

    public BlackDotsGameAbility(@Value("${BLACK_DOTS_GAME_BOT_TOKEN}") String token) {
        super(token, BOT_USER_NAME);
    }

    @Override
    public int creatorId() {
        return creatorId;
    }

    MessageSender getSender() {
        return sender;
    }

    // TODO - manage standard ability commands like /commands, /report, etc

    /**
     * "Aliens, Go Home!" game callback
     */
    public Reply aliensGoHomeCallback() {
        // TODO - we cannot inject AliensGoHomeGameAction as a bean since this method is called from super constructor.
        //  Create my own BaseAbilityBot for a proper bean injection to work?
        String aghBaseUrl = System.getenv("BLACK_DOTS_GAME_BOT_BASE_URL");
        AliensGoHomeGameAction action = new AliensGoHomeGameAction(sender, aghBaseUrl);
        return Reply.of(action, update ->
            Flag.CALLBACK_QUERY.test(update)
                && update.getCallbackQuery().getGameShortName() != null
                && !update.getCallbackQuery().getGameShortName().isEmpty()
                && update.getCallbackQuery().getGameShortName().equals(AliensGoHomeGameAction.SHORT_NAME)
        );
    }

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

}
