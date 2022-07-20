package ex04;

public class UserIdsGenerator {

    private static UserIdsGenerator usersIdsGenerator;
    private static int globalUserId = 0;

    public static UserIdsGenerator getUsersIdsGenerator() {
        if (usersIdsGenerator == null) {
            usersIdsGenerator = new UserIdsGenerator();
        }
        return usersIdsGenerator;
    }

    private UserIdsGenerator() {}

    public int generateID() {
        return ++globalUserId;
    }
}

