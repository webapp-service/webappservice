
let cantProvider = parseInt(document.getElementById("providers-list").textContent);

for (let i = 1; i <= cantProvider; i++) {

    let puntacion = parseInt(document.getElementById("star-valor-"+i).textContent);

    if (puntacion==0){
        let miDiv = document.getElementById("div-stars-"+i);
        let h3 = document.createElement("h3");
        let texto = document.createTextNode("Aun no a sido contratado!");
        texto.className="puntacion-h3";
        h3.appendChild(texto);
        miDiv.appendChild(h3);
    }else {

        for (let j = 1; j <= 5; j++) {

            if (j <= puntacion) {

                let miDiv = document.getElementById("div-stars-" + i);
                let icono = document.createElement("i");
                icono.className = "fa fa-star";
                miDiv.appendChild(icono);
            } else {

                let miDiv = document.getElementById("div-stars-" + i);
                let icono = document.createElement("i");
                icono.className = "fa-regular fa-star";
                miDiv.appendChild(icono);
            }
        }
    }
}