const stars = document.querySelectorAll(".star");
const rate = document.getElementById("rate");

stars.forEach((star) => {
    // star.addEventListener('mouseover', mouseOverEvent(star), false)
    // star.addEventListener('mouseout', mouseOutEvent(star), false)

    star.addEventListener('click', (e) => {
        let value = parseInt(star.id) + 1;
        rate.setAttribute("value", value.toString());

        stars.forEach((s) => {
            s.classList.remove('fa-solid');
            s.classList.add('fa-regular');

            // s.removeEventListener('mouseout', mouseOutEvent(s), false)
        })

        for (let i = 0; i <= star.id; i++) {
            stars[i].classList.remove('fa-regular');
            stars[i].classList.add('fa-solid');
        }
    })
})

// function mouseOverEvent(star) {
//     let index = star.id;
//
//     for (let i = 0; i <= index; i++) {
//         stars[i].classList.remove('fa-regular');
//         stars[i].classList.add('fa-solid');
//     }
// }
//
// function mouseOutEvent(star) {
//     let index = star.id;
//
//     for (let i = 0; i <= index; i++) {
//         stars[i].classList.remove('fa-solid');
//         stars[i].classList.add('fa-regular');
//     }
// }