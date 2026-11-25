package utils;

import java.util.UUID;
import payloads.User;

public class UserFactory {

    public static User createUserWithInvalidEmail() {

        return new User(
                System.currentTimeMillis(),
                "testuser_" + UUID.randomUUID().toString().substring(0, 8),
                "Test",
                "User",
                "invalid-email",
                "Test@123",
                "9876543210",
                1
        );
    }
}
