function cadastrarCliente() {
    const clienteNomeElemnt = document.getElementById('nome')
    const clienteTelefoneElemnt = document.getElementById('telefone')
    const clienteEmailElemnt = document.getElementById('email')
    const formulario = {
        nome: clienteNomeElemnt.value,
        fone: clienteTelefoneElemnt.value,
        email: clienteEmailElemnt.value
    }
    var data = JSON.stringify(formulario)
    fetch('http://localhost:8080/treinamento-hepta/rest/clientes/inserir', {
        method: 'POST',
        headers: {
            "Content-Type": 'application/json',
            'Accept': 'application/json'
        },
        body: data
    })
        .then(res => {
            if (res.status == 201) {
                alert("Cadastrado com sucesso");
                window.location.href = "index.html"
            } else {
                res.text().then(data => alert(data));
            }

        })

}