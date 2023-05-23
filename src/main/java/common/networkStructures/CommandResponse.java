package common.networkStructures;

import common.commands.Command;

import java.io.Serializable;

public class CommandResponse extends Response {
    public String command;
    public String output;
    public String[] args;
    public Serializable object;

    public CommandResponse(String output) {
        this.output = output;
    }

    public CommandResponse(String command, String[] args, String output) {
        this.command = command;
        this.output = output;
    }

    public CommandResponse(String command, String[] args, String output, Serializable object) {
        this.command = command;
        this.output = output;
        //this.object = object;
    }

    public String getCommand() {
        return command;
    }
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
