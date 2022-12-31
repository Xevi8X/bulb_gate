package pl.edu.pw.mini.projektZPOIF.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;

import static java.lang.Integer.parseInt;

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
    private int temperature = -1;

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
        if (bulb.getLocation() != this.location && bulb.getLocation() != null) this.location = bulb.getLocation();
        if (bulb.getSerial() != null &&!bulb.getSerial().equals(this.getSerial()) && !bulb.getSerial().isEmpty())this.serial = bulb.getSerial();
        if (bulb.getModel() != null && !bulb.getModel().equals(this.model) && !bulb.getModel().isEmpty()) this.model = bulb.getModel();
        if (bulb.getSupport() != null && !bulb.getSupport().equals(this.support)) this.support = bulb.getSupport();
        if (bulb.isPower() != this.power) this.power = bulb.isPower();
        if (bulb.getBright() != this.bright && bulb.getBright() != -1) this.bright = bulb.getBright();
        if (bulb.getColorMode() != this.colorMode && bulb.getColorMode() != -1)this.colorMode = bulb.getColorMode();
        if (bulb.getTemperature() != this.temperature && bulb.getTemperature() != -1) this.temperature = bulb.getTemperature();
        if (bulb.getRgb() != this.rgb && bulb.getRgb() != -1) this.rgb = bulb.getRgb();
        if (bulb.getHue() != this.hue && bulb.getHue() != -1) this.hue = bulb.getHue();
        if (bulb.getSaturation() != this.saturation && bulb.getSaturation() != -1) this.saturation = this.getSaturation();
        if (bulb.getName() != null && !bulb.getName().equals(this.getName()) && !bulb.getName().isEmpty())this.name = this.getName();
    }

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
                        InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(value[1].substring(2)), Integer.parseInt(value[2]));
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
                    bulb.temperature = Integer.parseInt(parts[1]);
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

    public void setValueByParameter(String parameter, String value) {
        switch (parameter) {
            case "power" -> power = value.equals("on");
            case "bright" -> bright = parseInt(value);
            case "ct" -> temperature = parseInt(value);
            case "rgb" -> rgb = parseInt(value);
            case "hue" -> hue = parseInt(value);
            case "sat" -> saturation = parseInt(value);
            // case "color_mode":
        }
    }
}
