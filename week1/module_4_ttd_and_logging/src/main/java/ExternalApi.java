/**
 * ExternalApi.java
 * Interface representing an external API dependency.
 * This is the interface we will MOCK in Mockito exercises to avoid
 * making real network calls during unit tests.
 */
public interface ExternalApi {

    /**
     * Fetches data from the external API.
     * @return the data string returned by the API
     */
    String getData();

    /**
     * Posts data to the external API.
     * @param payload the data to send
     * @return true if the post was successful, false otherwise
     */
    boolean postData(String payload);
}
