package com.flipfit.business;

import com.flipfit.bean.Slot;
import com.flipfit.database.LocalFileDatabase;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class SlotServiceImpl implements SlotServiceInterface {

    @Override
    public void createSlot(String gymId, LocalTime startTime, LocalTime endTime, int capacity) {
        String slotId = LocalFileDatabase.generateSlotId();
        
        Slot slot = new Slot();
        slot.setSlotId(slotId);
        slot.setGymId(gymId);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slot.setCapacity(capacity);
        slot.setAvailableSeats(capacity);
        
        LocalFileDatabase.saveSlot(slot);
        System.out.println("✓ Slot created successfully: " + slotId + 
                           " (" + startTime + " - " + endTime + ")");
    }

    @Override
    public List<Slot> getSlotsForGym(String gymId) {
        return LocalFileDatabase.loadSlots().stream()
            .filter(slot -> slot.getGymId().equals(gymId))
            .collect(Collectors.toList());
    }

    @Override
    public void updateSlot(String slotId, int capacity) {
        List<Slot> slots = LocalFileDatabase.loadSlots();
        
        for (Slot slot : slots) {
            if (slot.getSlotId().equals(slotId)) {
                int bookedSeats = slot.getCapacity() - slot.getAvailableSeats();
                slot.setCapacity(capacity);
                slot.setAvailableSeats(capacity - bookedSeats);
                
                LocalFileDatabase.updateSlot(slot);
                System.out.println("✓ Slot updated successfully: " + slotId);
                return;
            }
        }
        
        System.out.println("Error: Slot not found.");
    }

    @Override
    public void deleteSlot(String slotId) {
        List<Slot> slots = LocalFileDatabase.loadSlots();
        boolean removed = slots.removeIf(slot -> slot.getSlotId().equals(slotId));
        
        if (removed) {
            try {
                java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter("database/slots.txt"));
                for (Slot slot : slots) {
                    writer.println(slot.getSlotId() + "|" + slot.getGymId() + "|" + 
                                   slot.getStartTime() + "|" + slot.getEndTime() + "|" + 
                                   slot.getCapacity() + "|" + slot.getAvailableSeats());
                }
                writer.close();
                System.out.println("✓ Slot deleted successfully.");
            } catch (Exception e) {
                System.out.println("Error deleting slot: " + e.getMessage());
            }
        } else {
            System.out.println("Error: Slot not found.");
        }
    }

    @Override
    public Slot getSlotById(String slotId) {
        return LocalFileDatabase.loadSlots().stream()
            .filter(slot -> slot.getSlotId().equals(slotId))
            .findFirst()
            .orElse(null);
    }
}
