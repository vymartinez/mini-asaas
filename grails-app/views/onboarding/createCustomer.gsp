<!doctype html>
<html lang="pt-BR">
<head>
    <meta name="layout" content="external"/>
    <title>Mini Asaas</title>
</head>

<body>
<atlas-panel class="js-create-customer">
    <atlas-form action="/user/create" method="post" class="js-create-customer-form">
        <atlas-heading>Cadastro de Usuários</atlas-heading>
        <atlas-grid>
            <atlas-row>
                <atlas-col lg="6">
                    <atlas-input label="Nome" id="name" name="name" type="text" placeholder="Nome" required value="${ params.name }"></atlas-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-masked-input label="Email" id="email" name="email" type="text" placeholder="Email" mask-alias="email" required value="${ params.email }"></atlas-masked-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-password-input label="Senha" id="password" name="password" placeholder="Senha" label="Senha" required value="${ params.password }"></atlas-password-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-masked-input label="CPF/CNPJ" id="cpfCnpj" name="cpfCnpj" type="text" placeholder="CPF/CNPJ" mask-alias="cpf-cnpj" required value="${ params.cpfCnpj }"></atlas-masked-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-postal-code label="CEP" id="zipCode" name="zipCode" type="text" placeholder="CEP" required value="${ params.zipCode }"></atlas-postal-code>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-input label="Bairro" id="province" name="province" type="text" placeholder="Bairro" required disabled value="${ params.province }"></atlas-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-input label="Logradouro" id="address" name="address" type="text" placeholder="Logradouro" required disabled value="${ params.address }"></atlas-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-input label="Cidade" id="cityName" name="cityName" type="text" placeholder="Cidade" required disabled value="${ params.cityName }"></atlas-input>
                </atlas-col>
                <atlas-col lg="3">
                    <atlas-input label="UF" id="state" name="state" type="text" placeholder="UF" required disabled value="${ params.state }"></atlas-input>
                </atlas-col>
                <atlas-col lg="3">
                    <atlas-input label="Número" id="addressNumber" name="addressNumber" type="text" placeholder="Número" required value="${ params.addressNumber }"></atlas-input>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-input label="Complemento" id="complement" name="complement" type="text" placeholder="Complemento" value="${ params.complement }"></atlas-input>
                </atlas-col>
                <atlas-input id="cityId" name="cityId" label="cityId" hidden value="${ params.cityId }"></atlas-input>
            </atlas-row>
        </atlas-grid>
        <atlas-input type="hidden" class="js-zip-code-url" value="${createLink(controller: 'zipCode', action: 'find')}"></atlas-input>
        <atlas-button
               class="js-submit-button" description="Cadastrar" submit block
        ></atlas-button>
    </atlas-form>
</atlas-panel>
<asset:javascript src="create-customer.js"/>
</body>
</html>