package pl.edu.pw.mini.projektZPOIF.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.projektZPOIF.Repositories.Bulb;
import pl.edu.pw.mini.projektZPOIF.Repositories.BulbRepository;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Slf4j
@Service
public class UdpService {

    final BulbRepository bulbRepository;
    private DatagramSocket socket;
    private InetAddress broadcast;
    private int port;

    @Autowired
    public UdpService(BulbRepository bulbRepository) {

        this.bulbRepository = bulbRepository;
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(1000);
            broadcast = InetAddress.getByName("239.255.255.250");
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }
        port = 1982;
    }

    public void scan()
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

}
