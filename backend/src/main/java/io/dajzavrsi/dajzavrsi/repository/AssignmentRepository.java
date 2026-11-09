package io.dajzavrsi.dajzavrsi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import io.dajzavrsi.dajzavrsi.entity.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    List<Assignment> findByStudentId(Integer studentId);

    List<Assignment> findByMentorId(Integer mentorId);

    @Query("select a from Assignment a where a.committee1.id = :userId or a.committee2.id = :userId")
    List<Assignment> findByCommitteeMemberId(@Param("userId") Integer userId);
}
