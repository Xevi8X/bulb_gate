package pl.edu.pw.mini.projektZPOIF.Repositories;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.net.Socket;

public class Bulb {

    @Getter
    private String id;

    @Getter
    private String name;


    public Bulb(String id, String name) {
        this.id = id;
        this.name = name;
    }

    //lokacja

    //poloczenie tcp
    @Getter
    private Socket socket;

    public void connect() {
        try {
            socket = new Socket("83.5.184.145", 1337);
        } catch (IOException e) {
            // nie udalo sie polaczyc
        }
    }
}
