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
ALTER TABLE `review` MODIFY `reviewer_id` BINARY(16) NOT NULL;
