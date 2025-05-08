document.addEventListener("DOMContentLoaded", () => {


    /* ----------  DOM references used by the modal ---------- */
    const modal           = document.getElementById("purchase-modal");
    const modalTitle      = document.getElementById("modal-title");
    const modalPrice      = document.getElementById("modal-price");
    const confirmBtn      = document.getElementById("confirm-purchase");
    const cancelBtn       = document.getElementById("cancel-purchase");
    // const modalCloseBtn   = document.getElementById("modal-close");
  
    /* ----------  General page initialisation ---------- */
    const category   = document.body.getAttribute("data-category");
    const container  = document.getElementById("courses-card");
  
    const currentUser      = localStorage.getItem("email");
    const userStorageKey   = `purchasedCourses_${currentUser}`;
    let   purchasedCourses = JSON.parse(localStorage.getItem(userStorageKey) || "[]");
  
    /* ----------  Helper to open / close the modal ---------- */
    let coursePending = null;     // remembers which course is being confirmed
  
    function openModal(course){
      coursePending = course;                 // save for later
      modalTitle.textContent = course.title;
      modalPrice.textContent = `Amount payable: ₹${course.price}`;
      modal.hidden = false;
    }
    function closeModal(){
      modal.hidden = true;
      coursePending = null;
    }
    // modalCloseBtn.addEventListener("click", closeModal);
    cancelBtn   .addEventListener("click", closeModal);
  
    /* ----------  Confirm purchase → call your PUT API ---------- */
    confirmBtn.addEventListener("click", () => {
      if(!coursePending) return;               // safety
      const {id, title} = coursePending;
      const clientEmail = localStorage.getItem("email");
  
      fetch(`/courses/${id}/add-client`, {
        method : "PUT",
        headers: {"Content-Type":"application/json"},
        body   : JSON.stringify({ email: clientEmail })
      })
      .then(res => {
        if(!res.ok) throw new Error("Failed to update course");
        return res.text();
      })
      .then(() => {
//        alert("Course purchased!");
  
        purchasedCourses.push(title);
        localStorage.setItem(userStorageKey, JSON.stringify(purchasedCourses));
  
        // reflect the change in the UI
        const buyBtn      = document.querySelector(`.buy-btn[data-id="${id}"]`);
        const whatsappBtn = buyBtn.closest(".course-card").querySelector(".whatsapp-btn");
        buyBtn.textContent = "Purchased";
        buyBtn.disabled = true;
        whatsappBtn.style.display = "inline-block";
  
        closeModal();
      })
      .catch(err => {
        console.error("Error updating course:", err);
        alert("Purchase failed. Please try again later.");
        closeModal();
      });
    });
  
    /* ----------  Fetch & render courses ---------- */
    fetch(`/api/courses/${category}`)
      .then(res => res.json())
      .then(courses => {
        if(!courses.length){
          container.innerHTML = "<p>No courses found.</p>";
          return;
        }
  
        courses.forEach(course => {
          const card = document.createElement("div");
          card.classList.add("course-card");
  
          card.innerHTML = `
            <div class="course-image-wrapper">
              <img src="/images/${course.fileName}" alt="${course.title}" class="course-image" />
            </div>
            <div class="course-content">
              <h3 class="course-title">${course.title}</h3>
              <p class="course-description">${course.description}</p>
              <p><strong>Content:</strong> ${course.content}</p>
              <p><strong>Duration:</strong> ${course.duration} hours</p>
              <p><strong>Language:</strong> ${course.language}</p>
              <p><strong>Mentor:</strong> ${course.email}</p>
              <p><strong>Phone:</strong> ${course.phoneNumber}</p>
              <p class="course-price">₹${course.price}</p>
            </div>
            <div class="actions">
              <button class="btn buy-btn" data-id="${course.id}">Buy</button>
              <button class="btn whatsapp-btn" data-phone="${course.phoneNumber}">Start Video Call</button>
            </div>
          `;
          container.appendChild(card);
  
          const buyBtn      = card.querySelector(".buy-btn");
          const whatsappBtn = card.querySelector(".whatsapp-btn");
  
          // initial state
          if (purchasedCourses.includes(course.title)) {
            buyBtn.textContent = "Purchased";
            buyBtn.disabled = true;
            whatsappBtn.style.display = "inline-block";
          } else {
            whatsappBtn.style.display = "none";
          }
  
          /* ---- Buy button now just opens modal ---- */
          buyBtn.addEventListener("click", () => openModal(course));
  
          /* ---- WhatsApp button logic ---- */
          whatsappBtn.addEventListener("click", () => {
            const confirmStart = confirm(
              "Once you start a video call with the mentor, this course will be marked as used. To schedule another call, you will need to purchase the course again. Do you want to continue?"
            );
            if (!confirmStart) return;
  
            const rawPhone = course.phoneNumber.replace(/\D/g, "");
            const phone    = rawPhone.startsWith("91") ? rawPhone : `91${rawPhone}`;
            const message  = encodeURIComponent("Hi, I want to start a video call regarding your course.");
            const url      = `https://wa.me/${phone}?text=${message}`;
  
            window.open(url, "_blank");
  
            // Revert purchase
            purchasedCourses = purchasedCourses.filter(t => t !== course.title);
            localStorage.setItem(userStorageKey, JSON.stringify(purchasedCourses));
  
            buyBtn.textContent = "Buy";
            buyBtn.disabled = false;
            whatsappBtn.style.display = "none";
          });
        });
      })
      .catch(err => {
        console.error("Failed to fetch courses:", err);
        container.innerHTML = "<p>Error loading courses. Please try again later.</p>";
      });
  });
  