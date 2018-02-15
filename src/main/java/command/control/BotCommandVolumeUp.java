package command.control;

import command.BotCommand;
import io.vertx.core.Future;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import telegram.MPDBotContext;
import telegram.MPDTelegramUtils;

public class BotCommandVolumeUp extends BotCommand {

    @Override
    public String getCommand() {
        return MPDTelegramUtils.VOLUME_UP;
    }

    @Override
    public Future<Void> execute(MPDBotContext context, SendMessage sendMessage) {
        return context.getMopidyService().getVolume()
                .compose(volume -> context.getMopidyService().setVolume(Math.min(100, volume.getResult() + 20)))
                .compose(result -> {
                    Future<Void> future = Future.future();
                    context.redrawControlPanel(future, sendMessage, "Volume up");
                    return future;
                });
    }

}
