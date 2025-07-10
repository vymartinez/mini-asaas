function NotificationListController() {
    var _this = this;
    this.reference = $(".has-atlas")
    var notificationListReference = this.reference.find("#notification-center");
    var paginationController = new PaginationController(notificationListReference);

    this.search = function () {
        $.ajax({
            url: _this.reference.find(".js-notification-url").val(),
            type: 'GET',
            data: { page: paginationController.page },
            async: true,
            success: function (data) {
                if (!data) return;

                notificationListReference.html(data);
                paginationController.bindEvents();
            }
        })
    }

    this.init = function () {
        _this.search();
        paginationController.search = _this.search;
    }
}

var notificationListController;

document.addEventListener("DOMContentLoaded", function() {
    notificationListController = new NotificationListController();
    notificationListController.init();
});