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
        final var requestTCP = new RequestTCP(
                1,
                "set_power",
                power? "on": "off",
                "smooth",
                500);
        executeTCPRequest(id, requestTCP);
    }

    public void setRgb(String id, int rgb)
    {
        final var requestTCP = new RequestTCP(
                1,
                "set_rgb",
                rgb,
                "smooth",
                500);
        executeTCPRequest(id, requestTCP);
    }

    public void setBrightness(String id, byte brightness)
    {
        final var requestTCP = new RequestTCP(
                1,
                "set_bright",
                brightness,
                "smooth",
                500);
        executeTCPRequest(id, requestTCP);
    }

    public void setHsv(String id, int hue, int saturation)
    {
        final var requestTCP = new RequestTCP(
                1,
                "set_hsv",
                hue,
                saturation,
                "smooth",
                500);
        executeTCPRequest(id, requestTCP);
    }

    public void setTemperature(String id, int temperature)
    {
        final var requestTCP = new RequestTCP(
                1,
                "set_ct_abx",
                temperature,
                "smooth",
                500);
        executeTCPRequest(id, requestTCP);
    }

    private void executeTCPRequest(String id, RequestTCP requestTCP)
    {
        try
        {
            Bulb bulb = bulbRepository.getBulb(id);
            connectToBulb(bulb);
            sendTCPRequest(bulb, requestTCP);
        }
        catch (Exception e)
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
