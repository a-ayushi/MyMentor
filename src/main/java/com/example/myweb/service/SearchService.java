package com.example.myweb.service;

import java.util.List;

import com.example.myweb.model.WorkerCourse;

public interface SearchService {

    public List<WorkerCourse> findByTitleAndCategoryCourses(String title,String category);

    public List<WorkerCourse> findByTitle(String title);

    public List<WorkerCourse> findByCategory(String category);


}
