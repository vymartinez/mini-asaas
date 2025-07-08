<!doctype html>
<html lang="pt-BR">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link
        rel="stylesheet"
        href="https://atlas.asaas.com/v26.2.0/atlas.css"
        crossorigin="anonymous">
    <script
        defer
        src="https://atlas.asaas.com/v26.2.0/atlas.js"
        crossorigin="anonymous"
    ></script>
    <link
        rel="stylesheet"
        href="https://atlas.asaas.com/reset.css"
        crossorigin="anonymous">
    <asset:stylesheet src="general.css"/>
    <g:layoutHead/>
</head>

<body class="has-atlas">
    <atlas-screen fullscreen>
        <div class="bg-asaas">
            <atlas-page container fluid-content >
                <atlas-page-content slot="content" fluid>
                    <g:render template="/templates/alert" />
                    <g:layoutBody />
                </atlas-page-content>
            </atlas-page>
        </div>
    </atlas-screen>
    <asset:javascript src="application.js"/>
</body>
</html>