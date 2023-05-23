package common.networkStructures;
import common.commands.CommandWithResponse;

import java.io.Serializable;
import java.net.SocketAddress;

public class Request implements Serializable {
    private SocketAddress host;

    public Request() {};


    public SocketAddress getHost() {
        return host;
    }

    public void setHost(SocketAddress host) {
        this.host = host;
    }
}