package com.example.myweb.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.example.myweb.model.WorkerCourse;
public interface WorkerService {

    public String addCourse(WorkerCourse course , MultipartFile file) throws IOException;

    public List<WorkerCourse> getCoursesByEmail(String email);

    public WorkerCourse updateCourse(int courseId, WorkerCourse updatedCourse);

    public void deleteCourse(int courseId);

    public List<WorkerCourse> getTopCourses(int limit) ;

    List<WorkerCourse> getCoursesByCategory(String category);

    void addClientEmailToCourse(int courseId, String clientEmail);
    List<WorkerCourse> getPurchasedCourses(String email);

    List<WorkerCourse> getCoursesPurchasedByEmail(String email);

    //get the emails of users purchased a particular course
    List<String> getClientEmailsByCourseId(int courseId);

//    all clients who purchased any course from a particular mentor
    public Set<String> getAllClientEmailsForMentor(String mentorEmail);

    public List<WorkerCourse> findAll();


}
