package pl.edu.pw.mini.projektZPOIF.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.projektZPOIF.Repositories.Bulb;
import pl.edu.pw.mini.projektZPOIF.Repositories.BulbRepository;

import java.util.List;

@Service
public class BulbService {

    final BulbRepository bulbRepository;
    final udpService udpService;

    @Autowired
    public BulbService(BulbRepository bulbRepository, pl.edu.pw.mini.projektZPOIF.Services.udpService udpService) {
        this.bulbRepository = bulbRepository;
        this.udpService = udpService;
    }

    public List<Bulb> getBulbList()
    {
        return this.getBulbList(false);
    }

    public List<Bulb> getBulbList(boolean withScan)
    {
        if(withScan) udpService.scan();
        return bulbRepository.getBulbList();
    }
}
