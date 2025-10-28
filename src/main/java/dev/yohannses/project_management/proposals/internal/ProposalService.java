package dev.yohannses.project_management.proposals.internal;

import dev.yohannses.project_management.commons.dto.MilestoneDto;
import dev.yohannses.project_management.commons.dto.ProposalDto;
import dev.yohannses.project_management.commons.events.ProposalCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class ProposalService {

    final Repository repository;
    final ApplicationEventPublisher applicationEventPublisher;

    public ProposalService(Repository repository, ApplicationEventPublisher applicationEventPublisher) {
        this.repository = repository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    ProposalDto createProposal(ProposalDto proposal) {
        Proposal newProposal = Proposal.builder()
                .milestones(
                        proposal.getMilestones().stream().map(milestoneDto ->
                                        MileStone.builder()
                                                .title(milestoneDto.getTitle())
                                                .description(milestoneDto.getDescription())
                                                .detailedDescription(milestoneDto.getDetailedDescription())
                                                .payment(milestoneDto.getPayment())
                                                .build())
                                .toList()
                )
                .detailedDescription(proposal.getDetailedDescription())
                .title(proposal.getTitle())
                .shortDescription(proposal.getDescription())
                .build();

        newProposal.setTotalPayment();

        Proposal savedProposal = repository.save(newProposal);

        ProposalDto eventData = ProposalDto.builder()
                .description(savedProposal.getShortDescription())
                .milestones(savedProposal.getMilestones().stream().map(mileStone ->
                        MilestoneDto.builder()
                                .description(mileStone.getDescription())
                                .payment(mileStone.getPayment())
                                .title(mileStone.getTitle())
                                .detailedDescription(mileStone.getDetailedDescription())
                                .build()
                ).toList())
                .title(savedProposal.getTitle())
                .payment(savedProposal.getTotalPayment())
                .build();


        this.applicationEventPublisher.publishEvent(
                new ProposalCreatedEvent(
                        eventData,
                        savedProposal.getId(),
                        savedProposal.getCreatedAt(), savedProposal.getUpdatedAt()
                )
        );

        return proposal;
    }
}
