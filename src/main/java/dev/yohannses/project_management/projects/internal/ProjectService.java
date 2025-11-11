package dev.yohannses.project_management.projects.internal;

import dev.yohannses.project_management.commons.events.ProposalCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectService.class);
    private final ProjectRepository repo;

    public ProjectService(ProjectRepository repo) {
        this.repo = repo;
    }

    @ApplicationModuleListener
    void proposalCreated(ProposalCreatedEvent event) {
        Optional<Project> optionalProject = repo.findByProposalId(event.id());
        optionalProject.ifPresent(project -> LOG.warn("⛔ Project exists for proposal with ID: {} and payload: {}", event.id(), project));

        LOG.info("🔃 Project not found creating");

        Project newProject = Project.builder()
                .title(event.proposal().getTitle())
                .description(event.proposal().getDescription())
                .detailedDescription(event.proposal().getDetailedDescription())
                .proposalId(event.id())
                .status(ProjectStatus.ACTIVE)
                .build();

        Project createdProject = repo.save(newProject);

        LOG.info("✅ Project Created for New Proposal with ID: {} and payload: {}", event.id(), createdProject);
    }
}
