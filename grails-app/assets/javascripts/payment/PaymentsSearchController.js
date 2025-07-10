function PaymentsSearchController() {
    var _this = this;
    this.reference = $(".js-payments-search");
    var searchInputController;

    this.init = function() { initSearchInput(); };

    var initSearchInput = function() {
        searchInputController = new PaymentSearchInputController(_this.reference);
        _this.reference.find(".js-search-input").on("atlas-input-trigger-search", function() {
            searchInputController.search();
        });
    };
}

var paymentsSearchController;
document.addEventListener("AtlasContentLoaded", function() {
    paymentsSearchController = new PaymentsSearchController();
    paymentsSearchController.init();
});