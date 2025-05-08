document.addEventListener("DOMContentLoaded", () => {
    let courses = [];

    // Fetch featured courses
    fetch("/featured-courses")
        .then(response => response.json())
        .then(data => {
            courses = data; // Store courses for search functionality
            const container = document.getElementById("courses-scroller");
            if (!container) return;

            if (data.length === 0) {
                container.innerHTML = "<p>No featured courses found.</p>";
                return;
            }

            renderCourses(data, container);
        })
        .catch(error => console.error("Error loading featured courses:", error));

    // Search functionality
    const searchInput = document.querySelector(".searchTerm");
    const container = document.getElementById("courses-scroller");

    searchInput.addEventListener("input", () => {
        const query = searchInput.value.toLowerCase();
        const filteredCourses = courses.filter(course =>
            course.title.toLowerCase().includes(query) ||
            course.description.toLowerCase().includes(query)
        );

        if (!container) return;

        if (filteredCourses.length === 0) {
            container.innerHTML = "<p>No courses match your search.</p>";
        } else {
            renderCourses(filteredCourses, container);
        }
    });

    // Function to render courses
    function renderCourses(courses, container) {
        container.innerHTML = ""; // Clear existing content
        courses.forEach(course => {
            const div = document.createElement("div");
            div.classList.add("course-card");
            div.innerHTML = `
                <div class="course-image-wrapper">
                    <img class="course-thumb" src="images/${course.fileName}" alt="${course.title}" />
                </div>
                <div class="course-content">
                    <h3>${course.title}</h3>
                    <p>${course.description}</p>
                    <p><strong>Price:</strong> ₹${Number(course.price).toFixed(2)}</p>
                    ${course.language ? `<p><strong>Language:</strong> ${course.language}</p>` : ''}
                </div>
            `;

            //click event for login
            div.addEventListener("click",()=>{
            const isLoggedIn=localStorage.getItem("email")!==null;
            console.log("User logged in?",isLoggedIn);
            if(!isLoggedIn){
             window.location.href="/login";
            }
            });
            container.appendChild(div);
        });
    }
});
