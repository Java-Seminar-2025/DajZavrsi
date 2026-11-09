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
@Table(name = "assignment")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mentor_id", nullable = false)
    private User mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "committee1_id")
    private User committee1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "committee2_id")
    private User committee2;

    @Column(name = "subject", nullable = false, columnDefinition = "TEXT")
    private String subject;

    @Column(name = "validation_schema_url")
    private String validationSchemaUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AssignmentStatus status;

    protected Assignment() {
    }

    public Assignment(User student, User mentor, String subject, AssignmentStatus status) {
        this.student = student;
        this.mentor = mentor;
        this.subject = subject;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public User getMentor() {
        return mentor;
    }

    public void setMentor(User mentor) {
        this.mentor = mentor;
    }

    public User getCommittee1() {
        return committee1;
    }

    public void setCommittee1(User committee1) {
        this.committee1 = committee1;
    }

    public User getCommittee2() {
        return committee2;
    }

    public void setCommittee2(User committee2) {
        this.committee2 = committee2;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getValidationSchemaUrl() {
        return validationSchemaUrl;
    }

    public void setValidationSchemaUrl(String validationSchemaUrl) {
        this.validationSchemaUrl = validationSchemaUrl;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }
}
