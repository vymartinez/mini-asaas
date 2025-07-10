function PaymentShowController() {
    var _this = this;
    this.reference = $(".js-payment-show");
    this.referenceForm = this.reference.find(".js-payment-show-form");
    var buttonController;
    var editBtn;

    this.init = function() {
        initButtonController();
        disableFields();
        bindEditButton();
        bindFormSubmit();
        loadPayerOptions();
    };

    var initButtonController = function() {
        var submitBtn = _this.referenceForm.find('.js-submit-button');
        buttonController = new ButtonController();
        buttonController.buttonReference = submitBtn;
    };

    var disableFields = function() {
        var fields = _this.referenceForm.find('input, select').not('[name="paymentId"]');
        fields.prop('disabled', true);
        editBtn = _this.reference.find('[data-panel-start-editing]');
    };

    var bindEditButton = function() {
        if (!editBtn) return;
        editBtn.on('click', function() {
            _this.referenceForm.find('input, select').prop('disabled', false);
            editBtn.prop('disabled', true);
        });
    };

    var bindFormSubmit = function() {
        _this.referenceForm.on('submit', function(e) {
            clearErrors(_this.referenceForm);
            var payerIdField = _this.referenceForm.find("[name='payerId']");
            var billingTypeField = _this.referenceForm.find("[name='billingType']");
            var valueField = _this.referenceForm.find("[name='value']");
            var dueDateField = _this.referenceForm.find("[name='dueDate']");
            var valid = true;

            if (!payerIdField.val()) {
                showError(payerIdField, 'Selecione um pagador.');
                valid = false;
            }
            if (!billingTypeField.val()) {
                showError(billingTypeField, 'Selecione o tipo de cobrança.');
                valid = false;
            }
            var val = parseFloat(valueField.val().replace(',', '.'));
            if (isNaN(val) || val <= 0) {
                showError(valueField, 'Informe um valor maior que zero.');
                valid = false;
            }
            if (!/^\d{4}-\d{2}-\d{2}$/.test(dueDateField.val())) {
                showError(dueDateField, 'Data de vencimento inválida.');
                valid = false;
            }
            if (!valid) e.preventDefault();
        });

        function showError(input, message) {
            var wrapper = input.closest('atlas-col') || input.parent();
            var err = wrapper.find('.form-error');
            if (!err.length) { err = $('<div>').addClass('form-error').appendTo(wrapper); }
            err.text(message);
        }

        function clearErrors(form) {
            form.find('.form-error').remove();
        }
    };

    function loadPayerOptions() {
        var container = $('#payer-options-container');
        if (!container.length) return;
        var selectedId = container.data('selected-id') || '';
        fetch(`/payer/selectOptions?selectedId=${selectedId}`)
            .then(function(resp) { if (!resp.ok) throw new Error(); return resp.text(); })
            .then(function(html) { container.html(html); })
            .catch(function() { container.html('<atlas-option disabled>Erro ao carregar pagadores</atlas-option>'); });
    }
}

var paymentShowController;
document.addEventListener("AtlasContentLoaded", function() {
    paymentShowController = new PaymentShowController();
    paymentShowController.init();
});