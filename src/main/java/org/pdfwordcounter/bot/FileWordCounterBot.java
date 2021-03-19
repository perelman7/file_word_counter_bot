package org.pdfwordcounter.bot;

import org.pdfwordcounter.model.FileInfo;
import org.pdfwordcounter.parser.FileParser;
import org.pdfwordcounter.parser.PdfFileParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.net.URL;

public class FileWordCounterBot extends TelegramLongPollingBot {

    private static final String TELEGRAM_URL = "https://api.telegram.org/file/bot";

    private FileParser fileParser = new PdfFileParser();

    @Override
    public String getBotUsername() {
        return "pdf_word_counter_bot";
    }

    @Override
    public String getBotToken() {
        return "1664006800:AAH9CpezIeI64nE73jQYSJq5swrLBmkjQLE";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message.getDocument() != null) {
            Document document = message.getDocument();
            GetFile request = new GetFile(document.getFileId());
            try {
                File execute = this.execute(request);
                URL url = new URL(TELEGRAM_URL + this.getBotToken() + "/" + execute.getFilePath());
                FileInfo parse = fileParser.parse(url, document.getFileName());
                this.execute(new SendMessage(message.getChatId().toString(), this.generateMessage(parse)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(message.getText());
            try {
                this.execute(new SendMessage(message.getChatId().toString(), "Добро пожаловать \nДля того чтобы посчитать количество слов и символов в файле формата pdf просто загрузи его!\nУдачного пользования"));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateMessage(FileInfo fileInfo){
        String result = null;
        if(fileInfo != null){
            result = "Файл: " + fileInfo.getFilename()
                    + "\nСодержит \'" + fileInfo.getWordCount() + "\' слов и \'"
                    + fileInfo.getCharCount() + "\' символов.";
        }
        return result;
    }
}
