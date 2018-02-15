package command.control;

import command.BotCommand;
import io.vertx.core.Future;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import telegram.MPDBotContext;
import telegram.MPDTelegramUtils;

public class BotCommandMute extends BotCommand {

    @Override
    public String getCommand() {
        return MPDTelegramUtils.MUTE;
    }

    @Override
    public Future<Void> execute(MPDBotContext context, SendMessage sendMessage) {
        return context.getMopidyService().mute().compose(muted -> {
            Future<Void> future = Future.future();
            context.redrawControlPanel(future, sendMessage, muted ? "Muted" : "Unmuted");
            return future;
        });
    }

}
