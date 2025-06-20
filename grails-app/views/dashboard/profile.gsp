<!doctype html>
<html lang="pt-BR">
<head>
    <meta name="layout" content="internal"/>
    <title>Mini Asaas</title>
</head>

<body>
<atlas-panel>
    <atlas-layout gap="5" mobile-gap="5" >
        <atlas-layout alignment="center" justify="space-between" inline mobile-inline >
            <atlas-heading muted >Perfil</atlas-heading>
            <atlas-avatar user-name="Usuário" size="lg"> </atlas-avatar>
        </atlas-layout>
        <atlas-heading alignment="center" >Dados do Usuário</atlas-heading>
        <atlas-form action="/customer/update">
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
                            alias="email"
                            required
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
                        ></atlas-password-input>
                    </atlas-col>
                    <atlas-col lg="6">
                        <atlas-masked-input
                            label="CPF/CNPJ"
                            id="cpfCnpj"
                            name="cpfCnpj"
                            type="text"
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
                            disabled
                        ></atlas-input>
                    </atlas-col>
                </atlas-row>
            </atlas-grid>
            <atlas-button
                    description="Atualizar dados" submit block
            ></atlas-button>
        </atlas-form>
    </atlas-layout>
</atlas-panel>
</body>
</html>