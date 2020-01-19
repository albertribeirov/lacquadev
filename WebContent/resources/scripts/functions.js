function dataFormatada() {
	var data = new Date();
	var dia = data.getDate();
	if (dia.toString().length == 1) {
		dia = "0" + dia;
	}
	var mes = data.getMonth() + 1;
	if (mes.toString().length == 1) {
		mes = "0" + mes;
	}
	var ano = data.getFullYear();

	return dia + "/" + mes + "/" + ano;
}

// Chama função que preenche o campo data
$('#Data').val(dataFormatada);

// Verifica senha
function verificarSenha() {
	var senha1 = document.getElementById("");
	var senha2 = document.getElementById("");

	if (senha1 == senha2) {
		return true;
	}

	alert("Senha diferentes!");
	return false;
}

function listarApartamentos() {

}

// Transforma o campo de leitura em readonly após seu preenchimento.
function desabilitarCampoLeitura(pCampo) {
	let campo = $(pCampo);
	let valor = campo.val();
	if (valor != '' && valor != null) {
		campo.prop('readonly', true);
	} else {
		campo.prop('readonly', false);
	}
}

// Transforma o campo de leitura em writable com o evento doubleclick (duplo
// clique).
function habilitarCampoLeitura(pCampo) {
	let campo = $(pCampo);
	let valor = campo.prop('readonly', false);
}

// Solicita confirmação de exclusão
function excluir() {
	return confirm("Deseja excluir o registro?")
}

function formatarCampo(campoTexto) {
	if (campoTexto.value.length <= 11) {
		campoTexto.value = mascaraCpf(campoTexto.value);
	} else {
		campoTexto.value = mascaraCnpj(campoTexto.value);
	}
}

// <input type="text" onfocus="javascript: retirarFormatacao(this);"
// onblur="javascript: formatarCampo(this);" maxlength="14"/>

function retirarFormatacao(campoTexto) {
	campoTexto.value = campoTexto.value.replace(/(\.|\/|\-)/g, "");
}

function mascaraCpf(valor) {
	return valor.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/g, "\$1.\$2.\$3\-\$4");
}

function mascaraCnpj(valor) {
	return valor.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/g,
			"\$1.\$2.\$3\/\$4\-\$5");
}

// Verifica se o valor da leitura atual é menor que a leitura anterior e limpa o
function compararLeituras(pInput) {
	var tdLeitura = pInput;
	var leitura = Number(tdLeitura.nextElementSibling.value);
	var leituraAnterior = tdLeitura.parentElement.parentElement.previousSibling.firstElementChild.innerText;
	leituraAnterior = Number(leituraAnterior.replace(/\./g, ''));

	if (leitura < leituraAnterior) {
		alert("A leitura atual não pode ser menor que a leitura anterior.");
		tdLeitura.value = "";
	}
}