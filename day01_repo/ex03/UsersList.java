package ex03;

public interface UsersList {

    public void addUser(User newUser);
    public User retrieveUserByID(int id);
    public User retrieveUserByIndex(int index);
    public int numbOfUsers();

}
