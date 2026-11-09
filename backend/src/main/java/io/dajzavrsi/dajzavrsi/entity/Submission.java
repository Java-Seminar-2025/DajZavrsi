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

@Entity
@Table(name = "submission")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "document_url", nullable = false)
    private String documentUrl;

    @Column(name = "validation_report_url")
    private String validationReportUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_status", nullable = false)
    private SubmissionStatus reviewStatus;

    protected Submission() {
    }

    public Submission(Assignment assignment, Integer version, String documentUrl, SubmissionStatus reviewStatus) {
        this.assignment = assignment;
        this.version = version;
        this.documentUrl = documentUrl;
        this.reviewStatus = reviewStatus;
    }

    public Integer getId() {
        return id;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public Integer getVersion() {
        return version;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public String getValidationReportUrl() {
        return validationReportUrl;
    }

    public void setValidationReportUrl(String validationReportUrl) {
        this.validationReportUrl = validationReportUrl;
    }

    public SubmissionStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(SubmissionStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}
