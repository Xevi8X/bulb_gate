package pl.edu.pw.mini.projektZPOIF.Repositories;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.InetAddress;
import java.net.InetSocketAddress;

@AllArgsConstructor
public class Bulb {

    @Getter
    private String id;

    @Getter
    private String name;

    @Getter
    private InetSocketAddress location;

    //poloczenie tcp



}
