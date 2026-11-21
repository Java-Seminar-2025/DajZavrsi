package io.dajzavrsi.dajzavrsi.assignment;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
    List<Assignment> findByStudentId(UUID studentId);

    List<Assignment> findByMentorId(UUID mentorId);

    @Query("select a from Assignment a where a.committee1.id = :userId or a.committee2.id = :userId")
    List<Assignment> findByCommitteeMemberId(@Param("userId") UUID userId);
}
