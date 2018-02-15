package command;

import io.vertx.core.Future;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import telegram.MPDBotContext;
import telegram.exception.MPDTelegramUnknownCommandException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BotCommandsSet {

    private MPDBotContext context;
    private Map<String, BotCommand> commandsMap;

    public static BotCommandsSet init(MPDBotContext context) {
        return new BotCommandsSet(context);
    }

    private BotCommandsSet(MPDBotContext context) {
        this.commandsMap = new ConcurrentHashMap<>();
        this.context = context;
    }

    public <T extends BotCommand> BotCommandsSet register(T command) {
        command.setContext(context);
        commandsMap.put(command.getCommand(), command);
        return this;
    }

    public Future<Void> tryExecute(String cmd, SendMessage sendMessage) {
        BotCommand command = commandsMap.get(cmd);
            return (command != null)
                    ? command.execute(context, sendMessage)
                    : Future.failedFuture(new MPDTelegramUnknownCommandException());
    }
}
