function cadastrarOs() {
    const osDataElemnt = document.getElementById('data')
    const osEquipamentoElemnt = document.getElementById('equipamento')
    const osServicoElemnt = document.getElementById('servico')
    file = document.querySelector('#nota').files[0]
    var my_file_as_base64;
    if (file == null) {
        alert("Obrigatório anexar um arquivo");
    } else {
        getBase64(file, function (base64Data) {
            my_file_as_base64 = base64Data.replace(/^data:application\/[a-z]+;base64,/, "");

            data = new Date(osDataElemnt.value);
            dataFormatada = data.toLocaleDateString('pt-BR', { timeZone: 'UTC' });
            if (dataFormatada == "Invalid Date") {
                alert("Obrigatório informar uma data");
            } else {
                const formulario = {
                    data: dataFormatada,
                    equipamento: osEquipamentoElemnt.value,
                    servico: osServicoElemnt.value,
                    idCliente: localStorage.getItem('id'),
                    nota: my_file_as_base64
                }

                var data = JSON.stringify(formulario)
                fetch('http://localhost:8080/treinamento-hepta/rest/os/inserir', {
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
                            window.location.href = "ordemservico.html"
                        } else {
                            res.text().then(data => alert(data));
                        }
                    })
            }
        });
    }
}

function getBase64(file, callback) {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(file);
}