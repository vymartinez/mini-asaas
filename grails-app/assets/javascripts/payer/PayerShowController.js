function PayerShowController() {
    var _this = this;

    this.reference = $(".js-payer-show");
    this.referenceForm = this.reference.find(".js-payer-show-form");

    var zipCodeController;

    this.init = function () {
        initZipCode();
        bindFindZipCode();
    }

    var initZipCode = function () {
        zipCodeController = new ZipCodeController(_this.referenceForm);
    }

    var bindFindZipCode = function () {
        _this.referenceForm.find("#zipCode").on("atlas-postal-code-change", () => zipCodeController.search());
    }
}

var payerShowController;

document.addEventListener("AtlasContentLoaded", function () {
    payerShowController = new PayerShowController();
    payerShowController.init();
})