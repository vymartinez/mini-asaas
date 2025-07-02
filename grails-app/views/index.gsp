<!doctype html>
<html lang="pt-BR">
<head>
    <meta name="layout" content="external"/>
    <title>Mini Asaas</title>
    <asset:stylesheet src="index.css"/>
</head>

<body>
<atlas-panel fluid class="panel">
    <atlas-layout gap="9" alignment="center" justify="space-around" fluid>
        <atlas-heading theme="primary" alignment="center">Gerenciamento de cobranças de forma rápida e fácil.</atlas-heading>
        <atlas-layout inline alignment="center" justify="space-between" gap="4">
            <atlas-layout gap="3">
                <atlas-button
                    href="${createLink(controller: "login", action: "auth")}"
                    description="Acessar minha conta"
                    block
                    pill
                >
                </atlas-button>
                <atlas-button
                    href="${createLink(controller: "onboarding", action: "createCustomer")}"
                    description="Cadastrar conta"
                    block
                    pill
                    white
                >
                </atlas-button>
            </atlas-layout>
            <atlas-illustration name="card-asaas-coin-arrow" size="xlg"></atlas-illustration>
        </atlas-layout>
    </atlas-layout>
</atlas-panel>
</body>
</html>