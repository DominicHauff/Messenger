package app.messages;

import java.time.LocalDateTime;
import java.util.Objects;

public record Message(LocalDateTime timeStamp, String sender, String content, boolean read) implements Comparable<Message> {

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Message m = (Message) o;
        return this.hashCode() == m.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStamp, sender, content, read);
    }

    @Override
    public int compareTo(Message o) {
        return this.timeStamp.compareTo(o.timeStamp);
    }
}
