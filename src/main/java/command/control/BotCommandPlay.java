package command.control;

import command.BotCommand;
import io.vertx.core.Future;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import telegram.MPDBotContext;
import telegram.MPDTelegramUtils;

public class BotCommandPlay extends BotCommand {

    @Override
    public String getCommand() {
        return MPDTelegramUtils.PLAY;
    }

    @Override
    public Future<Void> execute(MPDBotContext context, SendMessage sendMessage) {
        return context.getMopidyService().play().compose(v -> {
            Future<Void> future = Future.future();
            context.redrawControlPanel(future, sendMessage, "Playing");
            return future;
        });
    }

}
