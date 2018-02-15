package command;

import io.vertx.core.Future;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import telegram.MPDBotContext;
import telegram.MPDTelegramUtils;

public class BotCommandNextPage extends BotCommand {

    @Override
    public String getCommand() {
        return MPDTelegramUtils.NEXT;
    }

    @Override
    public Future<Void> execute(MPDBotContext context, SendMessage sendMessage) {
        if (context.getPlaylistOffset() + 4 <= context.getPlaylists().size() - 1)
            context.setPlaylistOffset(context.getPlaylistOffset()+ 4);

        sendMessage.setText("Next page");
        MPDTelegramUtils.sendListKeyboard(sendMessage, context.getPlaylists(), context.getPlaylistOffset());
        return Future.succeededFuture();
    }

}
