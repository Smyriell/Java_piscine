package ex02;

public class UserNotFoundException extends RuntimeException {
    @Override
    public String toString() {
        return ("User is not found");
    }
}
