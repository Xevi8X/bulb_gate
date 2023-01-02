package pl.edu.pw.mini.projektZPOIF.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.projektZPOIF.DTO.RequestTCP;
import pl.edu.pw.mini.projektZPOIF.Exceptions.BulbNotFoundException;
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

    final ObjectMapper objectMapper;


    @Autowired
    public TcpService(BulbRepository bulbRepository, @Qualifier("getTcpStart") int tcpStart, ObjectMapper objectMapper) {
        this.tcpStart = tcpStart;
        this.bulbRepository = bulbRepository;
        this.objectMapper = objectMapper;
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

    public void bulbSetPower(String id, boolean power, int changingTimeInMillis)
    {
        try
        {
            Bulb bulb = bulbRepository.getBulb(id);
            connectToBulb(bulb);
            RequestTCP requestTCP;
            if(changingTimeInMillis > 0) {
                requestTCP = new RequestTCP(
                        1,
                        "set_power",
                        power ? "on" : "off",
                        "smooth",
                        changingTimeInMillis);
            }
            else
            {
                requestTCP = new RequestTCP(
                        1,
                        "set_power",
                        power ? "on" : "off",
                        "sudden",0);
            }
            sendTCPRequest(bulb, requestTCP);
        }
        catch (BulbNotFoundException e)
        {
            log.error(e.getMessage());
        }
        catch (IOException e)
        {
            log.error(e.getMessage());
        }
    }

    public void setRgb(String id, int rgb, int changingTimeInMillis)
    {
        try
        {
            Bulb bulb = bulbRepository.getBulb(id);
            connectToBulb(bulb);
            RequestTCP requestTCP;
            if(changingTimeInMillis > 0) {
                requestTCP = new RequestTCP(
                        1,
                        "set_rgb",
                        rgb,
                        "smooth",
                        changingTimeInMillis);
            }
            else
            {
                requestTCP = new RequestTCP(
                        1,
                        "set_rgb",
                        rgb,
                        "sudden",0);
            }
            sendTCPRequest(bulb, requestTCP);
        }
        catch (BulbNotFoundException e)
        {
            log.error(e.getMessage());
        }
        catch (IOException e)
        {
            log.error(e.getMessage());
        }
    }

    public void setBrightness(String id, byte brightness, int changingTimeInMillis)
    {
        try
        {
            Bulb bulb = bulbRepository.getBulb(id);
            connectToBulb(bulb);
            RequestTCP requestTCP;
            if(changingTimeInMillis > 0) {
                requestTCP = new RequestTCP(
                        1,
                        "set_bright",
                        brightness,
                        "smooth",
                        changingTimeInMillis);
            }
            else {
                requestTCP = new RequestTCP(
                        1,
                        "set_bright",
                        brightness,
                        "sudden",0);
            }
            sendTCPRequest(bulb, requestTCP);
        }
        catch (BulbNotFoundException e)
        {
            log.error(e.getMessage());
        }
        catch (IOException e)
        {
            log.error(e.getMessage());
        }
    }

    private void sendTCPRequest(Bulb bulb, RequestTCP requestTCP) throws IOException
    {
        var out = new PrintWriter(bulb.getSocket().getOutputStream(), true);
        var json = objectMapper.writeValueAsString(requestTCP);
        out.write(json+"\r\n");
        out.flush();
    }



    public class TCPListenerThread extends Thread
    {
        private final Bulb bulb;

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
