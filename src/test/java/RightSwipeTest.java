import action.Action;
import action.RightSwipe;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class RightSwipeTest extends ActionTest {

    @Override
    protected Action createAction() {
        return new RightSwipe();
    }

    @org.junit.Test
    public void validateAction() {
        super.validateAction();

        // rows
        for (int i = 0; i < initialSums.getSecond().length; i++) {
            assertEquals(initialSums.getFirst()[i], updatedSums.getFirst()[i]);
        }

        // columns
        assertEquals(Arrays.stream(initialSums.getSecond()).sum(), Arrays.stream(updatedSums.getSecond()).sum());
    }
}
