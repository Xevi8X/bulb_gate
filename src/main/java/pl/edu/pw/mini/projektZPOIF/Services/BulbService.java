package pl.edu.pw.mini.projektZPOIF.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.projektZPOIF.Models.Bulb;
import pl.edu.pw.mini.projektZPOIF.Repositories.BulbRepository;

import java.util.List;

@Service
public class BulbService {

    final BulbRepository bulbRepository;
    final UdpService udpService;

    @Autowired
    public BulbService(BulbRepository bulbRepository, UdpService udpService) {
        this.bulbRepository = bulbRepository;
        this.udpService = udpService;
    }

    public List<Bulb> getBulbList()
    {
        return this.getBulbList(false);
    }

    public List<Bulb> getBulbList(boolean withScan)
    {
        if(withScan) udpService.sendSearch();
        return bulbRepository.getBulbList();
    }
}
