package pl.edu.pw.mini.projektZPOIF.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.projektZPOIF.DTO.RequestTCP;
import pl.edu.pw.mini.projektZPOIF.Repositories.Bulb;
import pl.edu.pw.mini.projektZPOIF.Repositories.BulbRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Service
@Slf4j
public class TcpService {

    final BulbRepository bulbRepository;
    private int tcpStart;


    @Autowired
    public TcpService(BulbRepository bulbRepository, @Qualifier("getTcpStart") int tcpStart) {
        this.tcpStart = tcpStart;
        this.bulbRepository = bulbRepository;
    }

    public void connectToBulb(String id)
    {
        var bulbOptional = bulbRepository.getBulb(id);
        if(bulbOptional.isEmpty())
        {
            log.error("No bulb found, serial: {}", id);
            return;
        }
        var bulb = bulbOptional.get();
        connectToBulb(bulb);
    }

    public void connectToBulb(Bulb bulb)
    {
        try
        {
            if (bulb.getSocket() != null)
            {
                if (bulb.getSocket().isConnected()) return;
                bulb.getSocket().close();
            }
            bulb.setSocket(new Socket(bulb.getLocation().getAddress(), bulb.getLocation().getPort(),null, tcpStart++));
            new TCPListenerThread(bulb).start();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void bulbSetPower(String id, boolean power)
    {
        var bulbOptional = bulbRepository.getBulb(id);
        if(bulbOptional.isEmpty())
        {
            log.error("No bulb found, serial: {}", id);
            return;
        }
        var bulb = bulbOptional.get();
        connectToBulb(bulb);
        try
        {
            var out = new PrintWriter(bulb.getSocket().getOutputStream(), true);
            final var requestTCP = new RequestTCP(
                    1,
                    "set_power",
                    power? "on": "off",
                    "smooth",
                    500);
            ObjectMapper objectMapper = new ObjectMapper();
            var json = objectMapper.writeValueAsString(requestTCP);
            out.write(json+"\r\n");
            out.flush();

        }
        catch (IOException e)
        {
            log.error(e.getMessage());
        }
    }

    public class TCPListenerThread extends Thread
    {
        private Bulb bulb;

        public TCPListenerThread(Bulb bulb)
        {
            this.bulb=bulb;
        }

        public void run()
        {
            try
            {
                var in = new BufferedReader(new InputStreamReader(bulb.getSocket().getInputStream()));
                while(true)
                {
                    var msg = in.readLine();
                    if(msg == null) break;
                    System.out.println(msg);
                }
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
            log.debug("Thread exit!");
        }

    }
}
