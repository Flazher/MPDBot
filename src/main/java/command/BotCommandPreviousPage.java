package command;

import io.vertx.core.Future;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import telegram.MPDBotContext;
import telegram.MPDTelegramUtils;

public class BotCommandPreviousPage extends BotCommand {

    @Override
    public String getCommand() {
        return MPDTelegramUtils.PREV;
    }

    @Override
    public Future<Void> execute(MPDBotContext context, SendMessage sendMessage) {
        if (context.getPlaylistOffset() < 4)
           context.setPlaylistOffset(0);
        else
            context.setPlaylistOffset(context.getPlaylistOffset() - 4);
        sendMessage.setText("Previous page");
        MPDTelegramUtils.sendListKeyboard(sendMessage, context.getPlaylists(), context.getPlaylistOffset());
        return Future.succeededFuture();
    }

}
