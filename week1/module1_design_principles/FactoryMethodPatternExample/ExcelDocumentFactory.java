package FactoryMethodPatternExample;

/**
 * ExcelDocumentFactory - Concrete Factory for Excel Documents
 */
public class ExcelDocumentFactory extends DocumentFactory {

    @Override
    public Document createDocument() {
        System.out.println("[ExcelDocumentFactory] Creating an Excel Document...");
        return new ExcelDocument();
    }
}
