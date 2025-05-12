let deleteCallback = null;

document.getElementById('confirmDeleteBtn').addEventListener('click', () => {
  if (deleteCallback) deleteCallback();
  closeDeleteModal();
});

document.getElementById('cancelDeleteBtn').addEventListener('click', closeDeleteModal);

function openDeleteModal(message, onConfirm) {
  document.getElementById('deleteMessage').textContent = message;
  deleteCallback = onConfirm;
  document.getElementById('deleteModal').classList.remove('hidden');
}

function closeDeleteModal() {
  document.getElementById('deleteModal').classList.add('hidden');
  deleteCallback = null;
}
