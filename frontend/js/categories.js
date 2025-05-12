const categoriesEndpoint = 'http://localhost:8080/api/categories';

document.addEventListener("DOMContentLoaded", () => {
  fetchCategories();

  document.getElementById("category-form").addEventListener("submit", (e) => {
    e.preventDefault();
    const nameInput = document.getElementById("category-name");
    const name = nameInput.value.trim();
    if (name) {
      addCategory(name);
      nameInput.value = "";
    }
  });
});

function fetchCategories() {
  fetch(categoriesEndpoint)
    .then(res => res.json())
    .then(categories => populateCategoryTable(categories))
    .catch(err => console.error("Failed to fetch categories:", err));
}

function populateCategoryTable(categories) {
  const tbody = document.getElementById("category-table-body");
  tbody.innerHTML = "";
  for (const category of categories) {
    const row = document.createElement("tr");
    row.setAttribute("data-id", category.id);
    row.innerHTML = `
      <td>${category.id}</td>
      <td class="category-name">${category.name}</td>
      <td class="actions">
        <button class="table-btn" onclick="editCategory(${category.id})">Edit</button>
        <button class="table-btn" onclick="deleteCategory(${category.id})">Delete</button>
      </td>
    `;
    tbody.appendChild(row);
  }
}


function addCategory(name) {
  fetch(categoriesEndpoint, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name })
  })
    .then(() => fetchCategories())
    .catch(err => console.error("Failed to add category:", err));
}

function editCategory(id) {
  const row = document.querySelector(`tr[data-id='${id}']`);
  const nameCell = row.querySelector(".category-name");
  const currentName = nameCell.textContent;

  nameCell.innerHTML = `<input type="text" value="${currentName}" class="edit-input" />`;

  const actionsCell = row.querySelector(".actions");
  actionsCell.innerHTML = `
    <button class="table-btn" onclick="saveCategory(${id})">Save</button>
    <button class="table-btn" onclick="fetchCategories()">Cancel</button>
  `;
}

function saveCategory(id) {
  const row = document.querySelector(`tr[data-id='${id}']`);
  const newName = row.querySelector(".edit-input").value.trim();

  if (!newName) return alert("Category name cannot be empty");

  fetch(`${categoriesEndpoint}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name: newName })
  })
    .then(() => fetchCategories())
    .catch(err => console.error("Failed to update category:", err));
}

function deleteCategory(id) {
  openDeleteModal("Are you sure you want to delete this category?", () => {
    fetch(`${categoriesEndpoint}/${id}`, {
      method: "DELETE"
    })
      .then(() => fetchCategories())
      .catch(err => console.error("Failed to delete category:", err));
  });
}
