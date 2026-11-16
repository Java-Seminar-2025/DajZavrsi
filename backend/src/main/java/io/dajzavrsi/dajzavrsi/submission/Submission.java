package io.dajzavrsi.dajzavrsi.submission;

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

import io.dajzavrsi.dajzavrsi.assignment.Assignment;

@Entity
@Table(name = "submission")
@AllArgsConstructor
@NoArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignment_id", nullable = false)
    @Getter
    @Setter
    private Assignment assignment;

    @Column(name = "version", nullable = false)
    @Getter
    @Setter
    private Integer version;

    @Column(name = "document_url", nullable = false)
    @Getter
    @Setter
    private String documentUrl;

    @Column(name = "validation_report_url")
    @Getter
    @Setter
    private String validationReportUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_status", nullable = false)
    @Getter
    @Setter
    private SubmissionStatus reviewStatus;
}
