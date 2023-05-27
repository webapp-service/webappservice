/*// Obtener todos los elementos con la clase "estado"
const status = document.getElementsByClassName('status');

// Iterar sobre los elementos y cambiar el botón según el estado
for (let i = 0; i < status.length; i++) {
  const status = status[i].textContent.trim();

  // Obtener el botón asociado al estado actual
  const btn = status[i].parentNode.nextElementSibling.querySelector('.btn-status');

  // Cambiar el nombre y la clase del botón según el estado
  if (status === 'PENDING') {
    btn.textContent = 'CANCELAR';
    btn.classList.remove('btn-comment');
    btn.classList.add('btn-cancel');
  } else if (status === 'COMPLETED') {
    btn.textContent = 'COMENTAR';
    btn.classList.remove('btn-cancel');
    btn.classList.add('btn-comment');
  }
}
function captureButtonState(element) {
  var contractId = element.getAttribute('data-contract-id');
  var buttonState = element.getAttribute('data-button-state');

  // Aquí puedes hacer lo que necesites con contractId y buttonState,
  // como enviarlos en una petición AJAX o redirigir a otra página.
}


 */


let cantContracts = parseInt(document.getElementById("contracts-size").textContent);
document.getElementById('contracts-size').innerHTML = '';


for (let i = 1; i <= cantContracts; i++) {
  let status = parseInt(document.getElementById("contract-status-"+i).textContent);
  let buttonStyle = document.getElementById("contract-button-style-"+i)
  if (status == 1 || status == 2){
    let contractId= parseInt(document.getElementById("contract-" + i ).textContent);
    let button= document.getElementById("contract-button-"+i)
    button.setAttribute("href",`/user/${contractId}/2`)
    buttonStyle.innerHTML="CANCELAR"
    buttonStyle.classList.add("btn-cancel")
  }else{
    let contractId= parseInt(document.getElementById("contract-" + i ).textContent);
    let button= document.getElementById("contract-button-"+i)
    button.setAttribute("href",`/user/${contractId}/1`)
    buttonStyle.innerHTML="CALIFICAR"
    buttonStyle.classList.add("btn-comment")
  }
  let statusTh=document.getElementById("contract-status-"+i)
  statusTh.remove()
}
