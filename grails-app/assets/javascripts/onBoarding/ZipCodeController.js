function ZipCodeController(reference) {
    var _this = this;
    var buttonController = new ButtonController();

    this.reference = reference;

    this.search = function () {
        buttonController.buttonReference = $(_this);
        buttonController.disable();

        $.ajax({
            url: '/zipCode/find',
            type: 'GET',
            data: { zipCode: _this.reference.val() },
            async: true,
            success: function (data) {
                buttonController.enable();

                if (!data.success) return;
                _this.completeAddress(data)
            }
        })
    }

    this.completeAddress = function (data) {
        _this.reference.find("#address").val(data.address);
        _this.reference.find("#cityId").val(data.city.id);
        _this.reference.find("#zipCode").val(data.zipCode);
        _this.reference.find("#cityName").val(data.name);
        _this.reference.find("#province").val(data.province);
        _this.reference.find("#state").val(data.state);
    }
}