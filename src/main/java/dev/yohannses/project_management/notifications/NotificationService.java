package dev.yohannses.project_management.notifications;

import dev.yohannses.project_management.commons.events.ProposalCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final static Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    @ApplicationModuleListener
    void onProposalCreated(ProposalCreatedEvent event) {
        LOG.info("✅ Notification Triggered for New Proposal with ID: {} and payload: {}", event.id(), event.proposal());
    }

}
