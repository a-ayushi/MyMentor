document.addEventListener("DOMContentLoaded", () => {
  if (localStorage.getItem("loggedIn") === "true") {
    const navbar = document.getElementById("navbar");
    if (!navbar) return;

    // Prevent adding the button multiple times
    if (!document.getElementById("my-account-link")) {
      const li = document.createElement("li");
      li.innerHTML = `
        <a href="/my-account" id="my-account-link">
          Account <span class="material-symbols-outlined">account_circle</span>
        </a>
      `;
      navbar.appendChild(li);
    }
  }
});
