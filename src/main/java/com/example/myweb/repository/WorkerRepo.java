package com.example.myweb.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myweb.model.WorkerCourse;
import org.springframework.data.repository.query.Param;

public interface WorkerRepo extends JpaRepository<WorkerCourse, Integer>{

    public List<WorkerCourse> findByEmail(String toLowerCase);
    @Query("SELECT c FROM WorkerCourse c")
    List<WorkerCourse> findTopCourses(Pageable pageable);

    //courses by category
    List<WorkerCourse> findByCategoryIgnoreCase(String category);


    //   Get all courses uploaded by a specific mentor
    // Custom finder to get all courses uploaded by a specific worker
    @Query("SELECT wc FROM WorkerCourse wc WHERE wc.email = :mentorEmail")
    List<WorkerCourse> findCoursesByMentorEmail(@Param("mentorEmail") String mentorEmail);

//    List<WorkerCourse> findByPurchasedEmailsContaining(String lowerCase);
List<WorkerCourse> findByPurchasedByEmailsContaining(String email);

//also can use this
//    @Query("SELECT wc FROM WorkerCourse wc WHERE :email MEMBER OF wc.purchasedByEmails")
//    List<WorkerCourse> findCoursesByPurchaserEmail(@Param("email") String email);

    @Query("SELECT c FROM WorkerCourse c JOIN c.purchasedByEmails e WHERE e = :email")
    List<WorkerCourse> findCoursesPurchasedByEmail(@Param("email") String email);


}
