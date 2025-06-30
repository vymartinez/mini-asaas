package mini.asaas

class FormatTagLib {

    static namespace = "formatTagLib"

    def date = { Map attrs, Closure body ->
        out << g.formatDate(date: attrs.date, format: "dd/MM/yyyy")
    }

    def firstName = { Map attrs, Closure body ->
        out << (attrs.name as String).split(" ")[0]
    }
}