import action.Action;
import action.DownSwipe;

import java.util.Arrays;

import static org.junit.Assert.*;

public class DownSwipeTest extends ActionTest {

    @Override
    protected Action createAction() {
        return new DownSwipe();
    }

    @org.junit.Test
    public void validateAction() {
        super.validateAction();

        // rows
        assertEquals(Arrays.stream(initialSums.getFirst()).sum(), Arrays.stream(updatedSums.getFirst()).sum());

        // columns
        for (int i = 0; i < initialSums.getSecond().length; i++) {
            assertEquals(initialSums.getSecond()[i], updatedSums.getSecond()[i]);
        }
    }
}