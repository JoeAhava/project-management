package dev.yohannses.project_management.proposals.internal;

import dev.yohannses.project_management.commons.dto.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tbl_milestones")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MileStone {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String title;
    @Column(columnDefinition = "text")
    String description;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    String detailedDescription;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "payment_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "payment_currency"))
    })
    Payment payment;

    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "proposal_id")
    Proposal proposal;
}
