// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Slot;
import com.flipfit.dao.SlotDAO;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.SlotNotFoundException;
import com.flipfit.exception.SlotOperationException;

import java.time.LocalTime;
import java.util.List;

/**
 * The Class SlotServiceImpl.
 *
 * @author team pi
 * @ClassName "SlotServiceImpl"
 */
public class SlotServiceImpl implements SlotServiceInterface {

    /** The slot DAO. */
    private SlotDAO slotDAO;

    /**
     * Instantiates a new slot service impl.
     */
    public SlotServiceImpl() {
        this.slotDAO = new SlotDAO();
    }

    /**
     * Creates the slot.
     *
     * @param gymId     the gym id
     * @param startTime the start time
     * @param endTime   the end time
     * @param capacity  the capacity
     * @throws RegistrationFailedException the registration failed exception
     */
    @Override
    public void createSlot(String gymId, LocalTime startTime, LocalTime endTime, int capacity)
            throws RegistrationFailedException, InvalidInputException {
        if (gymId == null || gymId.isBlank()) {
            throw new InvalidInputException("Gym ID must be provided.");
        }
        if (startTime == null || endTime == null || !startTime.isBefore(endTime)) {
            throw new InvalidInputException("Start time must be before end time.");
        }
        if (capacity <= 0) {
            throw new InvalidInputException("Capacity must be greater than zero.");
        }

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

    /**
     * Gets the slots for gym.
     *
     * @param gymId the gym id
     * @return the slots for gym
     */
    @Override
    public List<Slot> getSlotsForGym(String gymId) throws InvalidInputException {
        if (gymId == null || gymId.isBlank()) throw new InvalidInputException("Gym ID must be provided.");
        return slotDAO.getSlotsByGymId(gymId);
    }

    /**
     * Update slot.
     *
     * @param slotId   the slot id
     * @param capacity the capacity
     */
    @Override
    public void updateSlot(String slotId, int capacity) throws InvalidInputException, SlotNotFoundException, SlotOperationException {
        if (slotId == null || slotId.isBlank()) throw new InvalidInputException("Slot ID must be provided.");
        if (capacity <= 0) throw new InvalidInputException("Capacity must be greater than zero.");

        Slot slot = slotDAO.getSlotById(slotId);

        if (slot != null) {
            int bookedSeats = slot.getCapacity() - slot.getAvailableSeats();
            slot.setCapacity(capacity);
            slot.setAvailableSeats(capacity - bookedSeats);

            if (slotDAO.updateSlot(slot)) {
                System.out.println("✓ Slot updated successfully: " + slotId);
            } else {
                throw new SlotOperationException("Slot update failed.");
            }
        } else {
            throw new SlotNotFoundException("Error: Slot not found.");
        }
    }

    /**
     * Delete slot.
     *
     * @param slotId the slot id
     */
    @Override
    public void deleteSlot(String slotId) throws InvalidInputException, SlotNotFoundException {
        if (slotId == null || slotId.isBlank()) throw new InvalidInputException("Slot ID must be provided.");
        if (slotDAO.deleteSlot(slotId)) {
            System.out.println("✓ Slot deleted successfully.");
        } else {
            throw new SlotNotFoundException("Error: Slot not found or could not be deleted.");
        }
    }

    /**
     * Gets the slot by id.
     *
     * @param slotId the slot id
     * @return the slot by id
     */
    @Override
    public Slot getSlotById(String slotId) throws InvalidInputException, SlotNotFoundException {
        if (slotId == null || slotId.isBlank()) throw new InvalidInputException("Slot ID must be provided.");
        Slot slot = slotDAO.getSlotById(slotId);
        if (slot == null) throw new SlotNotFoundException("Slot not found for id: " + slotId);
        return slot;
    }
}
