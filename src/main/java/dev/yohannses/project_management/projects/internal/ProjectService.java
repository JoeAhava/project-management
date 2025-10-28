package dev.yohannses.project_management.projects.internal;

import dev.yohannses.project_management.commons.events.ProposalCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectService.class);

    @ApplicationModuleListener
    void proposalCreated(ProposalCreatedEvent event) {
        LOG.info("✅ Project Created for New Proposal with ID: {} and payload: {}", event.id(), event.proposal());
    }
}
