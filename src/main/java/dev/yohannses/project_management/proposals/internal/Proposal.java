package dev.yohannses.project_management.proposals.internal;

import dev.yohannses.project_management.commons.dto.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tbl_proposals")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String title;
    String shortDescription;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    String detailedDescription;

    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    UUID projectId;

    @OneToMany(mappedBy = "proposal", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<MileStone> milestones;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "total_payment_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "total_payment_currency"))
    })
    Payment totalPayment;

    @PreUpdate
    @PrePersist
    public void validateTotalPayment() {
        BigDecimal sum = milestones.stream()
                .map(milestone -> milestone.getPayment().getAmount())

                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (sum.compareTo(totalPayment.getAmount()) != 0) {
            throw new IllegalArgumentException("Total payment does not match the sum of milestone payments.");
        }
        if (!validateCurrency()) {
            throw new IllegalArgumentException("Currency mismatch in milestones.");
        }
    }

    public boolean validateCurrency() {
        if (milestones == null || milestones.isEmpty()) {
            throw new IllegalArgumentException("Milestone list is empty.");
        }
        Currency firstCurrency = milestones.getFirst().getPayment().getCurrency();
        boolean allSame = milestones.stream()
                .allMatch(m -> firstCurrency.equals(m.getPayment().getCurrency()));

        if (!allSame) return false;
        return totalPayment == null || firstCurrency.equals(totalPayment.getCurrency());
    }

    public void setTotalPayment() {
        this.totalPayment = getMilestonesTotal();
    }

    public Payment getMilestonesTotal() {
        validateCurrency();
        return Payment.builder()
                .amount(
                        milestones.stream()
                                .map(milestone -> milestone.getPayment().getAmount())
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                )
                .currency(milestones.getFirst().getPayment().getCurrency())
                .build();
    }
}
