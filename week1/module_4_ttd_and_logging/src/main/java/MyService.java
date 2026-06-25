/**
 * MyService.java
 * A service class that depends on ExternalApi.
 *
 * In real production code this would call an actual REST endpoint.
 * In tests, the ExternalApi dependency is MOCKED via Mockito so that
 * MyService can be tested in isolation, without any network calls.
 */
public class MyService {

    // Dependency injected through the constructor (Constructor Injection)
    private final ExternalApi externalApi;

    /**
     * Constructor injection — allows Mockito to pass a mock ExternalApi in tests.
     * @param externalApi the API client to use
     */
    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    /**
     * Fetches data from the external API.
     * Delegates directly to ExternalApi.getData().
     * @return the data string from the API
     */
    public String fetchData() {
        return externalApi.getData();
    }

    /**
     * Sends data to the external API.
     * @param payload data to send
     * @return true if successful
     */
    public boolean sendData(String payload) {
        return externalApi.postData(payload);
    }
}
