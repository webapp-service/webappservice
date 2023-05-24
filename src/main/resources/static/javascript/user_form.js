const file = document.getElementById('image');
const preview = document.getElementById('preview');

file.addEventListener('change', function(e) {
  const selectedFile = e.target.files[0];
  
  if (selectedFile) {
    const reader = new FileReader();
    
    reader.onload = function(event) {
      preview.src = event.target.result;
      preview.style.display = 'block'; // Agrega esta línea para mostrar la imagen en la vista previa
    }
    
    reader.readAsDataURL(selectedFile);
  }
});
