function PaymentsFilterController() {
    const atlasFilterForm = document.querySelector("atlas-filter-form");
    if (!atlasFilterForm) return;

    const baseUrl = atlasFilterForm.getAttribute("data-base-url");

    atlasFilterForm.addEventListener("atlas-apply-filter", (e) => {
        const filters = e.detail.filterData || {};
        const params = new URLSearchParams();

        for (const key in filters) {
            const value = filters[key];
            if (value !== undefined && value !== null && value !== '') {
                params.set(key, value);
            }
        }

        window.location.href = `${baseUrl}?${params.toString()}`;
    });

    atlasFilterForm.addEventListener("atlas-clean-filter", () => {
        window.location.href = baseUrl;
    });

    loadPayerOptions();
}

function loadPayerOptions() {
    const container = document.getElementById('payer-options-container');
    if (!container) return;

    const selectedId = container.dataset.selectedId || '';
    const url = '/payer/selectOptions' + (selectedId ? `?selectedId=${selectedId}` : '');

    fetch(url)
        .then((resp) => {
            if (!resp.ok) throw new Error('Erro ao carregar pagadores');
            return resp.text();
        })
        .then((html) => {
            container.innerHTML = html;
        })
        .catch(() => {
            container.innerHTML = '<atlas-option disabled>Erro ao carregar pagadores</atlas-option>';
        });
}

document.addEventListener("AtlasContentLoaded", PaymentsFilterController);