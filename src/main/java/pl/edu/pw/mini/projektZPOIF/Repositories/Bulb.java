package pl.edu.pw.mini.projektZPOIF.Repositories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
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
    private int bright = -1;

    @Getter
    @Setter
    private int colorMode = -1;

    @Getter
    @Setter
    private int ct = -1;

    @Getter
    @Setter
    private int rgb = -1;

    @Getter
    @Setter
    private int hue = -1;

    @Getter
    @Setter
    private int saturation = -1;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @JsonIgnore
    private Socket socket;

    public void patch(Bulb bulb) {
        if (bulb.getLocation() != null) this.location = bulb.getLocation();
        if (bulb.getModel() != null) this.model = bulb.getModel();
        if (bulb.getSupport() != null && bulb.getSupport().length != 0) this.support = bulb.getSupport();
        if (bulb.isPower() != this.power) this.power = bulb.isPower();
        if (bulb.getBright() != this.bright && bulb.getBright() != -1) this.bright = bulb.getBright();
        if (bulb.getColorMode() != this.colorMode && bulb.getColorMode() != -1) this.colorMode = bulb.getColorMode();
        if (bulb.getCt() != this.ct && bulb.getCt() != -1) this.ct = bulb.getCt();
        if (bulb.getRgb() != this.rgb && bulb.getRgb() != -1) this.rgb = bulb.getRgb();
        if (bulb.getHue() != this.hue && bulb.getHue() != -1) this.hue = bulb.getHue();
        if (bulb.getSaturation() != this.saturation && bulb.getSaturation() != -1) this.saturation = this.getSaturation();
        if (bulb.getName() != null && !bulb.getName().isEmpty()) this.name = this.getName();
    }

    public boolean supports(String functionality)
    {
        return Arrays.asList(support).contains(functionality);
    }

    public static Optional<Bulb> parser (String msg)
    {
        Bulb bulb = new Bulb();

        String[] lines = msg.split("\r\n");

        for (String line: lines)
        {
            String[] parts = line.split(": ");
            if(parts.length != 2) continue;

            switch (parts[0]) {
                case "Location" -> {
                    String[] value = parts[1].split(":");
                    try {
                        bulb.location = new InetSocketAddress(InetAddress.getByName(value[1].substring(2)), Integer.parseInt(value[2]));
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
                case "id" -> bulb.serial = parts[1];
                case "model" -> bulb.model = parts[1];
                case "support" -> bulb.support = parts[1].split(" ");
                case "power" -> {
                    if (parts[1].equals("on")) bulb.setPower(true);
                    if (parts[1].equals("off")) bulb.setPower(false);
                }
                case "bright" -> bulb.bright = Integer.parseInt(parts[1]);
                case "color_mode" -> bulb.colorMode = Integer.parseInt(parts[1]);
                case "ct" -> bulb.ct = Integer.parseInt(parts[1]);
                case "rgb" -> bulb.rgb = Integer.parseInt(parts[1]);
                case "hue" -> bulb.hue = Integer.parseInt(parts[1]);
                case "sat" -> bulb.saturation = Integer.parseInt(parts[1]);
                case "name" -> bulb.name = parts[1];
            }
        }

        if(bulb.serial.isEmpty()) bulb.serial ="-1";
        if(bulb.location == null) return Optional.empty();
        return Optional.of(bulb);
    }

    public void patch(String key, Object value) {
        switch (key) {
            case "power" -> {
                if (value.equals("on")) setPower(true);
                if (value.equals("off")) setPower(false);
            }
            case "bright" -> setBright((int) value);
            case "ct" -> setCt((int) value);
            case "rgb" -> setRgb((int) value);
            case "hue" -> setHue((int) value);
            case "sat" -> setSaturation((int) value);
            case "color_mode" -> setColorMode((int) value);
            case "name" -> setName(value.toString());
        }

    }
}
