package dev.yohannses.project_management.commons.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MilestoneDto {
    @NotBlank(message = "Title cannot be blank")
    private String title;
    private String description;
    private String detailedDescription;
    @NotNull(message = "Price cannot be null")
    private Payment payment;

}
