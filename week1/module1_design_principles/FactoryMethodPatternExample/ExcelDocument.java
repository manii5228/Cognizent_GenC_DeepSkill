package FactoryMethodPatternExample;

/**
 * ExcelDocument - Concrete Document Implementation for Excel files (.xlsx)
 */
public class ExcelDocument implements Document {

    @Override
    public void open() {
        System.out.println("[ExcelDocument] Opening Excel document (.xlsx)...");
    }

    @Override
    public void save() {
        System.out.println("[ExcelDocument] Saving Excel document (.xlsx)...");
    }

    @Override
    public void close() {
        System.out.println("[ExcelDocument] Closing Excel document (.xlsx)...");
    }

    @Override
    public String getDocumentType() {
        return "Excel Document (.xlsx)";
    }
}
