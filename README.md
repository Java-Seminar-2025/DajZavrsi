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
1. Roles
Stores the different roles users can have within the application.
Attribute	Description
RoleID (PK)	Unique identifier for each role.
RoleName	Name of the role, such as Student, Professor, or Admin.

2. Users
Stores information about all registered users, including students, professors, and administrators.
Attribute	Description
UserID (PK)	Unique identifier for each user.
RoleID (FK)	References the user's role in the Roles table.
FirstName	User's first name.
LastName	User's last name.
Email	User's email address, which must be unique.
PasswordHash	Stores the user's hashed password instead of the original password.

3. StudyPrograms
Stores information about available study programs. Different programs can have different thesis formatting and validation requirements.
Attribute	Description
ProgramID (PK)	Unique identifier for each study program.
ProgramName	Name of the study program, such as Computer Science or Economics.
FacultyName	Name of the faculty offering the program.

4. Theses
The main table of the database. It stores information about each thesis, its topic, student, and current status.
Attribute	Description
ThesisID (PK)	Unique identifier for each thesis.
StudentID (FK)	References the student who owns the thesis in the Users table.
ProgramID (FK)	References the study program associated with the thesis.
Title	Title of the thesis.
TopicDescription	Description of the proposed thesis topic.
TopicSource	Indicates whether the topic was proposed by the student or professor.
TopicStatus	Current approval status of the proposed topic.
ThesisStatus	Current progress status of the thesis.
CreatedAt	Date and time when the thesis was created.

Example: A student proposes a topic (TopicStatus = Pending). After approval, the thesis can move to ThesisStatus = InProgress.
5. Assignments
Connects professors to theses and defines their responsibilities.
Attribute	Description
AssignmentID (PK)	Unique identifier for each professor assignment.
ThesisID (FK)	References the thesis to which the professor is assigned.
ProfessorID (FK)	References the assigned professor in the Users table.
AssignmentRole	Defines whether the professor is a Mentor or Committee member.
AssignedAt	Date and time when the professor was assigned.

A thesis can have one mentor and multiple committee members. The one-mentor restriction is enforced through application logic.
6. ThesisVersions
Stores the different versions of thesis documents uploaded by students.
Attribute	Description
VersionID (PK)	Unique identifier for each submitted version.
ThesisID (FK)	References the thesis to which the version belongs.
VersionNumber	Version number, such as 1, 2, or 3.
FileName	Name of the uploaded document.
FilePath	Location where the document is stored.
UploadedAt	Date and time when the version was uploaded.

This allows students to submit updated versions of their thesis without losing previous submissions.
7. Reviews
Stores evaluations and feedback provided by mentors and committee members.
Attribute	Description
ReviewID (PK)	Unique identifier for each review.
AssignmentID (FK)	Identifies the professor assignment responsible for the review.
VersionID (FK)	References the thesis version being reviewed. Can be empty for topic reviews.
ReviewStage	Defines whether the review concerns the topic (Topic) or document (Document).
Decision	Review decision, such as Approved or ChangesRequested.
Comments	Feedback or comments provided by the reviewer.
ReviewedAt	Date and time when the review was completed.

Important: This table supports both topic approval and document reviews, avoiding the need for separate review tables.
8. ValidationRules
Defines the rules used to automatically validate thesis documents. Rules can vary depending on the study program.
Attribute	Description
RuleID (PK)	Unique identifier for each validation rule.
ProgramID (FK)	References the study program to which the rule applies.
RuleCode	Unique code identifying the rule within a study program.
RuleName	Name of the validation rule.
RuleCategory	Category of the rule, such as Structure or Formatting.
ExpectedValue	Expected value or condition required to pass the rule.
RuleDescription	Detailed explanation of the validation requirement.
IsActive	Indicates whether the rule is currently enabled.

For example, Computer Science and Economics students may have different formatting requirements.
9. ValidationResults
Stores the results of automated validation checks performed on submitted thesis versions.
Attribute	Description
ResultID (PK)	Unique identifier for each validation result.
VersionID (FK)	References the thesis version being validated.
RuleID (FK)	References the validation rule being checked.
ResultStatus	Validation result, such as PASS or FAIL.
Details	Additional information about the validation result.
PageNumber	Page number where an issue was detected, if applicable.
CheckedAt	Date and time when the validation was performed.

For example, the system may check whether the document contains a conclusion. If it is missing, the result is stored as FAIL, with an explanation in Details.

## Building
Requires Docker with the buildx CLI plugin.

Once the Docker configuration is implemented, build the application using:

```bash
docker buildx build . -t thesis-validation
```

## Running
After building the Docker image, run:

```bash
docker run --rm -p 8080:8080 thesis-validation
```

The application will be accessible at:

`http://localhost:8080`

*Note: Docker commands assume the application has been configured to package and serve the frontend and backend together. Database configuration is also required.*


## Project Maintainers
- Paulo Božulić
- Josip Grgat
- Duje Budiša
