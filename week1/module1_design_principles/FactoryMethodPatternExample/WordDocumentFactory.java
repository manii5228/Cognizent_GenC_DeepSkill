package FactoryMethodPatternExample;

/**
 * WordDocumentFactory - Concrete Factory for Word Documents
 */
public class WordDocumentFactory extends DocumentFactory {

    @Override
    public Document createDocument() {
        System.out.println("[WordDocumentFactory] Creating a Word Document...");
        return new WordDocument();
    }
}
