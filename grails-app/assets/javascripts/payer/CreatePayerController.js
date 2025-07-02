function CreatePayerController() {
    var _this = this;

    this.reference = $(".js-create-payer");
    this.referenceForm = this.reference.find(".js-create-payer-form");

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

var createPayerController;

document.addEventListener("AtlasContentLoaded", function () {
    createPayerController = new CreatePayerController();
    createPayerController.init();
})