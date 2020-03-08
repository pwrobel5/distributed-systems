package sockets.server;

import java.util.Objects;

public class Pair<T, K> {
    private T address;
    private K port;

    public Pair(T address, K port) {
        this.address = address;
        this.port = port;
    }

    public T getAddress() {
        return address;
    }

    public K getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return address.equals(pair.address) &&
                port.equals(pair.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, port);
    }
}
