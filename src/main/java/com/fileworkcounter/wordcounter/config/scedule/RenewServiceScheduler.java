package com.fileworkcounter.wordcounter.config.scedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component
@Slf4j
public class RenewServiceScheduler {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${base.url}")
    private String BASE_URL;

    @Scheduled(cron = "0 */10 6-22 * * *")
    public void renew() {
        log.info("Renew : {}", LocalDateTime.now().toString());
        restTemplate.exchange(BASE_URL + "/renew", HttpMethod.POST, HttpEntity.EMPTY, String.class);
    }
}
