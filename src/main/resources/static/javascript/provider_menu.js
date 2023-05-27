
let cantContracts = parseInt(document.getElementById("contracts-size").textContent);
document.getElementById('contracts-size').innerHTML = '';


for (let i = 1; i <= cantContracts; i++) {
    let status = parseInt(document.getElementById("contract-status-" + i).textContent);
    let buttonStyle = document.getElementById("contract-button-style-" + i)
    if (status == 1) {
        let contractId = parseInt(document.getElementById("contract-" + i).textContent);
        let button = document.getElementById("contract-button-" + i)
        button.setAttribute("href", `/provider/${contractId}/1`)
        buttonStyle.innerHTML = "ACEPTAR"
        buttonStyle.classList.add("btn-acept")
    } else if (status == 2) {
        let contractId = parseInt(document.getElementById("contract-" + i).textContent);
        let button = document.getElementById("contract-button-" + i)
        button.setAttribute("href", `/provider/${contractId}/2`)
        buttonStyle.innerHTML = "COMPLETAR TAREA"
        buttonStyle.classList.add("btn-cancel")
    }else {
        let button = document.getElementById("contract-button-" + i)
        button.remove();
    }
    let statusTh = document.getElementById("contract-status-" + i)
    statusTh.remove()
}