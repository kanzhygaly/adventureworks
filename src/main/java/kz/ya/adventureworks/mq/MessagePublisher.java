package kz.ya.adventureworks.mq;

/**
 *
 * @author yerlana
 */
public interface MessagePublisher {
    
    void publish(final String message);
}
