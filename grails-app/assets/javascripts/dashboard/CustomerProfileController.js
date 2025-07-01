function CustomerProfileController() {
    var _this = this;

    this.reference = $(".js-customer-profile");
    this.referenceForm = this.reference.find(".js-customer-profile-form");

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

var customerProfileController;

document.addEventListener("AtlasContentLoaded", function () {
    customerProfileController = new CustomerProfileController();
    customerProfileController.init();
})