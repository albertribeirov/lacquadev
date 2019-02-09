function dataFormatada(){
    var data = new Date();
	var dia = data.getDate();
	if (dia.toString().length == 1){
		dia = "0"+dia;
	}
	var mes = data.getMonth()+1;
	if (mes.toString().length == 1){
		mes = "0"+mes;
	}
	var ano = data.getFullYear();  
	
	return dia+"/"+mes+"/"+ano;
}
 
//Chama função que preenche o campo data
$('#Data').val(dataFormatada);

//Verifica senha
function verificarSenha(){
	var senha1 = document.getElementById("");
	var senha2 = document.getElementById("");
	
	if(senha1 == senha2) {
		return true;
	}
	
	alert("Senha diferentes!");
	return false;
}

function listarApartamentos(){
	
}

//Transforma o campo de leitura em readonly após seu preenchimento.
function desabilitarCampoLeitura(pCampo){
	let campo = $(pCampo);
	let valor = campo.val();
	if (valor != '' && valor != null) {
		campo.prop('readonly', true);			
	} else {
		campo.prop('readonly', false);		
	}
}

//Transforma o campo de leitura em writable com o evento doubleclick (duplo clique).
function habilitarCampoLeitura(pCampo) {
	let campo = $(pCampo);
	let valor = campo.prop('readonly', false);
}