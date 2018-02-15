package command;

import io.vertx.core.Future;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import telegram.MPDBotContext;

public class BotCommandControl extends BotCommand {

    @Override
    public String getCommand() {
        return "/control";
    }

    @Override
    public Future<Void> execute(MPDBotContext context, SendMessage sendMessage) {
        Future<Void> future = Future.future();
        context.redrawControlPanel(future, sendMessage, "Here you are");
        return future;
    }

}
