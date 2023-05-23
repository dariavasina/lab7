package common.networkStructures;

import common.commands.CommandWithResponse;

import java.net.SocketAddress;

public class CommandRequest extends Request{
    private CommandWithResponse command;
    private String username;
    private String password;

    public CommandRequest(CommandWithResponse command) {
        this.command = command;
    }
    public CommandWithResponse getCommand() {
        return command;
    }

    public void setCommand(CommandWithResponse command) {
        this.command = command;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
