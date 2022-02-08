function listaOrdemServico() {
    try {
        fetch('http://localhost:8080/treinamento-hepta/rest/os/' + localStorage.getItem('id'), {
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
                <td> ${item.data} </td>
                <td> ${item.equipamento} </td>
                <td> ${item.servico} </td>
                <td> 
                    <button class="button" onClick="abrirBlob('${item.nota}')">Visualizar</button>
                </td>
                <td> 
                    <button class="button">&#9998;</button>
                    <button class="buttonDanger">&#9746;</button>
                </td>
                </tr>`
                })
            })
    } catch (e) {
        console.error(e)
    }
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

function abrirBlob(blob) {
    var file64 = base64ToArrayBuffer(blob); 
    var file = new Blob([file64], { type: "application/pdf" });
    var imageUrl = URL.createObjectURL(file);
    window.open(imageUrl);
   
}

listaOrdemServico();