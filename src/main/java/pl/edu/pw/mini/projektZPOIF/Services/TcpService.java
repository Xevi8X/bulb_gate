package pl.edu.pw.mini.projektZPOIF.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.projektZPOIF.DTO.RequestTCP;
import pl.edu.pw.mini.projektZPOIF.Exceptions.BulbNotFoundException;
import pl.edu.pw.mini.projektZPOIF.Models.Bulb;
import pl.edu.pw.mini.projektZPOIF.Models.BulbResponse;
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

    public void connectToBulb(Bulb bulb) throws IOException
    {
        if (bulb.getSocket() != null)
        {
            if (bulb.getSocket().isConnected()) return;
            bulb.getSocket().close();
        }
        bulb.setSocket(new Socket(bulb.getLocation().getAddress(), bulb.getLocation().getPort(),null, tcpStart++));
        new TCPListenerThread(bulb).start();
    }

    public void bulbSetPower(String id, boolean power)
    {
        Bulb bulb;
        try
        {
            bulb = bulbRepository.getBulb(id);
        }
        catch (BulbNotFoundException e)
        {
            log.error(e.getMessage());
            return;
        }

        try
        {
            connectToBulb(bulb);
        }
        catch (IOException e)
        {
            log.error(e.getMessage());
            return;
        }

        try
        {
            final var requestTCP = new RequestTCP(
                    1,
                    "set_power",
                    power? "on": "off",
                    "smooth",
                    500);
            sendTCPRequest(bulb, requestTCP);
        }
        catch (IOException e)
        {
            log.error(e.getMessage());
        }
    }

    public void setRgb(String id, int rgb)
    {
        Bulb bulb;
        try
        {
            bulb = bulbRepository.getBulb(id);
        }
        catch (BulbNotFoundException e)
        {
            log.error(e.getMessage());
            return;
        }

        try
        {
            connectToBulb(bulb);
        }
        catch (IOException e)
        {
            log.error(e.getMessage());
            return;
        }

        try
        {
            final var requestTCP = new RequestTCP(
                    1,
                    "set_rgb",
                    rgb,
                    "smooth",
                    500);
            sendTCPRequest(bulb, requestTCP);
        }
        catch (IOException e)
        {
            log.error(e.getMessage());
        }
    }

    public void setBrightness(String id, byte brightness)
    {
        Bulb bulb;
        try
        {
            bulb = bulbRepository.getBulb(id);
        }
        catch (BulbNotFoundException e)
        {
            log.error(e.getMessage());
            return;
        }

        try
        {
            connectToBulb(bulb);
        }
        catch (IOException e)
        {
            log.error(e.getMessage());
            return;
        }

        try
        {
            final var requestTCP = new RequestTCP(
                    1,
                    "set_bright",
                    brightness,
                    "smooth",
                    500);
            sendTCPRequest(bulb, requestTCP);
        }
        catch (IOException e)
        {
            log.error(e.getMessage());
        }
    }

    private void sendTCPRequest(Bulb bulb, RequestTCP requestTCP) throws IOException
    {
        var out = new PrintWriter(bulb.getSocket().getOutputStream(), true);
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(requestTCP);
        out.write(json+"\r\n");
        out.flush();
    }



    public class TCPListenerThread extends Thread
    {
        private Bulb bulb;
        private final ObjectMapper objectMapper = new ObjectMapper();

        public TCPListenerThread(Bulb bulb)
        {
            this.bulb = bulb;
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
                    BulbResponse bulbResponse = objectMapper.readValue(msg, BulbResponse.class);
                    if (bulbResponse.isNotification())
                    {
                        bulbResponse.getParams().forEach(bulb::setValueByParameter);
                    }
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
