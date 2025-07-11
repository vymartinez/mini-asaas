function PaginationController(reference) {
    var _this = this;
    this.reference = reference;
    var prevButton;
    var nextButton;
    this.page = 1;
    this.search = null;

    this.bindEvents = function () {
        prevButton = _this.reference.find(".js-previous-page-button");
        nextButton = _this.reference.find(".js-next-page-button");

        prevButton.on("atlas-button-click", () => _this.paginate(prevButton));
        nextButton.on("atlas-button-click", () => _this.paginate(nextButton));
    }

    this.paginate = function (buttonReference) {
        _this.page = buttonReference.attr("description");
        _this.search();
    }
}