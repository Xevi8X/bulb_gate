package pl.edu.pw.mini.projektZPOIF.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.mini.projektZPOIF.Repositories.Bulb;
import pl.edu.pw.mini.projektZPOIF.Services.BulbService;

import java.util.List;

@Tag(name = "Bulb")
@RestController
@Slf4j
@RequestMapping("/bulbs")
public class BulbController {

    final BulbService bulbService;

    @Autowired
    public BulbController(BulbService bulbService)
    {
        this.bulbService = bulbService;
    }

    @GetMapping()
    public List<Bulb> getBulbs(){
        return bulbService.getBulbList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bulb> getBulb(@PathVariable("id") String serial){
        var bulbList = bulbService.getBulbList();
        var bulb = bulbList.stream().filter(b -> b.getSerial().equals(serial)).findAny();
        if(bulb.isEmpty())
        {
            log.error(String.format("Bulb (id: %s) not found!",serial));
            return new ResponseEntity(null,HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(bulb.get());
    }
}
