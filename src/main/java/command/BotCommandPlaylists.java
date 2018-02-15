package command;

import io.vertx.core.Future;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import telegram.MPDBotContext;
import telegram.MPDTelegramUtils;

public class BotCommandPlaylists extends BotCommand {

    @Override
    public String getCommand() {
        return "/playlists";
    }

    @Override
    public Future<Void> execute(MPDBotContext context, SendMessage sendMessage) {
        Future<Void> future = Future.future();
        context.getMopidyService().getPlaylists().setHandler(result -> {
            if (result.succeeded() && result.result().ok()) {
                context.setPlaylists(result.result().getResult());
                MPDTelegramUtils.sendListKeyboard(sendMessage, context.getPlaylists(), context.getPlaylistOffset());
                sendMessage.setText("Playlists");
            } else {
                sendMessage.setText("Something's gone wrong");
            }
            future.complete();
        });
        return future;
    }
}
