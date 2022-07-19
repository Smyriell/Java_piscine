package ex02;

public class UsersArrayList implements UsersList {

    private static final int DEFAULT_SIZE = 10;
    private static final double CAP_MULTIPLIER = 1.5;

    private  User[] usersArr;
    private int size;
    private int capacity;

    public UsersArrayList() {
        usersArr = new User[DEFAULT_SIZE];
        size = 0;
        capacity = DEFAULT_SIZE;
    }


    @Override
    public void addUser(User newUser) {
        int newUserID = newUser.getUserId();

        for (int i = 0; i < size; i++) {
            if (usersArr[i].getUserId() == newUserID) {
                throw new UserExistsException("Exception: User with ID = " + newUser.getUserId()
                                                  + " already exists in the list: \n" + newUser.toString());
            }
        }

        if (size == capacity)
            reallocUsersArr();
        usersArr[size] = newUser;
        ++size;
        System.out.println("User " + newUser.getUserName() + " was successfully added to the list!");
    }

    @Override
    public User retrieveUserByID(int id) {
        int i = 0;

        for (; i < size; i++) {
            if (usersArr[i].getUserId() == id) {
                return usersArr[i];
            }
        }
        throw new UserNotFoundException("Exception: There is no user with id = " + id + " in the list");
    }

    @Override
    public User retrieveUserByIndex(int index) {
        if (index >= size) {
            throw new IndexNotFoundException("Exception: User Not Found by index: " + index
                                            + ". Last available index in the list is " + (size - 1));
        }
        return usersArr[index];
    }

    @Override
    public int NumbOfUsers() {
        return size;
    }

    private void reallocUsersArr() {
        int newCap = (int)((double)capacity * CAP_MULTIPLIER);
        User[] newUserArr = new User[newCap];
        for (int i = 0; i < size; i++) {
            newUserArr[i] = usersArr[i];
        }
        usersArr = newUserArr;
        capacity = newCap;
    }
}
