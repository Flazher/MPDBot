import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import telegram.TelegramMPDBot;

/**
 * Created by flazher on 15.02.18.
 */
public class MPDBot extends AbstractVerticle {

    @Override
    public void start(Future startFuture) throws Exception {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramMPDBot());
            startFuture.complete();
        } catch (TelegramApiException e) {
            startFuture.fail(e);
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
