package pl.edu.pw.mini.projektZPOIF.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.projektZPOIF.Repositories.Bulb;
import pl.edu.pw.mini.projektZPOIF.Repositories.BulbRepository;

import java.net.*;
import java.util.Arrays;

@Slf4j
@Service
public class UdpService {

    final BulbRepository bulbRepository;
    final DatagramSocket socket;
    private InetAddress broadcast;
    private int port;



    @Autowired
    public UdpService(BulbRepository bulbRepository, DatagramSocket datagramSocket){

        this.bulbRepository = bulbRepository;
        this.socket = datagramSocket;
        try {
            broadcast = InetAddress.getByName("255.255.255.255");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        port = 1982;
    }

    public void sendSearch()
    {
        String searchMessage = """
                M-SEARCH * HTTP/1.1\r
                HOST: 239.255.255.250:1982\r
                MAN: "ssdp:discover"\r
                ST: wifi_bulb""";
        byte[] buf = searchMessage.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, broadcast, port);
        try {
            socket.send(packet);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }

    }

    public void receiveBulb(Message message)
    {
        try {
            String data = new String((byte[]) message.getPayload());
            String[] dataDivided = data.split("\r\n");
            String[] location = dataDivided[4].split(":");
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
            String name = dataDivided[17].split(" ")[1];
            Bulb newBulb = new Bulb(address, id, model, support, power, bright, colorMode, ct, rgb, hue, sat, name);
            bulbRepository.addBulb(newBulb);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }
    }
}
