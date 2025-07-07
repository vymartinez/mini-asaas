'use strict';

(() => {

    function init() {

        const form = document.querySelector('atlas-form[action="/payment/create"]');
        if (!form) return;

        form.addEventListener('submit', onSubmit);
    }

    function onSubmit(event) {
        event.preventDefault();
        const form = event.target;
        clearErrors(form);

        const data = {
            payerId: form.querySelector('[name="payerId"]').value.trim(),
            billingType: form.querySelector('[name="billingType"]').value,
            value: form.querySelector('[name="value"]').value.trim(),
            dueDate: form.querySelector('[name="dueDate"]').value.trim(),
        };

        const errors = validate(data);

        if (errors.length) {
            errors.forEach(err => showError(form, err.field, err.message));
            form.querySelector(`
              input[name="${errors[0].field}"],
              select[name="${errors[0].field}"]
            `).focus();
            return;
        }

        form.submit();
    }

    function validate(data) {
        const errs = [];

        const pid = Number(data.payerId);
        if (!data.payerId || !Number.isInteger(pid) || pid <= 0) {
            errs.push({ field: 'payerId', message: 'Informe um Payer ID inteiro e maior que zero.' });
        }

        if (!data.billingType) {
            errs.push({ field: 'billingType', message: 'Selecione o tipo de cobrança.' });
        }

        const val = parseFloat(data.value.replace(',', '.'));
        if (!data.value || Number.isNaN(val) || val <= 0) {
            errs.push({ field: 'value', message: 'Informe um valor numérico maior que zero.' });
        }

        if (!/^\d{4}-\d{2}-\d{2}$/.test(data.dueDate)) {
            errs.push({ field: 'dueDate', message: 'Data deve usar o formato YYYY-MM-DD.' });
        } else {
            const [y, m, d] = data.dueDate.split('-').map(Number);
            const dt = new Date(y, m - 1, d);
            if (dt.getFullYear() !== y || dt.getMonth() !== m - 1 || dt.getDate() !== d) {
                errs.push({ field: 'dueDate', message: 'Data de vencimento inválida.' });
            }
        }

        return errs;
    }

    function showError(form, field, message) {
        const input = form.querySelector(`[name="${field}"]`);
        if (!input) return;

        const wrapper = input.closest('atlas-col') || input.parentElement;
        const errorEl = document.createElement('div');
        errorEl.className = 'form-error';
        errorEl.textContent = message;
        wrapper.appendChild(errorEl);
    }

    function clearErrors(form) {
        form.querySelectorAll('.form-error').forEach(el => el.remove());
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
})();