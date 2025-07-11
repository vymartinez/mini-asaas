function PayersSearchController() {
    var _this = this;
    this.reference = $(".js-payers-search");

    var searchInputController = new SearchInputController(_this.reference);
    var paginationController = new PaginationController(_this.reference);

    this.search = function () {
        $.ajax({
            url: _this.reference.find(".js-payers-search-url").val(),
            type: 'GET',
            data: {
                "nameOrEmail[like]": searchInputController.searchInput,
                "page": !searchInputController.firstPage ? paginationController.page : 1
            },
            async: true,
            success: function (data) {
                if (!data) return;

                _this.reference.html(data);
                searchInputController.bindInput();
                paginationController.bindEvents();
            }
        })
    }

    this.init = function () {
        _this.search();
        searchInputController.search = _this.search;
        paginationController.search = _this.search;
    }
}

var payersSearchController;

document.addEventListener("AtlasContentLoaded", function () {
    payersSearchController = new PayersSearchController();
    payersSearchController.init();
})