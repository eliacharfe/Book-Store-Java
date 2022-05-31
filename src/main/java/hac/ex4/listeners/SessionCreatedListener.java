package hac.ex4.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.session.events.SessionCreatedEvent;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class SessionCreatedListener implements ApplicationListener<SessionCreatedEvent> {
//        ,ApplicationListener<SessionDestroyedEvent> {

    private final AtomicInteger counter = new AtomicInteger();

    private void updateSessionCounter(HttpSessionEvent e){
        //Let's set in the context
        e.getSession().getServletContext()
                .setAttribute("activeSession", counter.get());
        System.out.println("!!!! Total active session are " + counter.get());
    }

    @Override
    public void onApplicationEvent(SessionCreatedEvent sce) {
        counter.incrementAndGet();
        System.out.println("!!!! Total active session are " + counter.get());
    }

    //@Override
//    public void onApplicationEvent(SessionDestroyedEvent sessionDestroyedEvent) {
//        counter.decrementAndGet();
//        System.out.println("Total active session are {} " + counter.get());
//    }
}