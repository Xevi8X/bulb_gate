package pl.edu.pw.mini.projektZPOIF.Repositories;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BulbRepository {


    private List<Bulb> bulbList;

    public List<Bulb> getBulbList()
    {
        if(bulbList == null) return List.of();
        return bulbList;
    }

    public void addBulb(Bulb bulb)
    {
        if(bulbList == null) bulbList = new ArrayList<Bulb>();
        bulbList.add(bulb);
    }
}
