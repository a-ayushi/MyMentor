document.addEventListener('DOMContentLoaded', async () => {
  const email = localStorage.getItem('email');
  const role = localStorage.getItem('role');
  const fullName = localStorage.getItem('fullName') || 'User';
  const container = document.querySelector('.container1');
  const logout= document.getElementById('logout')
  const welcomeText = document.getElementById('welcome-text');
  welcomeText.textContent = `Welcome, ${fullName}!`;

  const uploadedBtn = document.getElementById('uploaded-course-btn');
  const orderedBtn = document.getElementById('ordered-btn');

if (logout) {
  logout.addEventListener('click', () => {
    localStorage.clear();
  });
}
  // Hide worker buttons if the user is not a worker
  if (role !== 'worker') {
    uploadedBtn.style.display = 'none';
    orderedBtn.style.display = 'none';
  }

//check the role
 const dashboardLink = document.getElementById("dashboardLink");

    dashboardLink.addEventListener("click", function (event) {
      event.preventDefault(); // prevent default anchor behavior

      const role = localStorage.getItem("role");

      if (role === "client") {
        window.location.href = "/main";
      } else if (role === "worker") {
        window.location.href = "/workerdashboard";
      } else {
        alert("Role not recognized. Please log in again.");
        window.location.href = "/login";
      }
    });
  // ===== CLIENT UI =====
  if (role === 'client') {
    if (!document.getElementById('purchased-courses-btn') && !document.getElementById('my-profile-btn')) {
      const btnContainer = document.createElement('div');
      btnContainer.className = 'action-buttons';

      const purchasedBtn = document.createElement('button');
      purchasedBtn.textContent = 'Purchased Courses';
      purchasedBtn.id = 'purchased-courses-btn';

      const profileBtn = document.createElement('button');
      profileBtn.textContent = 'My Profile';
      profileBtn.id = 'my-profile-btn';

      btnContainer.appendChild(purchasedBtn);
      btnContainer.appendChild(profileBtn);
      container.appendChild(btnContainer);
    }

    const purchasedBtn = document.getElementById('purchased-courses-btn');
    const profileBtn = document.getElementById('my-profile-btn');

    // Purchased courses button logic
    purchasedBtn.addEventListener('click', async () => {
      try {
        purchasedBtn.style.display = 'none';
        profileBtn.style.display = 'none';

        const res = await fetch(`/courses/purchased?email=${encodeURIComponent(email)}`);
        const courses = await res.json();

        const existingList = document.getElementById('purchased-courses-list');
        if (existingList) existingList.remove();

        const cardGrid = document.createElement('div');
        cardGrid.className = 'card-grid';
        cardGrid.id = 'purchased-courses-list';

        if (courses.length === 0) {
          const msg = document.createElement('p');
          msg.textContent = 'You have not purchased any courses.';
          container.appendChild(msg);
        } else {
          courses.forEach(course => {
            const card = document.createElement('div');
            card.className = 'course-card';

            card.innerHTML = `
              <img src="images/${course.fileName}" alt="${course.title}" />
              <div class="content">
                <h3>${course.title}</h3>
                <p><strong>Price:</strong> ₹${course.price} ✅</p>
                <p><strong>Mentor:</strong> ${course.email}</p>
                <p><strong>Duration:</strong> ${course.duration}</p>
                <p><strong>Language:</strong> ${course.language}</p>
              </div>
            `;

            cardGrid.appendChild(card);
          });

          container.appendChild(cardGrid);
        }
      } catch (err) {
        console.error('Error loading purchased courses:', err);
        alert('Failed to load purchased courses.');
      }
    });

    // My Profile button logic
    profileBtn.addEventListener('click', () => {
      alert(`Name: ${fullName}\nEmail: ${email}`);
    });
  }

  // ===== WORKER UI =====
  if (role === 'worker') {
    uploadedBtn.addEventListener('click', () => {
      window.location.href = '/workerdashboard';
    });

    orderedBtn.addEventListener('click', async () => {
      try {
        const res = await fetch(`/getAllcourses?email=${encodeURIComponent(email)}`);
        const uploadedCourses = await res.json();

        const existingSection = document.getElementById('worker-orders-section');
        if (existingSection) existingSection.remove();

        const ordersSection = document.createElement('div');
        ordersSection.id = 'worker-orders-section';
        ordersSection.innerHTML = '<h2>Clients Who Purchased Your Courses:</h2>';

        for (const course of uploadedCourses) {
          const clientRes = await fetch(`/course/${course.id}/clients`);
          const clients = await clientRes.json();

          const card = document.createElement('div');
          card.className = 'course-card';

          card.innerHTML = `
            <img src="images/${course.fileName}" alt="${course.title}" />
            <div class="content">
              <h3>${course.title}</h3>
              <p><strong>Price:</strong> ₹${course.price}</p>
              <p><strong>Clients:</strong></p>
                ${clients.length > 0 ? clients.map(client => `<li>${client}</li>`).join('') : '<li>No clients yet</li>'}
            </div>
          `;

          ordersSection.appendChild(card);
        }

        container.appendChild(ordersSection);
      } catch (err) {
        console.error('Error loading clients:', err);
      }
    });
  }
});
