(() => {
    'use strict';

    window.addEventListener('DOMContentLoaded', () => {
        const form= document.querySelector('.js-payment-show-form');
        const submitBtn= form?.querySelector('.js-submit-button');
        const editBtn= document.querySelector('[data-panel-start-editing]');

        if (!form || !editBtn || !submitBtn) return;

        const fields = Array.from(form.querySelectorAll('input, select'))
            .filter(f => f.name !== 'paymentId');

        fields.forEach(f => f.disabled = true);

        editBtn.addEventListener('click', () => {
            fields.forEach(f => f.disabled = false);
            if (editBtn.tagName !== 'ATLAS-BUTTON') editBtn.disabled = true;
        });

        form.addEventListener('submit', event => {
            clearErrors(form);

            const payerId = form.querySelector('[name="payerId"]').value;
            const billingType = form.querySelector('[name="billingType"]').value;
            const valueField= form.querySelector('[name="value"]');
            const dueDateField= form.querySelector('[name="dueDate"]');

            let valid= true;

            if (!payerId) {
                showError(form.querySelector('[name="payerId"]'), 'Selecione um pagador.');
                valid = false;
            }
            if (!billingType) {
                showError(form.querySelector('[name="billingType"]'), 'Selecione o tipo de cobrança.');
                valid = false;
            }

            const val = parseFloat(valueField.value.replace(',', '.'));

            if (isNaN(val) || val <= 0) {
                showError(valueField, 'Informe um valor maior que zero.');
                valid = false;
            }

            if (!/^\d{4}-\d{2}-\d{2}$/.test(dueDateField.value)) {
                showError(dueDateField, 'Data de vencimento inválida.');
                valid = false;
            }

            if (!valid) event.preventDefault();
        });

        function showError(input, message) {
            const wrapper = input.closest('atlas-col') || input.parentElement;
            let err = wrapper.querySelector('.form-error');
            if (!err) {
                err = document.createElement('div');
                err.className = 'form-error';
                wrapper.appendChild(err);
            }
            err.textContent = message;
        }

        function clearErrors(form) {
            form.querySelectorAll('.form-error').forEach(el => el.remove());
        }
    });
})();