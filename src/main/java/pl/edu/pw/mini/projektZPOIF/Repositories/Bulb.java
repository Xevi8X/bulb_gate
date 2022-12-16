package pl.edu.pw.mini.projektZPOIF.Repositories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Optional;
@Slf4j
@NoArgsConstructor
public class Bulb {

    @Getter
    @Setter
    private InetSocketAddress location;

    @Getter
    @Setter
    private String serial;

    @Getter
    @Setter
    private String model;

    @Getter
    @Setter
    private String[] support;

    @Getter
    @Setter
    private boolean power;

    @Getter
    @Setter
    private int bright;

    @Getter
    @Setter
    private int colorMode;

    @Getter
    @Setter
    private int ct;

    @Getter
    @Setter
    private int rgb;

    @Getter
    @Setter
    private int hue;

    @Getter
    @Setter
    private int saturation;

    @Getter
    @Setter
    private String name;

    public static Optional<Bulb> parser (String msg)
    {
        Bulb bulb = new Bulb();

        String[] lines = msg.split("\r\n");

        for (String line: lines)
        {
            String[] parts = line.split(": ");
            if(parts.length != 2) continue;

            switch (parts[0])
            {
                case "Location":
                    String[] value = parts[1].split(":");
                    try {
                        InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(value[1].substring(2)), Integer.parseInt(parts[2]));
                        bulb.location = address;
                    }
                    catch (Exception e)
                    {
                        log.error(e.getMessage());
                    }
                    break;
                case "id":
                    bulb.serial = parts[1];
                    break;
                case "model":
                    bulb.model = parts[1];
                    break;
                case "support":
                    bulb.support = parts[1].split(" ");
                    break;
                case "power":
                    if(parts[1].equals("on")) bulb.setPower(true);
                    if(parts[1].equals("off")) bulb.setPower(false);
                    break;
                case "bright":
                    bulb.bright = Integer.parseInt(parts[1]);
                    break;
                case "color_mode":
                    bulb.colorMode = Integer.parseInt(parts[1]);
                    break;
                case "ct":
                    bulb.ct = Integer.parseInt(parts[1]);
                    break;
                case "rgb":
                    bulb.rgb = Integer.parseInt(parts[1]);
                    break;
                case "hue":
                    bulb.hue = Integer.parseInt(parts[1]);
                    break;
                case "sat":
                    bulb.saturation = Integer.parseInt(parts[1]);
                    break;
                case "name":
                    bulb.name = parts[1];
                    break;
            }
        }

        if(bulb.serial.isEmpty()) bulb.serial ="-1";
        if(bulb.location == null) return Optional.empty();
        return Optional.of(bulb);
    }
}
