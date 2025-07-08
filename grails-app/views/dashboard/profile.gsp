<!doctype html>
<html lang="pt-BR">
<head>
    <meta name="layout" content="internal"/>
    <title>Mini Asaas</title>
</head>

<body>
<atlas-panel class="js-customer-profile">
    <atlas-layout gap="5" mobile-gap="5" >
        <atlas-layout alignment="center" justify="space-between" inline mobile-inline >
            <atlas-heading muted >Perfil</atlas-heading>
            <atlas-avatar user-name="Usuário" size="lg"> </atlas-avatar>
        </atlas-layout>
        <atlas-heading alignment="center" >Dados do Usuário</atlas-heading>
        <atlas-form class="js-customer-profile-form" action="/user/update">
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
                            value="${ params.name ?: customer.name }"
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
                            alias="email"
                            required
                            value="${ params.email ?: customer.email }"
                        >
                        </atlas-masked-input>
                    </atlas-col>
                    <atlas-col lg="6">
                        <atlas-password-input
                            label="Senha"
                            id="password"
                            name="password"
                            placeholder="Senha"
                            label="Senha"
                            required
                            value="${ params.password }"
                        ></atlas-password-input>
                    </atlas-col>
                    <atlas-col lg="6">
                        <atlas-masked-input
                            label="CPF/CNPJ"
                            id="cpfCnpj"
                            name="cpfCnpj"
                            type="text"
                            placeholder="CPF/CNPJ"
                            value="${ params.cpfCnpj ?: customer.cpfCnpj }"
                            alias="cpf-cnpj"
                            required
                        ></atlas-masked-input>
                    </atlas-col>
                    <atlas-col lg="6">
                        <atlas-postal-code
                            label="CEP"
                            id="zipCode"
                            name="zipCode"
                            value="${ params.zipCode ?: customer.address.zipCode }"
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
                            value="${ params.province ?: customer.address.province }"
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
                            value="${ params.address ?: customer.address.address }"
                            required
                            disabled
                        ></atlas-input>
                    </atlas-col>
                    <atlas-col lg="3">
                        <atlas-input
                            label="UF"
                            id="state" name="state"
                            type="text"
                            placeholder="UF"
                            value="${ params.state ?: customer.address.city.state }"
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
                            value="${ params.addressNumber ?: customer.address.addressNumber }"
                            required
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
                            value="${ params.cityName ?: customer.address.city.name }">
                        </atlas-input>
                    </atlas-col>
                    <atlas-col lg="6">
                        <atlas-input
                            label="Complemento"
                            id="complement"
                            name="complement"
                            type="text"
                            placeholder="Complemento"
                            value="${ params.complement ?: customer.address.complement }"
                        ></atlas-input>
                    </atlas-col>
                </atlas-row>
                <atlas-input id="cityId" name="cityId" label="cityId" hidden value="${ params.cityId ?: customer.address.city.id }"></atlas-input>
                <atlas-input type="hidden" class="js-zip-code-url" value="${createLink(controller: 'zipCode', action: 'find')}"></atlas-input>
            </atlas-grid>
            <atlas-button
                    description="Atualizar dados" submit block class="js-submit-button"
            ></atlas-button>
            <atlas-helper-text class="js-feedback-message" criticality="warning" hidden></atlas-helper-text>
        </atlas-form>
    </atlas-layout>
</atlas-panel>
<asset:javascript src="customer-profile.js"/>
</body>
</html>