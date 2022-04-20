package ex02;

public final class UserIdsGenerator {

    private static UserIdsGenerator usersIdsGenerator;
    private static int globalUserId = 0;

    public static synchronized UserIdsGenerator getUsersIdsGenerator() {
        if (usersIdsGenerator == null) {
            usersIdsGenerator = new UserIdsGenerator();
        }
        return usersIdsGenerator;
    }

    private UserIdsGenerator() { }

    public int generateID() {
        return ++globalUserId;
    }
}
