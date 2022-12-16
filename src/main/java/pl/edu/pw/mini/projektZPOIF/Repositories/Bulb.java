package pl.edu.pw.mini.projektZPOIF.Repositories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Optional;

@NoArgsConstructor
public class Bulb {

    @Getter
    @Setter
    private InetSocketAddress location;

    @Getter
    @Setter
    private String id;

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
    private int sat;

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
                case "power":
                    if(parts[1].equals("on")) bulb.setPower(true);
                    if(parts[1].equals("off")) bulb.setPower(false);
                break;

            }
        }

        /*
        String ip = location[1].substring(12);
        int port = Integer.parseInt(location[2]);
        InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(ip), port);
        String id = dataDivided[6].split(" ")[1];
        String model = dataDivided[7].split(" ")[1];
        String[] supportList = dataDivided[9].split(" ");
        String[] support = Arrays.copyOfRange(supportList, 1, supportList.length);
        Boolean power = dataDivided[10].split(" ")[1].equals("on");
        int bright = Integer.parseInt(dataDivided[11].split(" ")[1]);
        int colorMode = Integer.parseInt(dataDivided[12].split(" ")[1]);
        int ct = Integer.parseInt(dataDivided[13].split(" ")[1]);
        int rgb = Integer.parseInt(dataDivided[14].split(" ")[1]);
        int hue = Integer.parseInt(dataDivided[15].split(" ")[1]);
        int sat = Integer.parseInt(dataDivided[16].split(" ")[1]);
        String name = dataDivided[17].split(" ")[1];*/

        if(bulb.id.isEmpty()) bulb.id ="-1";
        if(bulb.location == null) return Optional.empty();
        return Optional.of(bulb);
    }
}
