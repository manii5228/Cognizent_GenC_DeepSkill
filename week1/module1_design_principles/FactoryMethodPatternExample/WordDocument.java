package FactoryMethodPatternExample;

/**
 * WordDocument - Concrete Document Implementation for Word files (.docx)
 */
public class WordDocument implements Document {

    @Override
    public void open() {
        System.out.println("[WordDocument] Opening Word document (.docx)...");
    }

    @Override
    public void save() {
        System.out.println("[WordDocument] Saving Word document (.docx)...");
    }

    @Override
    public void close() {
        System.out.println("[WordDocument] Closing Word document (.docx)...");
    }

    @Override
    public String getDocumentType() {
        return "Word Document (.docx)";
    }
}
