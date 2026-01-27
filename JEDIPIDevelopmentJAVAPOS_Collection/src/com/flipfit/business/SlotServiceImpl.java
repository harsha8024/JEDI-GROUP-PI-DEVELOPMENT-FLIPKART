package com.flipfit.business;

import com.flipfit.bean.Slot;
import com.flipfit.dao.SlotDAO;
import com.flipfit.exception.RegistrationFailedException;

import java.time.LocalTime;
import java.util.List;

public class SlotServiceImpl implements SlotServiceInterface {

    private SlotDAO slotDAO;

    public SlotServiceImpl() {
        this.slotDAO = new SlotDAO();
    }

    @Override
    public void createSlot(String gymId, LocalTime startTime, LocalTime endTime, int capacity) throws RegistrationFailedException {
        String slotId = slotDAO.generateSlotId();

        Slot slot = new Slot();
        slot.setSlotId(slotId);
        slot.setGymId(gymId);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slot.setCapacity(capacity);
        slot.setAvailableSeats(capacity);

        if (slotDAO.saveSlot(slot)) {
            System.out.println("✓ Slot created successfully: " + slotId);
        } else {
            throw new RegistrationFailedException("Error: Failed to create slot for Gym ID: " + gymId);
        }
    }

    @Override
    public List<Slot> getSlotsForGym(String gymId) {
        return slotDAO.getSlotsByGymId(gymId);
    }

    @Override
    public void updateSlot(String slotId, int capacity) {
        Slot slot = slotDAO.getSlotById(slotId);

        if (slot != null) {
            int bookedSeats = slot.getCapacity() - slot.getAvailableSeats();
            slot.setCapacity(capacity);
            slot.setAvailableSeats(capacity - bookedSeats);

            if (slotDAO.updateSlot(slot)) {
                System.out.println("✓ Slot updated successfully: " + slotId);
            } else {
                System.err.println("Slot update failed.");
            }
        } else {
            System.out.println("Error: Slot not found.");
        }
    }

    @Override
    public void deleteSlot(String slotId) {
        if (slotDAO.deleteSlot(slotId)) {
            System.out.println("✓ Slot deleted successfully.");
        } else {
            System.out.println("Error: Slot not found or could not be deleted.");
        }
    }

    @Override
    public Slot getSlotById(String slotId) {
        return slotDAO.getSlotById(slotId);
    }
}
