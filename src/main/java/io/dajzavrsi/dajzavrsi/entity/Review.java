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
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission submission;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReviewStatus status;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    protected Review() {
    }

    public Review(Submission submission, User reviewer, ReviewStatus status, String notes) {
        this.submission = submission;
        this.reviewer = reviewer;
        this.status = status;
        this.notes = notes;
    }

    public Integer getId() {
        return id;
    }

    public Submission getSubmission() {
        return submission;
    }

    public User getReviewer() {
        return reviewer;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
