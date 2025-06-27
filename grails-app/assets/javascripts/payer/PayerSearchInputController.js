function PayerSearchInputController(reference) {
    this.reference = reference;
    var searchInputReference = this.reference.find(".js-search-input");

    this.search = function () {
        var searchValue = searchInputReference[0].value;

        window.location.search = encodeURI(`?nameOrEmail[like]=${searchValue}&page=1`);
    };
}