package utils;

import java.util.UUID;

import payloads.Pet;

public class PetsFactory {

    public static Pet createPet() {

        long petId = Math.abs(UUID.randomUUID().getMostSignificantBits());

        String petName = "Rocky_" +
                UUID.randomUUID().toString().substring(0, 8);

        return new Pet(petId, petName, "available");
    }

    public static Pet updatePetStatus(Pet pet, String status) {

        pet.setStatus(status);

        return pet;
    }
}
