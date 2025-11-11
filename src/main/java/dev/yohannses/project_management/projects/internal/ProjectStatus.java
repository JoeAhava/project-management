package dev.yohannses.project_management.projects.internal;


import lombok.Getter;

@Getter
public enum ProjectStatus {
    ACTIVE("ACTIVE"),
    APPROVED("APPROVED"),
    PENDING_APPROVAL("PENDING_APPROVAL"),
    PENDING_COMPLETION_APPROVAL("PENDING_COMPLETION_APPROVAL"),
    COMPLETION_APPROVED("COMPLETION_APPROVED");


    private final String status;

    ProjectStatus(String status) {
        this.status = status;
    }

}
