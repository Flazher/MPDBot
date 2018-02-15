package command.control;

import command.BotCommand;
import io.vertx.core.Future;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import telegram.MPDBotContext;
import telegram.MPDTelegramUtils;

public class BotCommandNextTrack extends BotCommand {

    @Override
    public String getCommand() {
        return MPDTelegramUtils.NEXT_TRACK;
    }

    @Override
    public Future<Void> execute(MPDBotContext context, SendMessage sendMessage) {
        return context.getMopidyService().next().compose(v -> {
            Future<Void> future = Future.future();
            context.redrawControlPanel(future, sendMessage, "Playing the next track");
            return future;
        });
    }

}
