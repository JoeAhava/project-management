package dev.yohannses.project_management.proposals.internal;

import dev.yohannses.project_management.commons.dto.ProposalDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Controller.API_V1)
@RequiredArgsConstructor
public class Controller {

    public static final String API_V1 = "/api/v1";
    private final ProposalService proposalService;


    @PostMapping(value = "/proposals", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates a new proposal")
    @ResponseStatus(HttpStatus.CREATED)
    public ProposalDto createProposal(@RequestBody @Valid ProposalDto proposalDto) {
        return proposalService.createProposal(proposalDto);

    }
}
