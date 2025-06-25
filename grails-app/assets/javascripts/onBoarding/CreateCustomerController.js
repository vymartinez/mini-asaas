function CreateCustomerController() {
    var _this = this;

    this.reference = $(".js-create-customer");
    this.referenceForm = this.reference.find(".js-create-customer-form");

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

var createCustomerController;

document.addEventListener("AtlasContentLoaded", function () {
    createCustomerController = new CreateCustomerController();
    createCustomerController.init();
})