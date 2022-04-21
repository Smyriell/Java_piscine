package ex02;

public interface UsersList {

    int DEFAULT_SIZE = 10;

    int DEFAULT_SCALE = 2;



    void addUser(User user);

    User getUserById(int id) throws UserNotFoundException;

    User getUserByIndex(int index) throws UserNotFoundException;

    Integer getUsersSum();
}
