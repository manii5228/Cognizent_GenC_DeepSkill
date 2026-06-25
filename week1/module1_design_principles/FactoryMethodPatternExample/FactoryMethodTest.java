package FactoryMethodPatternExample;

/**
 * FactoryMethodTest - Test Class for Factory Method Pattern
 *
 * Demonstrates creating different document types using their respective factories,
 * without the client knowing which concrete class is being instantiated.
 */
public class FactoryMethodTest {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("  Factory Method Pattern - Document Test");
        System.out.println("========================================");

        // Test 1: Create documents using individual factories
        System.out.println("\n--- Test 1: Direct Factory Usage ---");

        DocumentFactory wordFactory  = new WordDocumentFactory();
        DocumentFactory pdfFactory   = new PdfDocumentFactory();
        DocumentFactory excelFactory = new ExcelDocumentFactory();

        Document wordDoc  = wordFactory.createDocument();
        Document pdfDoc   = pdfFactory.createDocument();
        Document excelDoc = excelFactory.createDocument();

        System.out.println("\nCreated Documents:");
        System.out.println("  - " + wordDoc.getDocumentType());
        System.out.println("  - " + pdfDoc.getDocumentType());
        System.out.println("  - " + excelDoc.getDocumentType());

        // Test 2: Use the template method processDocument()
        System.out.println("\n--- Test 2: Processing Documents via Factory ---");
        wordFactory.processDocument();
        pdfFactory.processDocument();
        excelFactory.processDocument();

        // Test 3: Polymorphic usage — client works with Document interface only
        System.out.println("\n--- Test 3: Polymorphic Document Operations ---");
        Document[] docs = {
            new WordDocumentFactory().createDocument(),
            new PdfDocumentFactory().createDocument(),
            new ExcelDocumentFactory().createDocument()
        };

        for (Document doc : docs) {
            System.out.println("\nHandling -> " + doc.getDocumentType());
            doc.open();
            doc.save();
            doc.close();
        }

        System.out.println("\n[RESULT] Factory Method Pattern implemented successfully!");
        System.out.println("========================================");
    }
}
