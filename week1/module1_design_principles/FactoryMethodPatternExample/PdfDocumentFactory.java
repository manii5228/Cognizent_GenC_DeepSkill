package FactoryMethodPatternExample;

/**
 * PdfDocumentFactory - Concrete Factory for PDF Documents
 */
public class PdfDocumentFactory extends DocumentFactory {

    @Override
    public Document createDocument() {
        System.out.println("[PdfDocumentFactory] Creating a PDF Document...");
        return new PdfDocument();
    }
}
