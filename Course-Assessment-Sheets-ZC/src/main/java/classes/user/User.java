package classes.user;

public class User {
    private String userPath;

    public String getUserPath() {
        return userPath;
    }

    public void setUserPath(String userPath) {
        this.userPath = userPath;
    }

    public User(String userPath) {
        this.userPath = userPath;
    }
}