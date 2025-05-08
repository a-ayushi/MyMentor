package com.example.myweb.impl;

import java.util.List;

import com.example.myweb.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myweb.model.WorkerCourse;
import com.example.myweb.repository.WorkerRepo;
import com.example.myweb.service.SearchService;
import com.example.myweb.repository.SearchRepository;


@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private SearchRepository repo;

    @Override
    public List<WorkerCourse> findByTitleAndCategoryCourses(String title, String category) {
        List<WorkerCourse> byTitleAndCategory = repo.findByTitleAndCategory(title, category);

        return byTitleAndCategory;

    }

    @Override
    public List<WorkerCourse> findByTitle(String title) {
        List<WorkerCourse> byTitle = repo.findByTitle(title);
        return byTitle;
    }

    @Override
    public List<WorkerCourse> findByCategory(String category) {
        List<WorkerCourse> byCategory = repo.findByCategory(category);

        return byCategory;
    }


}
