const expensesEndpoint = 'http://localhost:8080/api/expenses';
const categoriesEndpoint = 'http://localhost:8080/api/categories';

document.addEventListener('DOMContentLoaded', () => {
  loadCategories();
  loadExpenses();

  document.getElementById('expense-form').addEventListener('submit', addExpense);
});

function loadCategories() {
  fetch(categoriesEndpoint)
    .then(res => res.json())
    .then(categories => {
      const select = document.getElementById('categorySelect');
      categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.id;
        option.textContent = category.name;
        select.appendChild(option);
      });
    })
    .catch(err => console.error('Failed to load categories', err));
}

function loadExpenses() {
  fetch(expensesEndpoint)
    .then(res => res.json())
    .then(expenses => {
      const tbody = document.getElementById('expensesTableBody');
      tbody.innerHTML = ''; // Clear previous data
      expenses.forEach(expense => {
        const row = document.createElement('tr');
        row.setAttribute('data-id', expense.id); // Store the expense id in the row
        row.innerHTML = `
          <td class="amount">${expense.amount}</td>
          <td class="expenseDate">${expense.expenseDate}</td>
          <td class="category">${expense.categoryDto.name}</td>
          <td>
            <button class="table-btn" onclick="editExpense(${expense.id})">Edit</button>
            <button class="table-btn" onclick="deleteExpense(${expense.id})">Delete</button>
          </td>
        `;
        tbody.appendChild(row);
      });
    })
    .catch(err => console.error('Failed to load expenses', err));
}

function addExpense(event) {
  event.preventDefault();
  const amount = document.getElementById('amount').value;
  const expenseDate = document.getElementById('expenseDate').value;
  const categoryId = document.getElementById('categorySelect').value;

  fetch(expensesEndpoint, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      amount,
      expenseDate,
      categoryDto: {
        id: categoryId
      }
    })
  })
    .then(res => {
      if (!res.ok) throw new Error('Failed to add expense');
      return res.json();
    })
    .then(() => {
      document.getElementById('expense-form').reset();
      loadExpenses();
    })
    .catch(err => console.error('Error adding expense:', err));
}

function editExpense(expenseId) {
  const row = document.querySelector(`tr[data-id='${expenseId}']`);
  const amount = row.querySelector('.amount').textContent;
  const expenseDate = row.querySelector('.expenseDate').textContent;
  const category = row.querySelector('.category').textContent;

  // Replace the row content with input fields for editing
  row.innerHTML = `
    <td><input type="number" class="edit-amount" value="${amount}" /></td>
    <td><input type="date" class="edit-expenseDate" value="${expenseDate}" /></td>
    <td><select class="edit-category"></select></td>
    <td>
      <button class="table-btn" onclick="saveExpense(${expenseId})">Save</button>
      <button class="table-btn" onclick="loadExpenses()">Cancel</button>
    </td>
  `;

  // Populate the category dropdown
  fetch(categoriesEndpoint)
    .then(res => res.json())
    .then(categories => {
      const select = row.querySelector('.edit-category');
      categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.id;
        option.textContent = category.name;
        select.appendChild(option);
      });
      // Set the current category
      const selectedOption = Array.from(select.options).find(option => option.textContent === category);
      if (selectedOption) {
        select.value = selectedOption.value;
      }

    })
    .catch(err => console.error('Failed to load categories for edit', err));
}

function saveExpense(expenseId) {
  const row = document.querySelector(`tr[data-id='${expenseId}']`);
  const amount = row.querySelector('.edit-amount').value;
  const expenseDate = row.querySelector('.edit-expenseDate').value;
  const categoryId = row.querySelector('.edit-category').value;

  fetch(`${expensesEndpoint}/${expenseId}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      amount,
      expenseDate,
      categoryDto: { id: categoryId }
    })
  })
    .then(res => {
      if (!res.ok) throw new Error('Failed to update expense');
      return res.json();
    })
    .then(() => {
      loadExpenses(); // Reload the table after updating
    })
    .catch(err => console.error('Error saving expense:', err));
}

function deleteExpense(id) {
  openDeleteModal("Are you sure you want to delete this expense?", () => {
    fetch(`${expensesEndpoint}/${id}`, {
      method: "DELETE"
    })
      .then(() => loadExpenses())
      .catch(err => console.error('Error deleting expense:', err));
  });
}

