CREATE TABLE `user` (
	`user_id` INTEGER AUTO_INCREMENT PRIMARY KEY,
	`username` VARCHAR(64) NOT NULL UNIQUE,
	`password` BINARY(64) NOT NULL,
	`role` ENUM('student', 'faculty') NOT NULL
);

CREATE TABLE `assignment` (
	`assignment_id` INTEGER AUTO_INCREMENT PRIMARY KEY,
	`student_id` INTEGER NOT NULL,
	`mentor_id` INTEGER NOT NULL,
	`committee1_id` INTEGER,
	`committee2_id` INTEGER,
	`subject` TEXT NOT NULL,
	`validation_schema_url` VARCHAR(255),
	`status` ENUM('in_progress', 'passed', 'failed') NOT NULL,

	CONSTRAINT fk_Student
	FOREIGN KEY (`student_id`) REFERENCES `user`(`user_id`),

	CONSTRAINT fk_Mentor
	FOREIGN KEY (`mentor_id`) REFERENCES `user`(`user_id`),

	CONSTRAINT fk_Committee1
	FOREIGN KEY (`committee1_id`) REFERENCES `user`(`user_id`),

	CONSTRAINT fk_Committee2
	FOREIGN KEY (`committee2_id`) REFERENCES `user`(`user_id`)
);

CREATE TABLE `submission` (
	`submission_id` INTEGER AUTO_INCREMENT PRIMARY KEY,
	`assignment_id` INTEGER NOT NULL,
	`version` INTEGER NOT NULL,
	`document_url` VARCHAR(255) NOT NULL,
	`validation_report_url` VARCHAR(255),
	`review_status` ENUM('pending_validation', 'pending_review', 'approved', 'denied') NOT NULL,

	CONSTRAINT fk_Assignment
	FOREIGN KEY (`assignment_id`) REFERENCES `assignment`(`assignment_id`)
);

CREATE TABLE `review` (
	`review_id` INTEGER AUTO_INCREMENT PRIMARY KEY,
	`submission_id` INTEGER NOT NULL,
	`reviewer_id` INTEGER NOT NULL,
	`status` ENUM('approved', 'denied') NOT NULL,
	`notes` TEXT,

	CONSTRAINT fk_Submission
	FOREIGN KEY (`submission_id`) REFERENCES `submission`(`submission_id`),

	CONSTRAINT fk_Reviewer
	FOREIGN KEY (`reviewer_id`) REFERENCES `user`(`user_id`)
);
