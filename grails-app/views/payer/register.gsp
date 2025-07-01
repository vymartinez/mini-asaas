<<html lang="pt-BR">
<head>
    <meta name="layout" content="internal"/>
    <title>Mini Asaas</title>
</head>

<body>
<atlas-panel class="js-create-payer">
    <atlas-layout gap="5" mobile-gap="5" >
        <atlas-heading muted >Criar Pagador</atlas-heading>
        <atlas-form action="/payer/create" class="js-create-payer-form">
            <atlas-grid container >
                <atlas-row>
                    <atlas-col lg="6">
                        <atlas-input
                            label="Nome"
                            id="name"
                            name="name"
                            type="text"
                            placeholder="Nome"
                            required
                        >
                        </atlas-input>
                    </atlas-col>
                    <atlas-col lg="6">
                        <atlas-masked-input
                            label="Email"
                            id="email"
                            name="email"
                            type="text"
                            placeholder="Email"
                            mask-alias="email"
                            required
                        >
                        </atlas-masked-input>
                    </atlas-col>
                    <atlas-col lg="6">
                        <atlas-masked-input
                            label="CPF/CNPJ"
                            id="cpfCnpj"
                            name="cpfCnpj"
                            type="text"
                            placeholder="CPF/CNPJ"
                            mask-alias="cpf-cnpj"
                            required
                        ></atlas-masked-input>
                    </atlas-col>
                    <atlas-col lg="6">
                        <atlas-masked-input
                            label="Telefone"
                            id="cellphone"
                            name="cellphone"
                            type="text"
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
                            placeholder="Logradouro"
                            required
                            disabled
                        ></atlas-input>
                    </atlas-col>
                    <atlas-col lg="6">
                        <atlas-input
                            label="Cidade"
                            id="cityName"
                            name="cityName"
                            type="text"
                            placeholder="Cidade"
                            required
                            disabled
                            value="${ params.cityName }">
                        </atlas-input>
                    </atlas-col>
                    <atlas-col lg="3">
                        <atlas-input
                            label="UF"
                            id="state" name="state"
                            type="text"
                            placeholder="UF"
                            required
                            disabled
                        ></atlas-input>
                    </atlas-col>
                    <atlas-col lg="3">
                        <atlas-input
                            label="Número"
                            id="addressNumber"
                            name="addressNumber"
                            type="text"
                            placeholder="Número"
                            required
                            disabled
                        ></atlas-input>
                    </atlas-col>
                    <atlas-col lg="6">
                        <atlas-input
                            label="Complemento"
                            id="complement"
                            name="complement"
                            type="text"
                            placeholder="Complemento"
                        ></atlas-input>
                    </atlas-col>
                </atlas-row>
            </atlas-grid>
            <atlas-button
                description="Cadastrar" submit block class="js-submit-button"
            ></atlas-button>
            <atlas-input id="cityId" name="cityId" label="cityId" hidden value="${ params.cityId }"></atlas-input>
            <atlas-input type="hidden" class="js-zip-code-url" value="${createLink(controller: 'zipCode', action: 'find')}"></atlas-input>
        </atlas-form>
    </atlas-layout>
</atlas-panel>
<asset:javascript src="create-payer.js"/>
</body>
</html>