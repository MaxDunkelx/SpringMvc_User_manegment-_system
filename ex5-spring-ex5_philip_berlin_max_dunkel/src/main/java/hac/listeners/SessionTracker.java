package hac.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Tracks the number of currently active sessions and presents the count when changes occur.
 */
@WebListener
public class SessionTracker implements HttpSessionListener {
    private final AtomicInteger activeSessions;

    /**
     * Constructor - initializes the number of active sessions to 0.
     */
    public SessionTracker(){
        super();
        activeSessions = new AtomicInteger();
        printActiveSessions();
    }

    /**
     * Increments the session counter and prints the number of active sessions.
     * @param event The event of session creation.
     */
    public void sessionCreated(final HttpSessionEvent event){
        activeSessions.incrementAndGet();
        printActiveSessions();
    }

    /**
     * Decrements the session counter and prints the number of active sessions.
     * @param event The event of session destruction.
     */
    public void sessionDestroyed(final HttpSessionEvent event){
        activeSessions.decrementAndGet();
        printActiveSessions();
    }

    /**
     * Prints the current number of active sessions.
     */
    private void printActiveSessions(){
        System.out.println("Active Sessions: " + activeSessions.get());
    }

}
