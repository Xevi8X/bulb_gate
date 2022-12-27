package pl.edu.pw.mini.projektZPOIF.Repositories;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pl.edu.pw.mini.projektZPOIF.Exceptions.BulbNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class BulbRepository {

    private List<Bulb> bulbList;

    public Bulb getBulb(String id) throws BulbNotFoundException
    {
        Optional<Bulb> optionalBulb = getBulbList()
                .stream()
                .filter(b -> b.getSerial().equals(id))
                .findFirst();

        if (optionalBulb.isEmpty())
        {
            throw new BulbNotFoundException("Bulb with serial \" + id + \" not found");
        }
        return optionalBulb.get();
    }
    public List<Bulb> getBulbList()
    {
        if(bulbList == null) return List.of();
        return bulbList;
    }

    public boolean addBulb(Bulb bulb)
    {
        synchronized (this) {
            if (bulbList == null) bulbList = new ArrayList<>();
            var bulbOpt = bulbList.stream().filter(b -> b.getSerial().equals(bulb.getSerial())).findFirst();
            if (bulbOpt.isPresent()) {
                Bulb bulbFromList = bulbOpt.get();
                bulbFromList.patch(bulb);
                return false;
            }
            bulbList.add(bulb);
            return true;
        }
    }
}
