package com.fileworkcounter.wordcounter.web;

import com.fileworkcounter.wordcounter.bot.FileWordCounterBot;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping
    public ResponseEntity<String> main(){
        return ResponseEntity.ok("Hello from bot");
    }

    @GetMapping("/name")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok(new FileWordCounterBot().getBotUsername());
    }
}
