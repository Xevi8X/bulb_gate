package pl.edu.pw.mini.projektZPOIF.Repositories;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class BulbRepository {

    @Getter
    private List<Bulb> bulbList;

    public void addBulb(Bulb bulb)
    {
        if(bulbList == null) bulbList = new ArrayList<Bulb>();
        bulbList.add(bulb);
    }
}
