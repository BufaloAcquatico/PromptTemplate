package it.lentini.prompttemplate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.lentini.prompttemplate.dto.TripEnquiry;
import it.lentini.prompttemplate.dto.TripSuggestionResponse;
import it.lentini.prompttemplate.service.EnquiryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnquiryController {

    private final EnquiryService enquiryService;
    private final ObjectMapper objectMapper;

    public EnquiryController(EnquiryService enquiryService, ObjectMapper objectMapper) {
        this.enquiryService = enquiryService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/help")
    public TripSuggestionResponse help(@RequestBody @Valid TripEnquiry tripEnquiry) {

        try {
            String chatbotResponse = enquiryService.processEnquiry(tripEnquiry);
            String json = extractJsonObject(chatbotResponse);

            return objectMapper.readValue(json, TripSuggestionResponse.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Could not parse chatbot response as TripSuggestionResponse", e);
        }
    }

    private String extractJsonObject(String response) {
        int start = response.indexOf('{');
        int end = response.lastIndexOf('}');

        if (start == -1 || end == -1 || start > end) {
            throw new IllegalStateException("Chatbot response did not contain a valid JSON object");
        }

        return response.substring(start, end + 1);
    }
}
