let puntacion = parseInt(document.getElementById("star-valor").textContent);

for (let i = 1; i <= 5; i++) {

    if (i<=puntacion){

        var miDiv = document.getElementById("div-stars");
        var icono = document.createElement("i");
        icono.className = "fa fa-star";
        miDiv.appendChild(icono);
    }else{

        var miDiv = document.getElementById("div-stars");
        var icono = document.createElement("i");
        icono.className = "fa-regular fa-star";
        miDiv.appendChild(icono);
    }
    console.log(i);

}



