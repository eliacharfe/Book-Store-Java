package hac.ex4.listeners;

import org.springframework.stereotype.Component;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * a @WebListener class for session count
 * the @Component is needed only if we INJECT beans
 */
@Component
@WebListener
public class SessionListenerCounter implements HttpSessionListener {

    /** Count active sessions. */
    private final AtomicInteger activeSessions;

    /** Constructor of session listener counter. */
    public SessionListenerCounter() {
        super();
        activeSessions = new AtomicInteger();
    }

    /**
     * Detect when a new session is created.
     * @param event - session event.
     */
    public void sessionCreated(final HttpSessionEvent event) {
        activeSessions.incrementAndGet();
        System.out.println("++ Total active session are: " + activeSessions.get());

    }

    /**
     * Detect when a new session is destroyed.
     * @param event - session event.
     */
    public void sessionDestroyed(final HttpSessionEvent event) {
        activeSessions.decrementAndGet();
        System.out.println("-- Total active session are: " + activeSessions.get());
    }
}
