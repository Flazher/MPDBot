package command.control;

import command.BotCommand;
import io.vertx.core.Future;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import telegram.MPDBotContext;
import telegram.MPDTelegramUtils;

public class BotCommandVolumeDown extends BotCommand {

    @Override
    public String getCommand() {
        return MPDTelegramUtils.VOLUME_DOWN;
    }

    @Override
    public Future<Void> execute(MPDBotContext context, SendMessage sendMessage) {
        return context.getMopidyService().getVolume()
                .compose(volume -> context.getMopidyService().setVolume(Math.max(0, volume.getResult() - 20)))
                .compose(result -> {
                    Future<Void> future = Future.future();
                    context.redrawControlPanel(future, sendMessage, "Volume down");
                    return future;
                });
    }

}
