function NotificationListController() {
    var _this = this;
    this.reference = $(".has-atlas");
    var notificationListReference = this.reference.find("#notification-center");

    this.init = function () {
        $.ajax({
            url: _this.reference.find(".js-notification-url").val(),
            type: 'GET',
            async: true,
            success: function (data) {
                if (!data) return;

                notificationListReference[0].innerHTML = data
            }
        })
    }
}

var notificationListController;

document.addEventListener("AtlasContentLoaded", function() {
    notificationListController = new NotificationListController();
    notificationListController.init();
});