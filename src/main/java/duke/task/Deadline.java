package duke.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Task object of type Deadline
 */
public class Deadline extends Task {
    protected LocalDate byDate;
    protected LocalTime byTime;

    public Deadline(String description, LocalDate byDate) {
        super(description);
        this.byDate = byDate;
    }

    public Deadline(String description, LocalDate byDate, LocalTime byTime) {
        super(description);
        this.byDate = byDate;
        this.byTime = byTime;
    }

    /**
     * Stringifies the Deadline object to a default string representation
     *
     * @return Default string representation of the Deadline object
     */
    @Override
    public String toString() {
        if (this.byTime == null) {
            return "[D]" + super.toString() + " (by: " +
                    this.byDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ")";
        } else {
            return "[D]" + super.toString() + " (by: " +
                    this.byDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) +
                    ", " + this.byTime.format(DateTimeFormatter.ofPattern("HH:mm")) + ")";
        }
    }

    /**
     * Stringifies the Deadline object to a special string representation which includes the number of days to
     * the deadline (if deadline has not passed), or the number of days since the deadline (if deadline has passed)
     *
     * @return Formatted string representation of the Deadline object
     */
    public String displayDeadline() {
        try {
            if (this.byTime == null) {
                return "[D]" + super.toString() + " (by: " +
                        this.byDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ") - " +
                        LocalDate.now().datesUntil(this.byDate).count() + " day(s) to go!";
            } else {
                return "[D]" + super.toString() + " (by: " +
                        this.byDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) +
                        ", " + this.byTime.format(DateTimeFormatter.ofPattern("HH:mm")) + ") - " +
                        LocalDate.now().datesUntil(this.byDate).count() + " day(s) to go!";
            }
        } catch (IllegalArgumentException iae) {
            if (this.byTime == null) {
                return "[D]" + super.toString() + " (by: " +
                        this.byDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ") - " +
                        "it's " + this.byDate.datesUntil(LocalDate.now()).count() + " day(s) past the deadline!";
            } else {
                return "[D]" + super.toString() + " (by: " +
                        this.byDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) +
                        ", " + this.byTime.format(DateTimeFormatter.ofPattern("HH:mm")) + ") - " +
                        "it's " + this.byDate.datesUntil(LocalDate.now()).count() + " day(s) past the deadline!";
            }
        }
    }
}
