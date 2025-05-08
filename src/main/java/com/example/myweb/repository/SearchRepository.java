package com.example.myweb.repository;

import com.example.myweb.model.WorkerCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchRepository extends JpaRepository<WorkerCourse,Integer> {
    @Query("FROM WorkerCourse wc WHERE wc.title LIKE %:title% AND wc.category LIKE %:category%")
    public List<WorkerCourse> findByTitleAndCategory(@Param("title") String title, @Param("category") String category);

    @Query("FROM WorkerCourse wc WHERE wc.title LIKE %:title%")
    public List<WorkerCourse> findByTitle(@Param("title") String title);


    @Query("FROM WorkerCourse wc WHERE wc.category LIKE %:category%")
    public List<WorkerCourse> findByCategory(@Param("category") String category);


}
