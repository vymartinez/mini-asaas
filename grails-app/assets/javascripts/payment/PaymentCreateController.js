function PaymentCreateController() {
    var _this = this;
    this.reference = $(".js-create-payment");
    this.referenceForm = this.reference.find(".js-create-payment-form");
    var buttonController;

    this.init = function() {
        initButtonController();
        bindFormSubmit();
        loadPayerOptions();
    };

    var initButtonController = function() {
        var submitBtn = _this.referenceForm.find(".js-submit-button");
        buttonController = new ButtonController();
        buttonController.buttonReference = submitBtn;
    };

    var bindFormSubmit = function() {
        _this.referenceForm.on("submit", function(e) {
            e.preventDefault();
            clearErrors(_this.referenceForm);

            var data = {
                payerId: _this.referenceForm.find("[name='payerId']").val().trim(),
                billingType: _this.referenceForm.find("[name='billingType']").val(),
                value: _this.referenceForm.find("[name='value']").val().trim(),
                dueDate: _this.referenceForm.find("[name='dueDate']").val().trim()
            };

            var errors = validate(data);
            if (errors.length) {
                errors.forEach(function(err) {
                    showError(_this.referenceForm, err.field, err.message);
                });
                _this.referenceForm.find("[name='" + errors[0].field + "']").focus();
            } else {
                buttonController.disable();
                _this.referenceForm.submit();
            }
        });
    };

    function validate(data) {
        var errs = [];
        var pid = Number(data.payerId);
        if (!data.payerId || !Number.isInteger(pid) || pid <= 0) {
            errs.push({ field: 'payerId', message: 'Informe um Payer ID inteiro e maior que zero.' });
        }
        if (!data.billingType) {
            errs.push({ field: 'billingType', message: 'Selecione o tipo de cobrança.' });
        }
        var val = parseFloat(data.value.replace(',', '.'));
        if (!data.value || isNaN(val) || val <= 0) {
            errs.push({ field: 'value', message: 'Informe um valor numérico maior que zero.' });
        }
        if (!/^\d{4}-\d{2}-\d{2}$/.test(data.dueDate)) {
            errs.push({ field: 'dueDate', message: 'Data deve usar o formato YYYY-MM-DD.' });
        } else {
            var parts = data.dueDate.split('-').map(Number);
            var dt = new Date(parts[0], parts[1] - 1, parts[2]);
            if (dt.getFullYear() !== parts[0] || dt.getMonth() !== parts[1] - 1 || dt.getDate() !== parts[2]) {
                errs.push({ field: 'dueDate', message: 'Data de vencimento inválida.' });
            }
        }
        return errs;
    }

    function showError(form, field, message) {
        var input = form.find("[name='" + field + "']");
        if (!input.length) return;
        var wrapper = input.closest('atlas-col') || input.parent();
        var errorEl = $('<div>').addClass('form-error').text(message);
        wrapper.append(errorEl);
    }

    function clearErrors(form) {
        form.find('.form-error').remove();
    }

    function loadPayerOptions() {
        var container = $('#payer-options-container');
        if (!container.length) return;
        var selectedId = container.data('selected-id') || '';
        var url = '/payer/selectOptions' + (selectedId ? '?selectedId=' + selectedId : '');
        fetch(url)
            .then(function(resp) { if (!resp.ok) throw new Error('Erro ao carregar pagadores'); return resp.text(); })
            .then(function(html) { container.html(html); })
            .catch(function() { container.html('<atlas-option disabled>Erro ao carregar pagadores</atlas-option>'); });
    }
}

var paymentCreateController;
document.addEventListener("AtlasContentLoaded", function() {
    paymentCreateController = new PaymentCreateController();
    paymentCreateController.init();
});