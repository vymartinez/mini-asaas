package mini.asaas

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class PdfService {

    public byte[] renderPdfFromHtml(String html) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        PdfRendererBuilder builder = new PdfRendererBuilder()

        builder.useFastMode()
        builder.withHtmlContent(html, null)
        builder.toStream(outputStream)
        builder.run()

        return outputStream.toByteArray()
    }
}