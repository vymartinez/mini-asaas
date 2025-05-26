<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <asset:stylesheet src="bootstrap.css"/>
    <asset:stylesheet src="register.css"/>
    <title>Mini Asaas</title>
</head>

<body>
    <main class="flex align-items-center justify-content-center w-100 h-screen bg-asaas">
        <section class="form bg-light p-5">
            <h2 class="h2 text-center">
                Cadastro de Usuários
            </h2>
            <form class="flex flex-column">
                <label for="nome" class="col-form-label">
                    Nome
                    <input id="nome" name="nome" type="text" placeholder="Nome" class="form-control">
                </label>
                <div class="flex inline">
                    <label for="cpf" class="col-form-label w-100">
                        CPF
                        <input id="cpf" name="cpf" type="text" placeholder="CPF" class="form-control">
                    </label>
                    <label for="telefone" class="col-form-label w-100">
                        Telefone
                        <input id="telefone" name="telefone" type="text" placeholder="Telefone" class="form-control">
                    </label>
                </div>
                <div class="flex inline">
                    <label for="cep" class="col-form-label w-100">
                        CEP
                        <input id="cep" maxlength="8" name="cep" type="text" placeholder="CEP" class="form-control" onclose="getCep(cep)">
                    </label>
                    <label for="bairro" class="col-form-label w-100">
                        Bairro
                        <input id="bairro" name="bairro" type="text" disabled placeholder="Bairro" class="form-control">
                    </label>
                </div>
                <div class="flex inline">
                    <label for="logradouro" class="col-form-label w-100">
                        Logradouro
                        <input id="logradouro" name="logradouro" type="text" placeholder="Logradouro" disabled class="form-control">
                    </label>
                    <label for="uf" class="col-form-label">
                        UF
                        <input id="uf" name="uf" type="text" placeholder="UF" disabled class="form-control">
                    </label>
                </div>
                <label for="complemento" class="col-form-label w-100 mb-3">
                    Complemento (Opcional)
                    <input id="complemento" name="complemento" type="text" disabled placeholder="Complemento" class="form-control">
                </label>
                <button class="btn text-white bg-asaas mb-5">
                    Fazer login
                </button>
            </form>
        </section>
    </main>
</body>

<asset:javascript src="script.js"/>

</html>
