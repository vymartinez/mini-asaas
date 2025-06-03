let formElements = {
    name: null,
    email: null,
    cpfCnpj: null,
    cellphone: null,
    zipCode: null,
    address: null,
    city: null,
    province: null,
    complement: null
};

const portugueseKeys = {
    name: "nome",
    email: "email",
    cpfCnpj: "cpfCnpj",
    cellphone: "telefone",
    zipCode: "cep",
    address: "logradouro",
    city: "bairro",
    province: "uf",
    addressNumber: "addressNumber"
}

for (const key in formElements) {
    let element = document.querySelector(`#${key}`);
    if (element) formElements[key] = element;
    if (key === "zipCode") {
        element.addEventListener('input', () => {
            if (isValidCep(formElements.zipCode.value)) getZipCode(formElements.zipCode.value);
        });
    }
}

function getZipCode(zipCode) {
    try {
        const data = fetch(`https://viacep.com.br/ws/${zipCode}/json/`)
            .then(res => res.json())
            .then(data => {
                for (const key in formElements) {
                    if (data[portugueseKeys[key]]) formElements[key].value = data[portugueseKeys[key]];
                }
            })
    } catch (err) {
        alert("CEP não encontrado. Tente novamente.");
    }
}

function isValidCep(zipCode) {
    const newZipCode = zipCode.replace(/\D/g, '');
    return newZipCode.length === 8;
}