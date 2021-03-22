package com.fileworkcounter.wordcounter.bot;

import com.fileworkcounter.wordcounter.model.FileInfo;
import com.fileworkcounter.wordcounter.parser.FileParser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.URL;

@Slf4j
@Component
public class FileWordCounterBot extends TelegramLongPollingBot {

    @Autowired
    private FileParser fileParser;

    @Value("${telegram.bot.file.url}")
    private String TELEGRAM_URL;

    @Value("${telegram.bot.username}")
    private String BOT_USERNAME;

    @Value("${telegtam.bot.token}")
    private String BOT_TOKEN;

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        User from = message.getFrom();
        if (message.getDocument() != null) {
            Document document = message.getDocument();
            log.info("File name: {} from: {} {} {}", document.getFileName(), from.getFirstName(), from.getUserName(), from.getId());
            GetFile getFileRequest = new GetFile(document.getFileId());
            try {
                File fileTg = this.execute(getFileRequest);
                URL fileUrl = new URL(TELEGRAM_URL + this.getBotToken() + "/" + fileTg.getFilePath());
                FileInfo fileInfo = fileParser.parse(fileUrl, document.getFileName());
                this.execute(new SendMessage(message.getChatId().toString(), this.generateMessage(fileInfo)));
            } catch (Exception e) {
                log.error("Parse file error: {}", e.getMessage());
            }
        } else {
            log.info("Message: {}, from: {} :: {} :: {}", message.getText(), from.getFirstName(), from.getUserName(), from.getId());
            try {
                if (message.getText().equals("/start")) {
                    this.execute(new SendMessage(message.getChatId().toString(), "Добро пожаловать \nДля того чтобы посчитать количество слов и символов в файле формата pdf просто загрузи его!\nУдачного пользования"));
                } else {
                    this.execute(new SendMessage(message.getChatId().toString(), "Я пока не умею общаться :("));
                }
            } catch (TelegramApiException e) {
                log.error("Send message error: {}", e.getMessage());
            }
        }
    }

    private String generateMessage(FileInfo fileInfo) {
        String result = null;
        if (fileInfo != null) {
            result = "Файл: " + fileInfo.getFilename()
                    + "\nСодержит '" + fileInfo.getWordCount() + "' слов и '"
                    + fileInfo.getCharCount() + "' символов.";
        }
        return result;
    }
}
