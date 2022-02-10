listaClientes();

function listaClientes() {
    localStorage.removeItem('id');
    try {
        fetch('http://localhost:8080/treinamento-hepta/rest/clientes/', {
            method: 'GET',
            headers: {
                "Content-Type": 'application/json'
            },
        })
            .then((response) => response.json())
            .then((data) => {
                document.querySelectorAll(`div table tbody#event-table tr`).forEach(e => e.remove())
                data.map((item) => {
                    var lista = document.querySelector(`table tbody#event-table`)
                    lista.innerHTML += `<tr>
                <td> ${item.nome} </td>
                <td> ${item.fone} </td>
                <td> ${item.email} </td>
                <td> <a href="ordemservico.html" onclick = "localStorage.setItem('id', ${item.id})">Ordem de Serviço</a> </td>
                <td> 
                    <a href="alterarcliente.html"><button class="button" onclick = "localStorage.setItem('id', ${item.id})">&#9998;</button></a>       
                    <button class="buttonDanger" onClick= "deleteCliente('${item.id}')">&#9746;</button>
                </td>
                </tr>`
                })
            })
    } catch (e) {
        console.error(e)
    }
}

function deleteCliente(idCliente) {
    if (confirm('Deseja Excluir ?')) {
        fetch('http://localhost:8080/treinamento-hepta/rest/clientes/deletar/' + idCliente, {
            method: 'DELETE',
        })
            .then(data => {
                listaClientes();
            })

            .catch(error => alert(error))
    }
}

