package common.networkStructures;

public class AuthenticationResponse extends Response{
    private boolean authenticated;
    private Exception exception;

    public AuthenticationResponse() {};

    public void setAuthenticated(boolean registrationResult) {
        this.authenticated = registrationResult;
    }
    public boolean isAuthenticated() {
        return authenticated;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
