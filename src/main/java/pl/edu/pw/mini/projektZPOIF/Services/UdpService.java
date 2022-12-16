package pl.edu.pw.mini.projektZPOIF.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.projektZPOIF.Repositories.Bulb;
import pl.edu.pw.mini.projektZPOIF.Repositories.BulbRepository;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Optional;

@Slf4j
@Service
public class UdpService {

    final BulbRepository bulbRepository;
    final DatagramSocket socket;
    private InetAddress broadcast;
    private final int port;



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
        Optional<Bulb> newBulb =  Bulb.parser(new String((byte[]) message.getPayload()));
        if(newBulb.isEmpty())
        {
            log.error("Msg can not be parse\nMessage:\n{}",message);
            return;
        }
        bulbRepository.addBulb(newBulb.get());
    }
}