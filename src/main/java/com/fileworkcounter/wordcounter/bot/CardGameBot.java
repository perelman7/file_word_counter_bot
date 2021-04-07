package com.fileworkcounter.wordcounter.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendGame;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
@Slf4j
public class CardGameBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.cardgame.url}")
    private String GAME_URL;

    @Value("${telegram.bot.cardgame.username}")
    private String BOT_USERNAME;

    @Value("${telegtam.bot.cardgame.token}")
    private String BOT_TOKEN;

    @Value("${telegram.bot.cardgame.short.name}")
    private String SHORT_GAME_NAME;

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.getMessage() != null && update.getMessage().getText().equals("/start")) {
                User sender = update.getMessage().getFrom();
                log.info("Bot was started by: {} {} {} {}", sender.getId(), sender.getUserName(), sender.getFirstName(), sender.getLastName());
                execute(new SendGame(update.getMessage().getChatId().toString(), SHORT_GAME_NAME));
            } else if (update.getCallbackQuery() != null) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                User sender = callbackQuery.getFrom();
                log.info("Request for game from: {} {} {} {}", sender.getId(), sender.getUserName(), sender.getFirstName(), sender.getLastName());
                AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(callbackQuery.getId());
                answerCallbackQuery.setUrl(GAME_URL);
                sendApiMethod(answerCallbackQuery);
            } else {
                User sender = update.getMessage().getFrom();
                log.info("Unsupported message was send by: {} {} {} {}", sender.getId(), sender.getUserName(), sender.getFirstName(), sender.getLastName());
                execute(new SendMessage(update.getMessage().getChatId().toString(), "Я пока не умею общаться :("));
                log.info("Общение закончено...");
            }
        } catch (Exception e) {
            log.error("Game bot get error: {}", e.getMessage());
        }
    }
}
