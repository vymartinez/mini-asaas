let formElements = {
    name: null,
    email: null,
    cpfCnpj: null,
    cellphone: null,
    zipCode: null,
    address: null,
    state: null,
    province: null,
    complement: null,
    addressNumber: null
};

const portugueseKeys = {
    name: "nome",
    email: "email",
    cpfCnpj: "cpfCnpj",
    cellphone: "telefone",
    zipCode: "cep",
    address: "logradouro",
    province: "bairro",
    state: "uf",
    addressNumber: "numero"
}

let ibgeCode = null;

for (const key in formElements) {
    let element = document.querySelector(`#${key}`);
    if (element) formElements[key] = element;
}

var postalCodeField = document.querySelector("atlas-postal-code");

postalCodeField.addEventListener("atlas-postal-code-change", () => {
    if (isValidCep(formElements.zipCode.value)) getZipCode(formElements.zipCode.value);
});

var button = document.querySelector("atlas-button");

button.addEventListener("click", (e) => {
    e.preventDefault();
    button.disabled = true;

    // validar os dados

    // chamar api
})

function getZipCode(zipCode) {
    try {
        const data = fetch(`https://viacep.com.br/ws/${zipCode}/json/`)
            .then(res => res.json())
            .then(data => {
                for (const key in formElements) {
                    if (data[portugueseKeys[key]]) {
                        formElements[key].value = data[portugueseKeys[key]];
                        formElements[key].disabled = false;
                    }
                }
                ibgeCode = data["ibge"];
                formElements.addressNumber.disabled = false;
                formElements.complement.disabled = false;
            })
    } catch (err) {
        alert("CEP não encontrado. Tente novamente.");
    }
}

function isValidCep(zipCode) {
    const newZipCode = zipCode.replace(/\D/g, '');
    return newZipCode.length === 8;
}