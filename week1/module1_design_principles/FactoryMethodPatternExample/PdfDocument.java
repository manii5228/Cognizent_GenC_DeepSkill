package FactoryMethodPatternExample;

/**
 * PdfDocument - Concrete Document Implementation for PDF files (.pdf)
 */
public class PdfDocument implements Document {

    @Override
    public void open() {
        System.out.println("[PdfDocument] Opening PDF document (.pdf)...");
    }

    @Override
    public void save() {
        System.out.println("[PdfDocument] Saving PDF document (.pdf)...");
    }

    @Override
    public void close() {
        System.out.println("[PdfDocument] Closing PDF document (.pdf)...");
    }

    @Override
    public String getDocumentType() {
        return "PDF Document (.pdf)";
    }
}
