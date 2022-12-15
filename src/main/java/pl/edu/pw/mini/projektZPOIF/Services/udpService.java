package pl.edu.pw.mini.projektZPOIF.Services;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pw.mini.projektZPOIF.Repositories.Bulb;
import pl.edu.pw.mini.projektZPOIF.Repositories.BulbRepository;

public class udpService {

    final BulbRepository bulbRepository;

    @Autowired
    public udpService(BulbRepository bulbRepository) {
        this.bulbRepository = bulbRepository;
    }

    public void scan()
    {
        //MA ROZESLAC BROADCAST, poczkac na powrot komunikatu, i jesli pozgłaszaja sie zarowki to na zrobic

        //InetAddress addr = InetAddress.getByName("127.0.0.1");
        bulbRepository.addBulb(new Bulb("123","123"));
    }

}
