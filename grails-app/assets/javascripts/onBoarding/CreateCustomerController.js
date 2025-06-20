function CreateCustomerController() {
    var _this = this;

    _this.reference = $(".js-create-customer");
    _this.referenceForm = _this.reference.find(".js-create-customer-form");

    var zipCodeController;

    _this.init = function () {
        initZipCode();
        bindFindZipCode();
    }

    var initZipCode = function () {
        zipCodeController = new ZipCodeController(_this);
    }

    var bindFindZipCode = function () {
        _this.referenceForm.find("atlas-postal-code").on("atlas-postal-code-change", zipCodeController.search());
    }
}

var createCustomerController;

document.addEventListener("AtlasContentLoaded", function () {
    createCustomerController = new CreateCustomerController();
    createCustomerController.init();
})