listaOrdemServico();
var file;
function listaOrdemServico() {
    try {
        fetch('http://localhost:8080/treinamento-hepta/rest/os/buscarid/' + localStorage.getItem('idOs'), {
            method: 'GET',
            headers: {
                "Content-Type": 'application/json'
            },
        })
            .then((response) => response.json())
            .then((data) => {
                document.querySelector('#data').value = data.data
                document.querySelector('#equipamento').value = data.equipamento
                document.querySelector('#servico').value = data.servico
                file = data.nota
                var visualizar = document.querySelector('.button');
                visualizar.addEventListener('click', function (event) {
                    abrirBlob(data.nota);
                })
            })
    } catch (e) {
        console.error(e)
    }
}

function atualizarOs() {
    const osDataElemnt = document.getElementById('data')
    const osEquipamentoElemnt = document.getElementById('equipamento')
    const osServicoElemnt = document.getElementById('servico')
    fileUp = document.querySelector('#nota').files[0]
    data = new Date(osDataElemnt.value);
    dataFormatada = data.toLocaleDateString('pt-BR', { timeZone: 'UTC' });

    if (dataFormatada == "Invalid Date" || osEquipamentoElemnt.value == "" || osServicoElemnt.value == "") {
        alert("Campos obrigatórios não preenchidos");
    } else {
        if (fileUp == null) {
            const formulario = {
                data: dataFormatada,
                equipamento: osEquipamentoElemnt.value,
                servico: osServicoElemnt.value,
                idCliente: localStorage.getItem('id'),
                nota: file
            }
            salvarAlteracao(formulario);
        } else {
            getBase64(fileUp, function (base64Data) {
                my_file_as_base64 = base64Data.replace(/^data:application\/[a-z]+;base64,/, "");
                const formulario = {
                    data: dataFormatada,
                    equipamento: osEquipamentoElemnt.value,
                    servico: osServicoElemnt.value,
                    idCliente: localStorage.getItem('id'),
                    nota: my_file_as_base64
                }
                salvarAlteracao(formulario);
            });
        }
    }
}

function salvarAlteracao(formulario) {
    var data = JSON.stringify(formulario)
    fetch('http://localhost:8080/treinamento-hepta/rest/os/atualizar/' + localStorage.getItem('idOs'), {
        method: 'PUT',
        headers: {
            "Content-Type": 'application/json',
            'Accept': 'application/json'
        },
        body: data
    })
        .then(res => {
            if (res.status == 200) {
                alert("Atualizado com sucesso");
                window.location.href = "ordemservico.html"
            } else {
                res.text().then(data => alert(data));
            }
        })
}

function abrirBlob(blob) {
    var file64 = base64ToArrayBuffer(blob);
    var file = new Blob([file64], { type: "application/pdf" });
    var pdfUrl = URL.createObjectURL(file);
    window.open(pdfUrl);

}
function base64ToArrayBuffer(base64) {
    var binaryString = window.atob(base64);
    var binaryLen = binaryString.length;
    var bytes = new Uint8Array(binaryLen);
    for (var i = 0; i < binaryLen; i++) {
        var ascii = binaryString.charCodeAt(i);
        bytes[i] = ascii;
    }
    return bytes;
}

function getBase64(file, callback) {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(file);
}