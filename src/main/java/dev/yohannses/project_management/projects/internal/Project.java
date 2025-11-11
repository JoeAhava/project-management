package dev.yohannses.project_management.projects.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tbl_projects", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"proposal_id"}, name = "unique_project_proposal_id")
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String title;
    String description;
    String detailedDescription;

    @Enumerated(EnumType.STRING)
    ProjectStatus status;

    UUID proposalId;
}
