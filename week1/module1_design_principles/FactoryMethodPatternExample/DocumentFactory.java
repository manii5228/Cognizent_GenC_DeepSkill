package FactoryMethodPatternExample;

/**
 * DocumentFactory - Abstract Factory Class
 *
 * Declares the Factory Method createDocument() which subclasses must override.
 * Also defines a template method processDocument() that uses the factory method.
 */
public abstract class DocumentFactory {

    /**
     * Factory Method - to be implemented by each concrete factory.
     * @return a Document instance of the appropriate type
     */
    public abstract Document createDocument();

    /**
     * Template method that uses the factory method.
     * Demonstrates how the factory integrates with a workflow.
     */
    public void processDocument() {
        Document doc = createDocument();
        System.out.println("\n>> Processing: " + doc.getDocumentType());
        doc.open();
        doc.save();
        doc.close();
        System.out.println(">> Done processing: " + doc.getDocumentType());
    }
}
