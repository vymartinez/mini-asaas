package mini.asaas.utils

class FormatterTagLib {
    def formatAnyDate = { attrs, body ->
        out << formatDate(date: attrs.date, format: "dd/MM/yyyy")
    }
}
