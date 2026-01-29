// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Slot;
import com.flipfit.exception.RegistrationFailedException;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.SlotNotFoundException;
import com.flipfit.exception.SlotOperationException;

import java.time.LocalTime;
import java.util.List;

/**
 * The Interface SlotServiceInterface.
 *
 * @author team pi
 * @ClassName "SlotServiceInterface"
 */
public interface SlotServiceInterface {

    /**
     * Creates the slot.
     *
     * @param gymId     the gym id
     * @param startTime the start time
     * @param endTime   the end time
     * @param capacity  the capacity
     * @throws RegistrationFailedException the registration failed exception
     */
    void createSlot(String gymId, LocalTime startTime, LocalTime endTime, int capacity)
            throws RegistrationFailedException, InvalidInputException;

    /**
     * Gets the slots for gym.
     *
     * @param gymId the gym id
     * @return the slots for gym
     */
    List<Slot> getSlotsForGym(String gymId) throws InvalidInputException;

    /**
     * Update slot.
     *
     * @param slotId   the slot id
     * @param capacity the capacity
     */
    void updateSlot(String slotId, int capacity) throws InvalidInputException, SlotNotFoundException, SlotOperationException;

    /**
     * Delete slot.
     *
     * @param slotId the slot id
     */
    void deleteSlot(String slotId) throws InvalidInputException, SlotNotFoundException;

    /**
     * Gets the slot by id.
     *
     * @param slotId the slot id
     * @return the slot by id
     */
    Slot getSlotById(String slotId) throws InvalidInputException, SlotNotFoundException;
}
