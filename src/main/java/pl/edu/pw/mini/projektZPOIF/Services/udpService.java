package pl.edu.pw.mini.projektZPOIF.Services;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pw.mini.projektZPOIF.Repositories.Bulb;
import pl.edu.pw.mini.projektZPOIF.Repositories.BulbRepository;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class udpService {

    final BulbRepository bulbRepository;
    private DatagramSocket socket;
    private InetAddress broadcast;
    private int port;

    @Autowired
    public udpService(BulbRepository bulbRepository) {

        this.bulbRepository = bulbRepository;
        try {
            socket = new DatagramSocket();
            broadcast = InetAddress.getByName("239.255.255.250");
        }
        catch (Exception e)
        {
            // log
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
            byte[] received = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String receivedMessage = new String(packet.getData(), 0, packet.getLength());
        }
        catch (Exception e)
        {
            // log
        }

        //InetAddress addr = InetAddress.getByName("127.0.0.1");
        bulbRepository.addBulb(new Bulb("123","123"));
    }

}
