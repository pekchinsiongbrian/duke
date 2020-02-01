package duke.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void setTestTodo() {
        assertEquals("[T][" + "\u2718" +"] Play computer",
                new Todo("Play computer").toString());
    }
}
