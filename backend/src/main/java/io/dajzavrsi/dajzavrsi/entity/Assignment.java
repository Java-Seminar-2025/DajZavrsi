package io.dajzavrsi.dajzavrsi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "assignment")
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @Getter
    @Setter
    private User student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mentor_id", nullable = false)
    @Getter
    @Setter
    private User mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "committee1_id")
    @Getter
    @Setter
    private User committee1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "committee2_id")
    @Getter
    @Setter
    private User committee2;

    @Column(name = "subject", nullable = false, columnDefinition = "TEXT")
    @Getter
    @Setter
    private String subject;

    @Column(name = "validation_schema_url")
    @Getter
    @Setter
    private String validationSchemaUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Getter
    @Setter
    private AssignmentStatus status;
}
