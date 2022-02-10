listaClientes();

function listaClientes() {
    try {
        fetch('http://localhost:8080/treinamento-hepta/rest/clientes/' + localStorage.getItem('id'), {
            method: 'GET',
            headers: {
                "Content-Type": 'application/json'
            },
        })
            .then((response) => response.json())
            .then((data) => {
                document.querySelector('#nome').value = data.nome
                document.querySelector('#telefone').value = data.fone
                document.querySelector('#email').value = data.email
            })
    } catch (e) {
        console.error(e)
    }
}

function alterarCliente() {
    const clienteNomeElemnt = document.getElementById('nome')
    const clienteTelefoneElemnt = document.getElementById('telefone')
    const clienteEmailElemnt = document.getElementById('email')
    if (clienteNomeElemnt.value == "" || clienteEmailElemnt.value == "" || clienteTelefoneElemnt.value == "") {
        alert("Campos Obrigatórios não preenchidos")
    } else {
        const formulario = {
            nome: clienteNomeElemnt.value,
            fone: clienteTelefoneElemnt.value,
            email: clienteEmailElemnt.value
        }
        var data = JSON.stringify(formulario)
        fetch('http://localhost:8080/treinamento-hepta/rest/clientes/atualizar/' + localStorage.getItem('id'), {
            method: 'PUT',
            headers: {
                "Content-Type": 'application/json',
                'Accept': 'application/json'
            },
            body: data
        })
            .then(res => {
                if (res.status == 200) {
                    alert("Alterado com sucesso");
                    window.location.href = "index.html"
                } else {
                    res.text().then(data => alert(data));
                }

            })
    }
}