function PaymentSearchInputController(reference) {
    this.reference = reference;
    var searchInputReference = this.reference.find(".js-search-input");

    this.search = function() {
        var term = searchInputReference.val().trim();
        window.location.search = encodeURI(`?payer.name[like]=${term}&page=1`);
    };
}