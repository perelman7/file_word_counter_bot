package com.fileworkcounter.wordcounter;

import com.fileworkcounter.wordcounter.bot.FileWordCounterBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class WordcounterApplication {

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new FileWordCounterBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        SpringApplication.run(WordcounterApplication.class, args);
    }

}
