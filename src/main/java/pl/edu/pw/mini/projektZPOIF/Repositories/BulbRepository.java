package pl.edu.pw.mini.projektZPOIF.Repositories;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class BulbRepository {

    @Getter
    private List<Bulb> bulbList;

    public void addBulb(Bulb bulb)
    {
        if(bulbList == null) bulbList = new ArrayList<Bulb>();
        bulbList.add(bulb);
    }
}
