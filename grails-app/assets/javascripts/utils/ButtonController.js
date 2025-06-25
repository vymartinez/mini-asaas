function ButtonController() {
    this.descriptionWait = "Aguarde..."
    this.buttonReference = null;
    this.oldDescription = null;
    this.withoutSpin = null;
    this.spinStyleClass = "";

    this.disable = function() {
        if (this.buttonReference.data("descriptionWait")) {
            this.descriptionWait = this.buttonReference.data("descriptionWait");
        }

        if (this.buttonReference.hasClass("btn-default")) {
            this.buttonReference.css("color", this.buttonReference.css("color"));
        }

        this.oldDescription = this.buttonReference.html();
        this.buttonReference.prop('disabled', true);
        this.buttonReference.addClass("disabled");

        if (!this.withoutSpin) this.buttonReference.html(this.buildSpinHtml() + this.descriptionWait);
    }

    this.disableOnlySpinner = function() {
        this.oldDescription = this.buttonReference.html();
        this.buttonReference.prop('disabled', true);
        this.buttonReference.addClass("disabled");
        this.buttonReference.html(this.buildSpinHtml());
    }

    this.enable = function() {
        this.buttonReference.prop('disabled', false);
        this.buttonReference.removeClass("disabled");
        this.buttonReference.html(this.oldDescription);
    };

    this.buildSpinHtml = function()  {
        return "<i class='fa fa-circle-o-notch fa-spin fa-fw " + this.spinStyleClass + "' style='margin-right: 5px;'></i>";
    };
}