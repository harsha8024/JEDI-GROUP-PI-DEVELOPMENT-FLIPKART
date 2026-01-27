package com.flipfit.business;

import com.flipfit.bean.Slot;
import com.flipfit.exception.RegistrationFailedException;

import java.time.LocalTime;
import java.util.List;

public interface SlotServiceInterface {
    void createSlot(String gymId, LocalTime startTime, LocalTime endTime, int capacity) throws RegistrationFailedException;
    List<Slot> getSlotsForGym(String gymId);
    void updateSlot(String slotId, int capacity);
    void deleteSlot(String slotId);
    Slot getSlotById(String slotId);
}
