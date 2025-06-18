let postalCodeField = document.querySelector("atlas-postal-code");

postalCodeField.addEventListener("atlas-postal-code-change", async function () {
    let postalCodeData = await fetchPostalCodeData(removeNonNumeric(this.value));

    if (!postalCodeData) return console.error("Erro ao buscar o CEP. Por favor, verifique o CEP informado.");

    document.getElementById("zipCode").value = postalCodeData.zipCode;
    document.getElementById("cityId").value = postalCodeData.city.id;
    document.getElementById("cityName").value = postalCodeData.city.name;
    document.getElementById("address").value = postalCodeData.address;
    document.getElementById("state").value = postalCodeData.state.name;
    document.getElementById("province").value = postalCodeData.province;
});
const url = new URL(window.location.href);

async function fetchPostalCodeData(zipCode) {
    try {
        let response = await fetch(`${url.origin}/zipCode/find?zipCode=${zipCode}`)
        return await response.json()
    } catch (error) {
        return null;
    }
}

function removeNonNumeric(value) {
    return value.replace(/\D/g, '');
}