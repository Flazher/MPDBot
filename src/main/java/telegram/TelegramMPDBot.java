package telegram;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

/**
 * Created by flazher on 15.02.18.
 */
public class TelegramMPDBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            SendMessage sendMessage = new SendMessage()
                    .enableMarkdown(true)
                    .setChatId(message.getChatId())
                    .setText(message.getText() + " yourself.");


            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton().setText("twenty one pilots: Stressed Out ").setCallbackData("update_msg_text"));

            List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
            rowInline2.add(new InlineKeyboardButton().setText("twenty one pilots: Stressed Out ").setCallbackData("update_msg_text"));
            rowInline2.add(new InlineKeyboardButton().setText(EmojiParser.parseToUnicode(":speaker:")));
            rowInline2.add(new InlineKeyboardButton().setText(EmojiParser.parseToUnicode(":loud_sound:")));
            rowInline2.add(new InlineKeyboardButton().setText(EmojiParser.parseToUnicode(":arrow_forward:")));
            rowInline2.add(new InlineKeyboardButton().setText("\u23F8️"));
            // Set the keyboard to the markup
            rowsInline.add(rowInline);
            rowsInline.add(rowInline2);

            // Add it to the message
            markupInline.setKeyboard(rowsInline);

            sendMessage.setReplyMarkup(markupInline);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            // Set variables
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            if (call_data.equals("update_msg_text")) {
                String answer = "Updated message text";
                EditMessageText new_message = new EditMessageText()
                        .setChatId(chat_id)
                        .setMessageId(toIntExact(message_id))
                        .setText(answer);
                try {
                    execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "FlazherMPDBot";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    public void onClosing() {

    }
}
