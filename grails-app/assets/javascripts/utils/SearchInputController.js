function SearchInputController(reference) {
    var _this = this;
    this.reference = reference;
    var searchInputReference = null;
    this.search = null;
    this.searchInput = null;
    this.firstPage = null;

    this.bindInput = function () {
        searchInputReference = _this.reference.find(".js-search-input");

        searchInputReference.on("atlas-input-trigger-search", () => {
            _this.searchInput = searchInputReference[0].value;
            _this.firstPage = true;
            _this.search();
        });

        _this.firstPage = false;
    }
}