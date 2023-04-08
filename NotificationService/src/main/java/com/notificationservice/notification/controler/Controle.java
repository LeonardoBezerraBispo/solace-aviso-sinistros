package com.notificationservice.notification.controler;

import java.io.IOException;

import org.json.JSONArray;
import org.springframework.web.bind.annotation.RestController;

import com.notificationservice.notification.view.NotificationService;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class Controle {
    NotificationService notification = new NotificationService();

    @PostMapping(value = "/santander/seguros/v1/send-notification")
    public String message() throws IOException, InterruptedException {
        JSONArray payload = new JSONArray(this.notification.notificationPayload());
            for(int i = 0; payload.length() > i; i++){
                payload.optJSONObject(i).put("status", "aberto");
            }
        return payload.toString();
    }

}
