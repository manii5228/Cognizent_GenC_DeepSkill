package FactoryMethodPatternExample;

/**
 * Document - Abstract Base Interface
 *
 * All document types (Word, PDF, Excel) must implement this interface.
 */
public interface Document {

    /** Opens the document. */
    void open();

    /** Saves the document. */
    void save();

    /** Closes the document. */
    void close();

    /** Returns the document type as a readable string. */
    String getDocumentType();
}
