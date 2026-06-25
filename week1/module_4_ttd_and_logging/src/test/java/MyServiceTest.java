import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * MyServiceTest.java
 * Mockito Exercise 1: Mocking and Stubbing
 * Mockito Exercise 2: Verifying Interactions
 *
 * MOCKING:   Creating a fake (mock) object that replaces a real dependency.
 *            The mock records all method calls but does nothing by default.
 *
 * STUBBING:  Configuring the mock to return specific values when certain
 *            methods are called — using when(...).thenReturn(...).
 *
 * VERIFYING: Asserting that specific methods were called (or not called)
 *            on the mock, with specific arguments — using verify(...).
 *
 * Why mock ExternalApi?
 *   - Avoids real network calls (fast, reliable tests)
 *   - Tests MyService logic in complete isolation
 *   - Makes tests deterministic regardless of external system state
 */
@DisplayName("MyService — Mockito Tests")
public class MyServiceTest {

    // ================================================================== //
    //  MOCKITO EXERCISE 1: Mocking and Stubbing
    // ================================================================== //

    @Test
    @DisplayName("Mockito Ex1: Mock ExternalApi and stub getData() to return predefined value")
    public void testExternalApi() {
        // ARRANGE — create a mock object for ExternalApi
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);

        // STUB — configure the mock: when getData() is called, return "Mock Data"
        when(mockApi.getData()).thenReturn("Mock Data");

        // Inject the mock into MyService via constructor injection
        MyService service = new MyService(mockApi);

        // ACT — call the method under test
        String result = service.fetchData();

        // ASSERT — verify that the returned value matches the stubbed response
        assertEquals("Mock Data", result,
                "fetchData() should return the stubbed value 'Mock Data'");

        System.out.println("[Mockito Ex1] testExternalApi PASSED. Result: " + result);
    }

    @Test
    @DisplayName("Mockito Ex1: Stub postData() to return true on success")
    public void testSendDataSuccess() {
        // ARRANGE
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        when(mockApi.postData("payload-123")).thenReturn(true);

        MyService service = new MyService(mockApi);

        // ACT
        boolean success = service.sendData("payload-123");

        // ASSERT
        assertTrue(success, "sendData() should return true for a successful post");
        System.out.println("[Mockito Ex1] testSendDataSuccess PASSED.");
    }

    @Test
    @DisplayName("Mockito Ex1: Stub getData() to throw an exception (error simulation)")
    public void testExternalApiThrowsException() {
        // ARRANGE
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);

        // Stub to throw a RuntimeException (simulates API downtime)
        when(mockApi.getData()).thenThrow(new RuntimeException("API is down"));

        MyService service = new MyService(mockApi);

        // ACT + ASSERT — confirm the exception propagates
        org.junit.jupiter.api.Assertions.assertThrows(
            RuntimeException.class,
            () -> service.fetchData(),
            "fetchData() should propagate RuntimeException from the API"
        );
        System.out.println("[Mockito Ex1] testExternalApiThrowsException PASSED.");
    }

    // ================================================================== //
    //  MOCKITO EXERCISE 2: Verifying Interactions
    // ================================================================== //

    @Test
    @DisplayName("Mockito Ex2: Verify that getData() was called exactly once")
    public void testVerifyInteraction() {
        // ARRANGE
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        MyService service = new MyService(mockApi);

        // ACT
        service.fetchData();

        // VERIFY — assert that mockApi.getData() was called exactly 1 time
        verify(mockApi).getData();          // shorthand for verify(mockApi, times(1))

        System.out.println("[Mockito Ex2] testVerifyInteraction PASSED — getData() called once.");
    }

    @Test
    @DisplayName("Mockito Ex2: Verify getData() is called exactly N times")
    public void testVerifyInteractionCount() {
        // ARRANGE
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        when(mockApi.getData()).thenReturn("Data");
        MyService service = new MyService(mockApi);

        // ACT — call fetchData 3 times
        service.fetchData();
        service.fetchData();
        service.fetchData();

        // VERIFY — getData() must have been called exactly 3 times
        verify(mockApi, times(3)).getData();

        System.out.println("[Mockito Ex2] testVerifyInteractionCount PASSED — getData() called 3 times.");
    }

    @Test
    @DisplayName("Mockito Ex2: Verify postData() was called with specific argument")
    public void testVerifyPostDataWithSpecificArgument() {
        // ARRANGE
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        when(mockApi.postData(anyString())).thenReturn(true);
        MyService service = new MyService(mockApi);

        // ACT
        service.sendData("order-data-456");

        // VERIFY — postData() was called with exactly "order-data-456"
        verify(mockApi).postData("order-data-456");

        System.out.println("[Mockito Ex2] testVerifyPostDataWithSpecificArgument PASSED.");
    }

    @Test
    @DisplayName("Mockito Ex2: Verify no more interactions occurred on the mock")
    public void testVerifyNoMoreInteractions() {
        // ARRANGE
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        when(mockApi.getData()).thenReturn("Data");
        MyService service = new MyService(mockApi);

        // ACT
        service.fetchData();

        // VERIFY
        verify(mockApi).getData();                     // getData() called once
        verifyNoMoreInteractions(mockApi);             // no other methods called

        System.out.println("[Mockito Ex2] testVerifyNoMoreInteractions PASSED.");
    }
}
