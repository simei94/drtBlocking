package events;

import org.matsim.core.events.handler.EventHandler;

public interface DrtBlockingRequestRejectedEvent1Handler extends EventHandler {
    void handleEvent(DrtBlockingRequestRejectedEvent1 event);
}
