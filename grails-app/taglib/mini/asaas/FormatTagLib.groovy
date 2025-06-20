package mini.asaas

class FormatTagLib {

    static namespace = "formatTagLib"

    static returnObjectForTags = [
        "formatDate"
    ]

    def formatDate = { Map attrs, Closure body ->
        return formatDate(date: attrs.date, format: "dd/MM/yyyy")
    }
}

