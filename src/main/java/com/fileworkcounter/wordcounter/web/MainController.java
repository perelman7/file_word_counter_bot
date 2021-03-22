package com.fileworkcounter.wordcounter.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@Slf4j
public class MainController {

    @GetMapping
    public ResponseEntity<String> main() {
        return ResponseEntity.ok("Hello from bot");
    }

    @PostMapping("/renew")
    public ResponseEntity<String> test() {
        log.info("Renew controller: {}", LocalDateTime.now().toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
