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
            "Content-Type": 'application/json'
        },
        body: data
    })
        .then(res => alert(res))
        .catch(res => alert (res))
}

function goBack() {
    window.history.back()
}