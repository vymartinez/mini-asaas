<html>
<head>
    <meta name="layout" content="internal">
    <title>Mini Asaas</title>
</head>
<body>
<atlas-form-panel action="${createLink(controller: "payer", action: "update")}" header="Detalhes do Pagador">
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
                    value="${payer.name}"
                >
                </atlas-input>
            </atlas-col>
            <atlas-col lg="6">
                <atlas-masked-input
                    label="Email"
                    name="email"
                    mask-alias="email"
                    value="${payer.email}"
                    required
                ></atlas-masked-input>
            </atlas-col>
            <atlas-col lg="6">
                <atlas-masked-input
                    label="CPF/CNPJ"
                    id="cpfCnpj"
                    name="cpfCnpj"
                    type="text"
                    value="${payer.cpfCnpj}"
                    placeholder="CPF/CNPJ"
                    alias="cpf-cnpj"
                    required
                ></atlas-masked-input>
            </atlas-col>
            <atlas-col lg="6">
                <atlas-masked-input
                    label="Telefone"
                    id="cellphone"
                    name="cellphone"
                    type="text"
                    value="${payer.cellPhone}"
                    placeholder="Telefone"
                    alias="cell-phone"
                    required
                ></atlas-masked-input>
            </atlas-col>
            <atlas-col lg="6">
                <atlas-postal-code
                    label="CEP"
                    id="zipCode"
                    name="zipCode"
                    value="${payer.address?.zipCode}"
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
                    value="${payer.address?.province}"
                    placeholder="Bairro"
                    required
                    readonly
                ></atlas-input>
            </atlas-col>
            <atlas-col lg="6">
                <atlas-input
                    label="Logradouro"
                    id="address"
                    name="address"
                    type="text"
                    value="${payer.address?.address}"
                    placeholder="Logradouro"
                    required
                    readonly
                ></atlas-input>
            </atlas-col>
            <atlas-col lg="6">
                <atlas-input
                    label="UF"
                    id="state" name="state"
                    type="text"
                    value="${payer.address?.city?.state}"
                    placeholder="UF"
                    required
                    readonly
                ></atlas-input>
            </atlas-col>
            <atlas-col lg="6">
                <atlas-input
                    label="UF"
                    id="cityName" name="cityName"
                    type="text"
                    value="${payer.address?.city?.name}"
                    placeholder="Cidade"
                    required
                    readonly
                ></atlas-input>
            </atlas-col>
            <atlas-col lg="6">
                <atlas-input
                    label="Número"
                    id="addressNumber"
                    name="addressNumber"
                    type="text"
                    value="${payer.address?.addressNumber}"
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
                    value="${payer.address?.complement}"
                    placeholder="Complemento"
                ></atlas-input>
            </atlas-col>
            <atlas-col lg="6">
                <atlas-input
                    label="Data de criação"
                    name="dateCreated"
                    value="${formatTagLib.formatAnyDate(date: payer.dateCreated)}"
                    required
                    readonly
                >
                </atlas-input>
            </atlas-col>
            <atlas-col lg="6">
                <atlas-input
                    label="Id do Cliente vinculado"
                    name="customerId"
                    required
                    readonly
                    value="${payer.customer.id}"
                >
                </atlas-input>
            </atlas-col>
            <atlas-col lg="6">
                <atlas-input
                    label="Nome do Cliente vinculado"
                    name="customerName"
                    required="true"
                    readonly
                    value="${payer.customer.name}"
                >
                </atlas-input>
            </atlas-col>
        </atlas-row>
    </atlas-grid>
</atlas-form-panel>
</body>
</html>