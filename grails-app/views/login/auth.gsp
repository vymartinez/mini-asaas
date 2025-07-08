<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta name="layout" content="external"/>
    <title>Mini Asaas - Login</title>
</head>

<body>
<atlas-panel>
    <atlas-form action="${ postUrl }" method="post">
        <atlas-heading>Acesso ao Sistema</atlas-heading>

        <atlas-grid>
            <atlas-row>

                <atlas-col lg="12">
                    <atlas-masked-input
                        label="Email"
                        id="username"
                        name="username"
                        type="text"
                        placeholder="Email"
                        mask-alias="email"
                        required>
                    </atlas-masked-input>
                </atlas-col>

                <atlas-col lg="12">
                    <atlas-password-input
                        label="Senha"
                        id="password"
                        name="password"
                        placeholder="Senha"
                        required>
                    </atlas-password-input>
                </atlas-col>

                <atlas-col lg="12" style="text-align: center; margin-top: 20px;">
                    <atlas-button description="Entrar" submit block></atlas-button>
                </atlas-col>

            </atlas-row>
        </atlas-grid>

    </atlas-form>
</atlas-panel>

</body>
</html>