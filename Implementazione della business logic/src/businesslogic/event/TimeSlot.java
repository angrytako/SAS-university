package businesslogic.event;

import businesslogic.UseCaseLogicException;

public class TimeSlot {
    private int slot;


    public TimeSlot(int slot) throws UseCaseLogicException {
        if (slot < 0 && slot > 23) {
            throw new UseCaseLogicException("Slot is wrong");
        }
        this.slot = slot;
    }

    @Override
    public String toString() {
        return "slot: " + (this.slot + 1);
    }
}
