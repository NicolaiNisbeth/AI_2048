import action.Action;
import action.UpSwipe;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class UpSwipeTest extends ActionTest {

    @Override
    protected Action createAction() {
        return new UpSwipe();
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
