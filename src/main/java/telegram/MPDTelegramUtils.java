package telegram;

import com.vdurmont.emoji.EmojiParser;
import mopidy.model.MPDTrack;
import mopidy.model.TelegramListItem;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MPDTelegramUtils {

    public static final String PREV = "< Prev";
    public static final String NEXT = "Next >";

    // Emojis
    public static final String MUTE = "\uD83D\uDD07";
    public static final String PLAY = "▶";
    public static final String PAUSE = "\u23F8";
    public static final String PREV_TRACK = "⏮";
    public static final String NEXT_TRACK = "⏭";
    public static final String VOLUME_DOWN = "\uD83D\uDD08";
    public static final String VOLUME_UP = "\uD83D\uDD0A";

    static public KeyboardRow PREV_NEXT_KEYBOARD_ROW = new KeyboardRow();

    static {
        PREV_NEXT_KEYBOARD_ROW.add(PREV);
        PREV_NEXT_KEYBOARD_ROW.add(NEXT);
    }

    public static <T extends TelegramListItem> void sendListKeyboard(SendMessage sendMessage, List<T> list, Integer offset) {
        ReplyKeyboardMarkup playlistKeyboard = new ReplyKeyboardMarkup();

        List<KeyboardRow> listKeyboardRows = new ArrayList<KeyboardRow>() {{
            add(new KeyboardRow());
            add(new KeyboardRow());
            add(new KeyboardRow());
            add(new KeyboardRow());
            add(MPDTelegramUtils.PREV_NEXT_KEYBOARD_ROW);
        }};

        KeyboardRow row;
        for (int i = 0; i < 4; i++) {
            row = listKeyboardRows.get(i);
            if (list.size() > offset + i)
                row.add(list.get(offset + i).getTitle());
        }

        playlistKeyboard.setKeyboard(listKeyboardRows).setOneTimeKeyboard(true);
        sendMessage.setReplyMarkup(playlistKeyboard);
    }

    public static void sendControlPanel(MPDTrack np, Integer volume, SendMessage sendMessage) {
        ReplyKeyboardMarkup menuKeyboard = new ReplyKeyboardMarkup();

        List<KeyboardRow> menuKeyboardRows = new ArrayList<KeyboardRow>() {{
            KeyboardRow nowPlayingRow = new KeyboardRow();
            nowPlayingRow.add(np.getName());

            KeyboardRow artistRow = new KeyboardRow();
            artistRow.add(np.getSingleArtist().getName());

            KeyboardRow volumePlayingRow = new KeyboardRow();
            volumePlayingRow.add("Volume: " + volume + "%");

            KeyboardRow controlsRow = new KeyboardRow();
            controlsRow.add(MUTE);
            controlsRow.add(VOLUME_DOWN);
            controlsRow.add(VOLUME_UP);
            controlsRow.add(EmojiParser.parseToUnicode(PLAY));
            controlsRow.add(EmojiParser.parseToUnicode(PAUSE));

            controlsRow.add(PREV_TRACK);
            controlsRow.add(NEXT_TRACK);

            add(artistRow);
            add(nowPlayingRow);
            add(volumePlayingRow);
            add(controlsRow);

        }};

        menuKeyboard.setKeyboard(menuKeyboardRows).setOneTimeKeyboard(true);
        sendMessage.setReplyMarkup(menuKeyboard);
    }
}
