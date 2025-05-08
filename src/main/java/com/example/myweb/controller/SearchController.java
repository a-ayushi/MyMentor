package com.example.myweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.myweb.model.WorkerCourse;
import com.example.myweb.service.SearchService;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    //API for search
    @GetMapping("/search")
    public String search(String title, String category, Model model) {

        List<WorkerCourse> filteredCourses;

        if (title != null && !title.isEmpty() && category != null && !category.isEmpty()){

            filteredCourses  = searchService.findByTitleAndCategoryCourses(title, category);
        }
        else if (title != null && !title.isEmpty()) {
            filteredCourses = searchService.findByTitle(title);
        }

        // Agar sirf category diya gaya hai
        else if (category != null && !category.isEmpty()) {
            filteredCourses = searchService.findByCategory(category);
        }

        else {
            filteredCourses = new ArrayList<>();
        }

        model.addAttribute("courses", filteredCourses);
        return "index";
    }

}
