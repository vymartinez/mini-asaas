let formElements = {
    nome: null,
    cpf: null,
    telefone: null,
    cep: null,
    logradouro: null,
    bairro: null,
    uf: null,
    complemento: null
};

for (const key in formElements) {
    let element = document.querySelector(`#${key}`);
    if (element) formElements[key] = element;
    if (key === "cep") {
        element.addEventListener('input', () => {
            if (isValidCep(formElements.cep.value)) getCep(formElements.cep.value);
        });
    }
}

let button = document.querySelector("button");
button.addEventListener('click', (event) => {
    event.preventDefault();
    for (const key in formElements) {
        if (!formElements[key].value && key !== "complemento") return alert("Preencha todos os dados corretamente, por favor.");
    }
    for (const key in formElements) {
        console.log(formElements[key].value);
    }
    alert("Cadastro realizado com sucesso!");
})

function getCep(cep) {
    try {
        const data = fetch(`https://viacep.com.br/ws/${cep}/json/`)
            .then(res => res.json())
            .then(data => {
                for (const key in formElements) {
                    if (data[key]) formElements[key].value = data[key];
                }
                formElements.complemento.disabled = false;
            })
    } catch (err) {
        formElements.complemento.disabled = true;
        alert("CEP não encontrado. Tente novamente.");
    }
}

function isValidCep(cep) {
    const newCep = cep.replace(/\D/g, '');
    return newCep.length === 8;
}