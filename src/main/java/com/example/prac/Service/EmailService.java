package com.example.prac.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    public String sendOtp(String email, String otp) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.brevo.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        Map<String, Object> body = new HashMap<>();

        Map<String, String> sender = new HashMap<>();
        sender.put("name", "Quiz App");
        sender.put("email", "iamarnavtyagig@gmail.com"); // Verified sender

        body.put("sender", sender);

        List<Map<String, String>> to = new ArrayList<>();

        Map<String, String> receiver = new HashMap<>();
        receiver.put("email", email);

        to.add(receiver);

        body.put("to", to);
        body.put("subject", "Quiz App OTP Verification");

        body.put(
                "textContent",
                "Hello,\n\nYour OTP is: " + otp
                        + "\n\nThis OTP is valid for 5 minutes.\n\nThank You!"
        );

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, request, String.class);

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());

            return "OTP Sent Successfully";

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }
}