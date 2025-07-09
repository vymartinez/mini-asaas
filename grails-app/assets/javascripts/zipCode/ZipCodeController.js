function ZipCodeController(reference) {
    var _this = this;
    this.reference = reference;
    var zipCodeReference = this.reference.find("#zipCode");

    var buttonController = new ButtonController();
    buttonController.buttonReference = this.reference.find(".js-submit-button");

    var feedbackReference = this.reference.find(".js-feedback-message");
    var feedbackMessage = "CEP não encontrado. Verifique e tente novamente."

    this.search = function () {
        buttonController.disable();
        feedbackReference.html("");
        feedbackReference.attr("hidden", true);

        var zipCode = zipCodeReference[0].value;
        if (!zipCode) return;
        $.ajax({
            url: _this.reference.find(".js-zip-code-url").val(),
            type: 'GET',
            data: { zipCode: zipCode },
            async: true,
            success: function (data) {
                if (!data) return;

                buttonController.enable();
                _this.completeAddress(data)
            },
            error: function () {
                feedbackReference.html(feedbackMessage);
                feedbackReference.attr("hidden", false);
                buttonController.enable();
            }
        })
    }

    this.completeAddress = function (data) {
        _this.reference.find("#address").val(data.address);
        _this.reference.find("#cityId").val(data.city.id);
        _this.reference.find("#zipCode").val(data.zipCode);
        _this.reference.find("#cityName").val(data.name);
        _this.reference.find("#province").val(data.province);
        _this.reference.find("#state").val(data.state.name);
    }
}