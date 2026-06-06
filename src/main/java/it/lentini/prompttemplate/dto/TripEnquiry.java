package it.lentini.prompttemplate.dto;

import jakarta.validation.constraints.NotBlank;

public record TripEnquiry(@NotBlank String name, String email, @NotBlank String question) {
}
