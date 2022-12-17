package pl.edu.pw.mini.projektZPOIF.Repositories;


import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BulbRepository {

    private List<Bulb> bulbList;

    public Optional<Bulb> getBulb(String id)
    {
        if(getBulbList().stream().anyMatch(b -> b.getSerial().equals(id)))
        {
            Bulb bulb = getBulbList().stream().filter(b -> b.getSerial().equals(id)).findFirst().get();
            return Optional.of(bulb);
        }
        return Optional.empty();
    }
    public List<Bulb> getBulbList()
    {
        if(bulbList == null) return List.of();
        return bulbList;
    }

    public void addBulb(Bulb bulb)
    {
        if(bulbList == null) bulbList = new ArrayList<Bulb>();
        if(bulbList.stream().anyMatch(b -> b.getSerial().equals(bulb.getSerial())))
        {
            Bulb bulbFromList = bulbList.stream().filter(b -> b.getSerial().equals(bulb.getSerial())).findFirst().get();
            bulbFromList.patch(bulb);
        }
        bulbList.add(bulb);
    }
}
