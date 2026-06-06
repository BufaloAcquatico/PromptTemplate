package it.lentini.prompttemplate.dto;

import jakarta.validation.constraints.NotBlank;

public record TripEnquiry(@NotBlank(message = "Name cannot be blank") String name, @NotBlank(message = "Question cannot be blank") String question) {
}
