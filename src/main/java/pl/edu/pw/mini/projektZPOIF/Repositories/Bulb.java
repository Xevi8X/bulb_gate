package pl.edu.pw.mini.projektZPOIF.Repositories;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.InetAddress;
import java.net.InetSocketAddress;

@AllArgsConstructor
public class Bulb {

    @Getter
    private InetSocketAddress location;

    @Getter
    private String id;

    @Getter
    private String model;

    @Getter
    private String[] support;

    @Getter
    private boolean power;

    @Getter
    private int bright;

    @Getter
    private int colorMode;

    @Getter
    private int ct;

    @Getter
    private int rgb;

    @Getter
    private int hue;

    @Getter
    private int sat;

    @Getter
    private String name;

    //poloczenie tcp



}
