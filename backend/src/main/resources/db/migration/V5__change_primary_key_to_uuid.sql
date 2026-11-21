ALTER TABLE `assignment` DROP FOREIGN KEY `fk_Student`;
ALTER TABLE `assignment` DROP FOREIGN KEY `fk_Mentor`;
ALTER TABLE `assignment` DROP FOREIGN KEY `fk_Committee1`;
ALTER TABLE `assignment` DROP FOREIGN KEY `fk_Committee2`;

ALTER TABLE `submission` DROP FOREIGN KEY `fk_Assignment`;

ALTER TABLE `review` DROP FOREIGN KEY `fk_Submission`;
ALTER TABLE `review` DROP FOREIGN KEY `fk_Reviewer`;


ALTER TABLE `user` MODIFY `user_id` BINARY(16) NOT NULL;

ALTER TABLE `assignment` MODIFY `assignment_id` BINARY(16) NOT NULL;
ALTER TABLE `assignment` MODIFY `student_id` BINARY(16) NOT NULL;
ALTER TABLE `assignment` MODIFY `mentor_id` BINARY(16) NOT NULL;
ALTER TABLE `assignment` MODIFY `committee1_id` BINARY(16);
ALTER TABLE `assignment` MODIFY `committee2_id` BINARY(16);

ALTER TABLE `submission` MODIFY `submission_id` BINARY(16) NOT NULL;
ALTER TABLE `submission` MODIFY `assignment_id` BINARY(16) NOT NULL;

ALTER TABLE `review` MODIFY `review_id` BINARY(16) NOT NULL;
ALTER TABLE `review` MODIFY `submission_id` BINARY(16) NOT NULL;
ALTER TABLE `review` MODIFY `reviewer_id` BINARY(16) NOT NULL ;


ALTER TABLE `assignment` ADD CONSTRAINT fk_Student
	FOREIGN KEY (`student_id`) REFERENCES `user`(`user_id`);

ALTER TABLE `assignment` ADD CONSTRAINT fk_Mentor
	FOREIGN KEY (`mentor_id`) REFERENCES `user`(`user_id`);

ALTER TABLE `assignment` ADD CONSTRAINT fk_Committee1
	FOREIGN KEY (`committee1_id`) REFERENCES `user`(`user_id`);

ALTER TABLE `assignment` ADD CONSTRAINT fk_Committee2
	FOREIGN KEY (`committee2_id`) REFERENCES `user`(`user_id`);

ALTER TABLE `submission` ADD CONSTRAINT fk_Assignment
	FOREIGN KEY (`assignment_id`) REFERENCES `assignment`(`assignment_id`);

ALTER TABLE `review` ADD CONSTRAINT fk_Submission
	FOREIGN KEY (`submission_id`) REFERENCES `submission`(`submission_id`);

ALTER TABLE `review` ADD CONSTRAINT fk_Reviewer
	FOREIGN KEY (`reviewer_id`) REFERENCES `user`(`user_id`);
