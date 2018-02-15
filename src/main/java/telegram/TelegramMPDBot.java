package telegram;

import command.*;
import command.control.*;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by flazher on 15.02.18.
 */
public class TelegramMPDBot extends TelegramLongPollingBot {

    private String botName;
    private String botToken;
    private String owner;
    private Boolean hateEveryoneElse;

    private BotCommandsSet commandsSet;
    private MPDBotContext context;

    public TelegramMPDBot(Vertx vertx, JsonObject config) {
        this.botName = config.getString("telegram.bot.name");
        this.botToken = config.getString("telegram.bot.token");
        this.owner = config.getString("telegram.bot.owner");
        this.hateEveryoneElse = config.getBoolean("telegram.bot.hate_everyone_else", true);
        this.context = MPDBotContext.init(vertx, config);
        this.commandsSet = BotCommandsSet.init(context)
                .register(new BotCommandControl())
                .register(new BotCommandPlaylists())
                .register(new BotCommandNextPage())
                .register(new BotCommandPreviousPage())
                .register(new BotCommandPlay())
                .register(new BotCommandPause())
                .register(new BotCommandMute())
                .register(new BotCommandVolumeUp())
                .register(new BotCommandVolumeDown())
                .register(new BotCommandPreviousTrack())
                .register(new BotCommandNextTrack());
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage()
                .enableMarkdown(true)
                .setChatId(message.getChatId());

        if (owner != null && !owner.equals(message.getFrom().getUserName()) && hateEveryoneElse) {
            try {
                sendMessage.setText("Ignoring");
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            Future<Void> future = commandsSet.tryExecute(message.getText(), sendMessage);
            future.setHandler(v -> {
                if (v.succeeded()) {
                } else {
                    sendMessage.setText(message.getText() + " yourself.");
                }
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onClosing() {

    }
}
