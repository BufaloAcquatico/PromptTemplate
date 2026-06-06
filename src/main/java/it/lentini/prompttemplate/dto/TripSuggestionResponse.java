package it.lentini.prompttemplate.dto;

public record TripSuggestionResponse(
        String reply,
        String destination,
        String reply_type
) {
}
