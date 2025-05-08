package com.example.myweb.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myweb.model.WorkerCourse;
import com.example.myweb.service.WorkerService;

@Controller
public class WorkerController {

	@Autowired
	private WorkerService service;

	@Value("${file.upload-dir}")
	private String uploadDir;

	@GetMapping("/workerdashboard")
	public String workerDashboard(Model model) {
		if (model.containsAttribute("success")) {
			System.out.println("Flash attribute success: " + model.getAttribute("success"));
		}
		model.addAttribute("pageTitle", "Upload Your Course - My Mentor");
		model.addAttribute("WorkerCourse", new WorkerCourse());
		return "workerdashboard";
	}
	@PostMapping("/addCourse")
	public String createCourse(@RequestParam("file") MultipartFile file,
							   WorkerCourse course,
							   RedirectAttributes attrs) throws IOException {
	
		// Define the directory path
		Path directoryPath = Paths.get(uploadDir);
		
		// Ensure the directory exists
		if (!Files.exists(directoryPath)) {
			Files.createDirectories(directoryPath);
		}
	
		// Save the file to the directory
		String filename = file.getOriginalFilename();
		Path filePath = directoryPath.resolve(filename);
		Files.write(filePath, file.getBytes());
	
		// Set the file path in the course object
		course.setFileName(filename);
	
		// Persist the course
		service.addCourse(course, file);
	
		// Add success message
		attrs.addFlashAttribute("success", "Upload successful ✅");
		return "redirect:/workerdashboard"; // Redirect to the dashboard
	}

	//  API 1: Get all courses by email
	@GetMapping("/getAllcourses")
	@ResponseBody
	public List<WorkerCourse> getCoursesByEmail(@RequestParam String email) {
		String cleanEmail = email.trim().toLowerCase(); // optional toLowerCase
		return service.getCoursesByEmail(cleanEmail);
	}

	//  API 2: Edit course by courseId
	@PutMapping("/course/{id}")
	@ResponseBody
	public WorkerCourse updateCourse(
		@PathVariable int id,
		@RequestPart(value = "course", required = false) WorkerCourse updatedCourse,
		@RequestPart(value = "file", required = false) MultipartFile file
	) throws IOException {
		if (updatedCourse == null) {
			throw new IllegalArgumentException("Course data is required. Please ensure the 'course' part is included in the request.");
		}

		// Retrieve the existing course to retain the current image if no new file is uploaded
		WorkerCourse existingCourse = service.getCourseById(id);
		if (existingCourse == null) {
			throw new IllegalArgumentException("Course not found with ID: " + id);
		}

		if (file != null && !file.isEmpty()) {
			// Save the new file
			String filename = file.getOriginalFilename();
			Path savePath = Paths.get("src/main/resources/static/images/" + filename);
			Files.write(savePath, file.getBytes());
			updatedCourse.setFileName(filename); // Set the new file name
		} else {
			// Retain the existing file name
			updatedCourse.setFileName(existingCourse.getFileName());
		}

		return service.updateCourse(id, updatedCourse);
	}


	//  API 3: Delete course by courseId
	@DeleteMapping("/course/{id}")
	@ResponseBody
	public String deleteCourse(@PathVariable int id) {
		try {
			service.deleteCourse(id);
			return "Course deleted successfully.";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}
	}

	@GetMapping("/featured-courses")
	@ResponseBody
	public List<WorkerCourse> getFeaturedCourses() {
		return service.getTopCourses(10); // return top 10 courses
	}


//render html page with the category embedded
@GetMapping("/category/{category}")
public String getAllCoursesByCategory(@PathVariable String category, Model model) {
	model.addAttribute("category", category);
	return "courses-by-category"; // courses-by-category.html
}

	//will return courses of that category
	// Returns list of courses as JSON for a given category
	@ResponseBody
	@GetMapping("/api/courses/{category}")
	public List<WorkerCourse> getCoursesByCategory(@PathVariable String category) {
		return service.getCoursesByCategory(category); // Returns JSON
	}


	@PutMapping("/courses/{courseId}/add-client")
	public ResponseEntity<String> addClientToCourse(
			@PathVariable int courseId,
			@RequestBody Map<String, String> payload) {

		String clientEmail = payload.get("email");

		if (clientEmail == null || clientEmail.isEmpty()) {
			return ResponseEntity.badRequest().body("Email is required");
		}

		service.addClientEmailToCourse(courseId, clientEmail);
		return ResponseEntity.ok("Client email added to course successfully");
	}


	//get the email for client to check which courses he has purchased
	@GetMapping("/courses/purchased")
	public ResponseEntity<List<WorkerCourse>> getPurchasedCourses(@RequestParam String email) {
		List<WorkerCourse> courses = service.getCoursesPurchasedByEmail(email);
		return ResponseEntity.ok(courses);
	}


	//it get the clients who has purchased a particular course
	@GetMapping("/course/{courseId}/clients")
	@ResponseBody
	public List<String> getClientsForCourse(@PathVariable int courseId) {
		return service.getClientEmailsByCourseId(courseId);
	}

	//all clients who purchased any course from a particular mentor
	@GetMapping("/mentor/{mentorEmail}/clients")
	@ResponseBody
	public Set<String> getClientsForMentor(@PathVariable String mentorEmail) {
		return service.getAllClientEmailsForMentor(mentorEmail);
	}

	 @GetMapping("/total-purchases")
    public ResponseEntity<Map<String, Object>> getTotalPurchases() {
        List<WorkerCourse> courses = service.findAll();

        int totalPurchases = 0;
        double totalAmount = 0.0;

        for (WorkerCourse course : courses) {
            if (course.getPurchasedByEmails() != null) {
                totalPurchases += course.getPurchasedByEmails().size();
                totalAmount += course.getPurchasedByEmails().size() * Double.parseDouble(course.getPrice());
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("totalPurchases", totalPurchases);
        response.put("totalAmount", totalAmount);

        return ResponseEntity.ok(response);
    }
}
