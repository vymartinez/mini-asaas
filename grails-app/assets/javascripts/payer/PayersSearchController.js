function PayersSearchController() {
    var _this = this;
    this.reference = $(".js-payers-search");

    var searchInputController;

    this.init = function () {
        initSearchInput();
    }

    var initSearchInput = function () {
        searchInputController = new PayerSearchInputController(_this.reference);
        _this.reference.find(".js-search-input").on("atlas-input-trigger-search", () => searchInputController.search());
    }
}

var payersSearchController;

document.addEventListener("AtlasContentLoaded", function () {
    payersSearchController = new PayersSearchController();
    payersSearchController.init();
})