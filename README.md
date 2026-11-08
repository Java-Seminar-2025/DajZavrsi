# Thesis Validation System
A web application for managing and validating final theses.

## About the Project
- Thesis Validation System is a web application designed to simplify the process of submitting, reviewing, and validating final theses.
- The application is intended for students, mentors, and examination committee members.
- Students can upload their final theses in PDF or Word format and check whether their documents meet the formatting and structural requirements defined by their university.
- The system automatically checks the uploaded documents and generates a validation report containing detected errors and suggestions for improvement.
- Each student is assigned a mentor and two examination committee members who can review the submitted thesis.
- The mentor and committee members can approve the thesis or request corrections.
- Students can upload updated versions of their theses based on the feedback they receive.
- Users must create an account and log in to access the application.

## User Roles

### Student
- Register and log in.
- Upload final thesis documents (PDF/DOCX).
- Run automatic thesis validation.
- View validation results and detected errors.
- View feedback from the mentor and committee members.
- Upload corrected versions of the thesis.
- Track the approval status of the thesis.

### Mentor
- Log in and access assigned students.
- Review submitted theses.
- View automatic validation reports.
- Provide feedback and request corrections.
- Approve or reject thesis submissions.

### Examination Committee (2 Professors)
- Log in and access assigned theses.
- Review submitted documents and validation reports.
- Leave comments and suggestions.
- Approve the thesis or request additional corrections.

## Project Specification
- **Backend:** Java + Spring Boot
- **Frontend:** React
- **Database:** MySQL
- **Document Processing:** Apache PDFBox (PDF), Apache POI (Word)
- **Validation:** Custom Java validation rules based on university requirements
- **AI Integration:** OpenAI API / Ollama (local LLM)
- **Communication:** REST API (JSON)
- **Authentication & Authorization:** Spring Security
- **Version Control:** Git + GitHub
- **Containerization:** Docker

## Main Features
- User registration and authentication.
- Role-based access control (Student, Mentor, Committee Member).
- Thesis upload and document management.
- Automatic document formatting and structure validation.
- Generation of validation reports.
- Mentor and committee feedback.
- Thesis revision and resubmission.
- Thesis approval workflow.
- Tracking thesis submission status.

## Thesis Approval Process
1. The student uploads their final thesis.
2. The application automatically validates the document.
3. The student reviews the validation report and corrects any detected errors.
4. The student submits the thesis for review.
5. The mentor reviews the thesis and provides feedback or approval.
6. Two examination committee members review the thesis.
7. If corrections are required, the student uploads a revised version.
8. The thesis is marked as approved once the mentor and both committee members have approved it.

## Database

The database consists of four main tables:
    * User – Stores all registered users, including students, mentors, and committee members. Each user has a role that defines their responsibilities in the application.
    * Assignment – Stores thesis assignments and connects each student with a mentor and two committee members. It also contains the thesis subject, validation schema, and current assignment status.
    * Submission – Stores different versions of a thesis submitted by a student. Each submission is connected to an assignment and contains the document URL, validation report URL, version number, and review status.
    * Review – Stores reviews submitted by mentors and committee members. Each review is connected to a specific submission and reviewer, containing the approval status and optional feedback.
Database Relationships
    * One user can have multiple assignments as a student, mentor, or committee member.
    * One assignment can have multiple submissions.
    * One submission can have multiple reviews.
    * Each review belongs to one submission and one reviewer.

## Building and running
Use docker compose to build and run the application and its required environment.

By default, it will serve a frontend on port 8080.

```bash
docker compose up
```

*Note: Docker commands assume the application has been configured to package and serve the frontend and backend together. Database configuration is also required.*


## Project Maintainers
- Paulo Božulić
- Josip Grgat
- Duje Budiša
