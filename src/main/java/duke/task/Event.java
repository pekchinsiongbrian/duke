package duke.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDate atDate;
    protected LocalTime atTimeStart;
    protected LocalTime atTimeEnd;

    public Event(String description, LocalDate atDate) {
        super(description);
        this.atDate = atDate;
    }

    public Event(String description, LocalDate atDate, LocalTime atTimeStart) {
        super(description);
        this.atDate = atDate;
        this.atTimeStart = atTimeStart;
    }

    public Event(String description, LocalDate atDate, LocalTime atTimeStart, LocalTime atTimeEnd) {
        super(description);
        this.atDate = atDate;
        this.atTimeStart = atTimeStart;
        this.atTimeEnd = atTimeEnd;
    }

    @Override
    public String toString() {
        if (this.atTimeStart == null) {
            return "[E]" + super.toString() + " (at: " +
                    this.atDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ")";
        } else {
            if (this.atTimeEnd == null) {
                return "[E]" + super.toString() + " (at: " +
                        this.atDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) +
                        ", " + this.atTimeStart.format(DateTimeFormatter.ofPattern("HH:mm")) + ")";
            } else {
                return "[E]" + super.toString() + " (at: " +
                        this.atDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) +
                        ", " + this.atTimeStart.format(DateTimeFormatter.ofPattern("HH:mm")) +
                        " to " + this.atTimeEnd.format(DateTimeFormatter.ofPattern("HH:mm")) + ")";
            }
        }
    }

    public String displayEventTime() {
        try {
            if (this.atTimeStart == null) {
                return "[E]" + super.toString() + " (at: " +
                        this.atDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ") - " +
                        LocalDate.now().datesUntil(this.atDate).count() + " day(s) to go!";
            } else {
                if (this.atTimeEnd == null) {
                    return "[E]" + super.toString() + " (at: " +
                            this.atDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) +
                            ", " + this.atTimeStart.format(DateTimeFormatter.ofPattern("HH:mm")) + ") - " +
                            LocalDate.now().datesUntil(this.atDate).count() + " day(s) to go!";
                } else {
                    return "[E]" + super.toString() + " (at: " +
                            this.atDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) +
                            ", " + this.atTimeStart.format(DateTimeFormatter.ofPattern("HH:mm")) +
                            " to " + this.atTimeEnd.format(DateTimeFormatter.ofPattern("HH:mm")) + ") - " +
                            LocalDate.now().datesUntil(this.atDate).count() + " day(s) to go!";
                }
            }
        } catch (IllegalArgumentException iae) {
            if (this.atTimeStart == null) {
                return "[E]" + super.toString() + " (at: " +
                        this.atDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ") - " +
                        "it's " + this.atDate.datesUntil(LocalDate.now()).count() + " day(s) past the event!";
            } else {
                if (this.atTimeEnd == null) {
                    return "[E]" + super.toString() + " (at: " +
                            this.atDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) +
                            ", " + this.atTimeStart.format(DateTimeFormatter.ofPattern("HH:mm")) + ") - " +
                            "it's " + this.atDate.datesUntil(LocalDate.now()).count() + " day(s) past the event!";
                } else {
                    return "[E]" + super.toString() + " (at: " +
                            this.atDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) +
                            ", " + this.atTimeStart.format(DateTimeFormatter.ofPattern("HH:mm")) +
                            " to " + this.atTimeEnd.format(DateTimeFormatter.ofPattern("HH:mm")) + ") - " +
                            "it's " + this.atDate.datesUntil(LocalDate.now()).count() + " day(s) past the event!";
                }
            }
        }
    }
}
