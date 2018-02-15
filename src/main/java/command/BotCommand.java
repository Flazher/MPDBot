package command;

import io.vertx.core.Future;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import telegram.MPDBotContext;

public abstract class BotCommand {

    protected MPDBotContext context;

    public abstract String getCommand();

    public abstract Future<Void> execute(MPDBotContext context, SendMessage sendMessage);

    public void setContext(MPDBotContext context) {
        this.context = context;
    }

}
