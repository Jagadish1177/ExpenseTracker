document.addEventListener("DOMContentLoaded", () => {
fetch("http://localhost:8080/api/expenses")
    .then(res => res.json())
    .then(data => {
    populateDashboard(data);
    })
    .catch(err => {
    console.error("Failed to fetch expenses:", err);
    });
});

function populateDashboard(expenses) {
    let total = 0;
    const currentMonth = new Date().getMonth();
    let monthlyTotal = 0;

    const tbody = document.getElementById("recent-expense-table");
    tbody.innerHTML = "";

    // Sort by latest and take top 5
    const recent = expenses
        .sort((a, b) => new Date(b.expenseDate) - new Date(a.expenseDate))
        .slice(0, 5);

    for (const expense of expenses) {
        total += expense.amount;
        const expMonth = new Date(expense.expenseDate).getMonth();
        if (expMonth === currentMonth) monthlyTotal += expense.amount;
    }

    for (const expense of recent) {
        const row = document.createElement("tr");
        row.innerHTML = `
        <td>${expense.expenseDate}</td>
        <td>${expense.categoryDto.name}</td>
        <td>₹${expense.amount}</td>
        `;
        tbody.appendChild(row);
}

document.getElementById("total-expenses").textContent = `₹${total}`;
document.getElementById("monthly-expenses").textContent = `₹${monthlyTotal}`;
}
