package com.example.myweb.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.myweb.model.WorkerCourse;
import com.example.myweb.repository.WorkerRepo;
import com.example.myweb.service.WorkerService;

@Service
public class WorkerCourseImpl implements WorkerService {

	private static final String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "resources" + File.separator + "static" + File.separator + "images";
	@Autowired
	private WorkerRepo repo;

	@Override
	public String addCourse(WorkerCourse course, MultipartFile file) throws IOException {

		// Create the upload folder if it doesn't exist
		File uploadFolder = new File(UPLOAD_DIR);
		if (!uploadFolder.exists()) {
			uploadFolder.mkdirs();
		}

		if (file != null && !file.isEmpty()) {
			// Get the original file name
			String originalName = file.getOriginalFilename();

			// Generate a unique file name to avoid conflicts
			String uniqueName = System.currentTimeMillis() + "_" + originalName;

			// File path
			String filepath = UPLOAD_DIR + File.separator + uniqueName;

			// Copy and save the file
			Files.copy(file.getInputStream(), Paths.get(filepath));

			// Set the unique file name in the course
			course.setFileName(uniqueName);
		} else {
			// No file provided, set file name as null
			course.setFileName(null);
		}

		// Save the course to the repository
		repo.save(course);

		return "Course uploaded successfully.";
	}

	@Override
	public List<WorkerCourse> getCoursesByEmail(String email) {
		// remove the UnsupportedOperationException line!
		return repo.findByEmail(email.trim().toLowerCase());  // or however you store e-mails
	}


	@Override
	public WorkerCourse updateCourse(int courseId, WorkerCourse updatedCourse) {
		// Check if the course exists
		WorkerCourse existingCourse = repo.findById(courseId)
				.orElseThrow(() -> new IllegalArgumentException("Course with ID " + courseId + " not found"));

		// Update the fields of the existing course with the new values
		existingCourse.setTitle(updatedCourse.getTitle());
		existingCourse.setDescription(updatedCourse.getDescription());
		existingCourse.setCategory(updatedCourse.getCategory());
		existingCourse.setPrice(updatedCourse.getPrice());
		existingCourse.setFileName(updatedCourse.getFileName()); // Optional: Update file name if needed

		// Save the updated course to the repository
		return repo.save(existingCourse);
	}

	@Override
	public void deleteCourse(int courseId) {
		// Check if the course exists
		WorkerCourse course = repo.findById(courseId)
				.orElseThrow(() -> new IllegalArgumentException("Course with ID " + courseId + " not found"));

		// Delete the course
		repo.delete(course);
	}
	public List<WorkerCourse> getTopCourses(int limit) {
		return repo.findTopCourses(PageRequest.of(0, limit));
	}

	@Override
	public List<WorkerCourse> getCoursesByCategory(String category) {
		return repo.findByCategoryIgnoreCase(category);
	}

//	@Override
//	public List<WorkerCourse> getPurchasedCourses(String email) {
//		return repo.findByPurchasedEmailsContaining(email.trim().toLowerCase());
//	}


	@Override
	public void addClientEmailToCourse(int courseId, String clientEmail) {
		WorkerCourse course = repo.findById(courseId)
				.orElseThrow(() -> new RuntimeException("Course not found"));

		List<String> emails = course.getPurchasedByEmails();
			emails.add(clientEmail);
			course.setPurchasedByEmails(emails);
			repo.save(course);
			//debug
			System.out.println("Updated course: " + course.getPurchasedByEmails());

	}
	@Override
	public List<WorkerCourse> getPurchasedCourses(String email) {
		return repo.findCoursesPurchasedByEmail(email);
	}

	@Override
	public List<WorkerCourse> getCoursesPurchasedByEmail(String email) {
		return repo.findCoursesPurchasedByEmail(email);
	}

    @Override
	public List<String> getClientEmailsByCourseId(int courseId) {
		WorkerCourse course = repo.findById(courseId)
				.orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));

		return course.getPurchasedByEmails(); // return the client emails
	}

//	all clients who purchased any course from a particular mentor
	@Override
	public Set<String> getAllClientEmailsForMentor(String mentorEmail) {
		List<WorkerCourse> courses = repo.findCoursesByMentorEmail(mentorEmail);
		Set<String> allClients = new HashSet<>();

		for (WorkerCourse course : courses) {
			allClients.addAll(course.getPurchasedByEmails());
		}

		return allClients;
	}

	@Override
public List<WorkerCourse> findAll() {
    return repo.findAll(); // Use the repository to fetch all WorkerCourse entities
}

    @Override
    public WorkerCourse getCourseById(int id) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + id));}



}
