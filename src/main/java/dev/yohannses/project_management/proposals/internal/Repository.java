package dev.yohannses.project_management.proposals.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface Repository extends JpaRepository<Proposal, UUID> {
}
