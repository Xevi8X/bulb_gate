package pl.edu.pw.mini.projektZPOIF.Services;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pw.mini.projektZPOIF.Repositories.Bulb;
import pl.edu.pw.mini.projektZPOIF.Repositories.BulbRepository;

public class tcpService {

    final BulbRepository bulbRepository;

    @Autowired
    public tcpService(pl.edu.pw.mini.projektZPOIF.Repositories.BulbRepository bulbRepository) {
        this.bulbRepository = bulbRepository;
    }

    public void connectToBulbs()
    {
        //pootwierac TCP
    }

    public void bulbPower(Bulb bulb, Boolean isOn)
    {
        //setPower
        //TCPDTO tcpdto = new TCPDTO(1,"set_power",new Object[]{"abc"});
        //
        //        ObjectMapper objectMapper = new ObjectMapper();
        //
        //        String json = objectMapper.writeValueAsString(tcpdto);
    }
}
