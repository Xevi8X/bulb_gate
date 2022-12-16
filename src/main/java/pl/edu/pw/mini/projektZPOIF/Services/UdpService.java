package pl.edu.pw.mini.projektZPOIF.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.projektZPOIF.Repositories.Bulb;
import pl.edu.pw.mini.projektZPOIF.Repositories.BulbRepository;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

@Slf4j
@Service
public class UdpService {

    final BulbRepository bulbRepository;
    final DatagramSocket socket;
    private InetAddress broadcast;
    private int port;



    @Autowired
    public UdpService(BulbRepository bulbRepository, DatagramSocket datagramSocket) {

        this.bulbRepository = bulbRepository;
        this.socket = datagramSocket;
        try {
            broadcast = InetAddress.getByName("239.255.255.250");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        port = 1982;
    }

    public void sendSearch()
    {
        //MA ROZESLAC BROADCAST, poczkac na powrot komunikatu, i jesli pozgłaszaja sie zarowki to na zrobic

        String searchMessage = "M-SEARCH*HTTP/1.1\r\nHOST:239:255:255:250:1982\r\nMAN:\"ssdp:discover\"\r\nST:wifi_bulb";
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
        String data = new String((byte[]) message.getPayload());
        String[] dataDivided = data.split("\n");
        String[] location = dataDivided[4].split(":");
        int port = Integer.parseInt(location[2]);
        //bulbRepository.addBulb(new Bulb(dataDivided[6].split(":")[1].substring(1), "", new InetSocketAddress()));
    }
}
