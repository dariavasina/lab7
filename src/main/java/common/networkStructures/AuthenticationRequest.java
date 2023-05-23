package common.networkStructures;

public class AuthenticationRequest extends Request{
    private String username;
    private String password;
    private boolean isNewUser;

    public AuthenticationRequest(boolean isNewUser, String username, String password) {
        this.isNewUser = isNewUser;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isNewUser() {
        return isNewUser;
    }
}
