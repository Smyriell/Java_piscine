package ex02;

public class UsersArrayList implements UsersList {

    private int size = 0;
    private int capacity = DEFAULT_SIZE;

    private User[] usersList = new User[DEFAULT_SIZE];

    @Override
    public void addUser(User user) throws NullPointerException {
        if (size == capacity - 1)
            usersList = reallocUsersList(usersList);
        if (usersList != null) {
            usersList[size] = user;
            size++;
        }
        else
            throw new NullPointerException();
    }

    private User[] reallocUsersList(User[] usersList) {
        this.capacity = (int) (1.5 * (float)this.capacity);
        User temp[] = new User[capacity];
        for (int i = 0; i < size; i++)
            temp[i] = usersList[i];
        return temp;
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        for (int i = 0; i < size; i++) {
            if (usersList[i].getUserId() == id)
                return usersList[i];
        }
        throw new UserNotFoundException();
    }

    @Override
    public Integer getUsersSum() {
        return size;
    }

    @Override
    public User getUserByIndex(int index) throws UserNotFoundException, ArrayIndexOutOfBoundsException {
        if (index < 0)
            throw new ArrayIndexOutOfBoundsException();
        else if (usersList[index] == null)
            throw new UserNotFoundException();
        return usersList[index];
    }
}
