package pl.edu.pw.mini.projektZPOIF.Repositories;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class BulbRepository {

    private List<Bulb> bulbList;

    public Optional<Bulb> getBulb(String id)
    {
        return getBulbList().stream().filter(b -> b.getSerial().equals(id)).findFirst();
    }
    public List<Bulb> getBulbList()
    {
        if(bulbList == null) return List.of();
        return bulbList;
    }

    public void addBulb(Bulb bulb)
    {
        synchronized (this) {
            if (bulbList == null) bulbList = new ArrayList<>();
            var bulbOpt = bulbList.stream().filter(b -> b.getSerial().equals(bulb.getSerial())).findFirst();
            if (bulbOpt.isPresent()) {
                Bulb bulbFromList = bulbOpt.get();
                bulbFromList.patch(bulb);
                try {
                    bulb.getSocket().close();
                }
                catch(IOException e)
                {
                    log.error(e.getMessage());
                }
                return;
            }
            bulbList.add(bulb);
        }
    }
}
