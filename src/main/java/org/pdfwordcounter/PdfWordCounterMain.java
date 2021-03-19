package org.pdfwordcounter;

import org.pdfwordcounter.bot.FileWordCounterBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class PdfWordCounterMain {

    public static void main(String args[]) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new FileWordCounterBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
