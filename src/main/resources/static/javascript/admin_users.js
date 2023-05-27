const select = document.querySelector(".select-role");
const tables = document.querySelectorAll(".table__body");

select.addEventListener('change', function () {
        let selected = this.options[select.selectedIndex];

        tables.forEach((e) => {
            e.classList.add('hide')
        })

        switch (selected.value) {
            case 'Todos':
                tables[0].classList.remove('hide')
                break;
            case 'Proveedores':
                tables[1].classList.remove('hide')
                break;
            case 'Usuarios':
                tables[2].classList.remove('hide')
                break;
            case 'Administradores':
                tables[3].classList.remove('hide')
                break;
        }
    }
)
