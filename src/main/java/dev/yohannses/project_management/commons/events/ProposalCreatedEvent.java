package dev.yohannses.project_management.commons.events;

import dev.yohannses.project_management.commons.Constants;
import dev.yohannses.project_management.commons.dto.ProposalDto;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Externalized(Constants.PROPOSAL_Q)
public record ProposalCreatedEvent(ProposalDto proposal, UUID id, LocalDateTime createdAt, LocalDateTime updatedAt) { }
