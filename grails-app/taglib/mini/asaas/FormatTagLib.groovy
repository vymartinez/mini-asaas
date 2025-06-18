package mini.asaas

class FormatTagLib {

    static namespace = "formatTagLib"

    static returnObjectForTags = [
        "formatAnyDate"
    ]

    def formatAnyDate = { Map attrs, Closure body ->
        return formatDate(date: attrs.date, format: "dd/MM/yyyy")
    }
}

