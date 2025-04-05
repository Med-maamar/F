package tn.esprit.spring.restcontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload) {
        System.out.println("Webhook reçu : " + payload);
        // Ajoutez ici la logique de traitement du payload si besoin
        return ResponseEntity.ok("Webhook traité");
    }
}
