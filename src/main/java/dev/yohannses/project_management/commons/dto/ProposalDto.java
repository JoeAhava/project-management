package dev.yohannses.project_management.commons.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProposalDto {
    @NotBlank(message = "Title cannot be blank")
    private String title;
    private String description;
    private String detailedDescription;
    @Size(min = 1, message = "A proposal must have at least one milestone")
    private List<MilestoneDto> milestones;
    private Payment payment;
}
