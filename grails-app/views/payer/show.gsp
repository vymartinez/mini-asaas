<html>
<head>
    <meta name="layout" content="internal">
    <title>Mini Asaas</title>
</head>
<body>
<atlas-panel class="js-payer-show">
        <atlas-heading>Detalhes do Pagador</atlas-heading>
    <atlas-form action="${createLink(controller: "payer", action: "update")}" class="js-payer-show-form">
        <atlas-input
            value="${payer.id}"
            name="id"
            hidden
        >
        </atlas-input>
        <atlas-button slot="actions" description="Editar" data-panel-start-editing></atlas-button>
        <atlas-grid>
            <atlas-row>
                <atlas-col lg="6">
                    <atlas-input
                        label="Nome"
                        name="name"
                        required
                        value="${params.name ?: payer.name}"
                    >
                    </atlas-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-masked-input
                        label="Email"
                        name="email"
                        mask-alias="email"
                        value="${params.email ?: payer.email}"
                        required
                    ></atlas-masked-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-masked-input
                        label="CPF/CNPJ"
                        id="cpfCnpj"
                        name="cpfCnpj"
                        type="text"
                        value="${params.cpfCnpj ?: payer.cpfCnpj}"
                        placeholder="CPF/CNPJ"
                        mask-alias="cpf-cnpj"
                        required
                    ></atlas-masked-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-masked-input
                        label="Telefone"
                        id="cellPhone"
                        name="cellPhone"
                        type="text"
                        value="${params.cellPhone ?: payer.cellPhone}"
                        placeholder="Telefone"
                        mask-alias="cell-phone"
                        required
                    ></atlas-masked-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-postal-code
                        label="CEP"
                        id="zipCode"
                        name="zipCode"
                        value="${params.zipCode ?: payer.address.zipCode}"
                        type="text"
                        placeholder="CEP"
                        required
                    ></atlas-postal-code>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-input
                        label="Bairro"
                        id="province"
                        name="province"
                        type="text"
                        value="${params.province ?: payer.address.province}"
                        placeholder="Bairro"
                        required
                        disabled
                    ></atlas-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-input
                        label="Logradouro"
                        id="address"
                        name="address"
                        type="text"
                        value="${params.address ?: payer.address.address}"
                        placeholder="Logradouro"
                        required
                        disabled
                    ></atlas-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-input
                        label="UF"
                        id="state" name="state"
                        type="text"
                        value="${params.state ?: payer.address?.city.state}"
                        placeholder="UF"
                        required
                        disabled
                    ></atlas-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-input
                        label="Cidade"
                        id="cityName" name="cityName"
                        type="text"
                        value="${params.cityName ?: payer.address?.city.name}"
                        placeholder="Cidade"
                        required
                        disabled
                    ></atlas-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-input
                        label="Número"
                        id="addressNumber"
                        name="addressNumber"
                        type="text"
                        value="${params.addressNumber ?: payer.address.addressNumber}"
                        placeholder="Número"
                        required
                    ></atlas-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-input
                        label="Complemento"
                        id="complement"
                        name="complement"
                        type="text"
                        value="${params.complement ?: payer.address.complement}"
                        placeholder="Complemento"
                    ></atlas-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-input
                        label="Data de criação"
                        name="dateCreated"
                        value="${formatTagLib.date(date: payer.dateCreated)}"
                        required
                        disabled
                    >
                    </atlas-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-input
                        label="Id do Cliente vinculado"
                        name="customerId"
                        required
                        disabled
                        value="${payer.customer.id}"
                    >
                    </atlas-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-input
                        label="Nome do Cliente vinculado"
                        name="customerName"
                        required="true"
                        disabled
                        value="${payer.customer.name}"
                    >
                    </atlas-input>
                </atlas-col>
            </atlas-row>
        </atlas-grid>
        <atlas-input id="cityId" name="cityId" label="cityId" hidden value="${ params.cityId ?: payer.address.city.id }"></atlas-input>
        <atlas-input type="hidden" class="js-zip-code-url" value="${createLink(controller: 'zipCode', action: 'find')}"></atlas-input>
        <atlas-button
                class="js-submit-button" description="Atualizar dados do pagador" submit block
        ></atlas-button>
    </atlas-form>
</atlas-panel>
<asset:javascript src="payer-show.js"/>
</body>
</html>