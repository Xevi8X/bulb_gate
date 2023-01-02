package pl.edu.pw.mini.projektZPOIF.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.mini.projektZPOIF.Repositories.Bulb;
import pl.edu.pw.mini.projektZPOIF.Services.TcpService;

@Tag(name = "Power")
@RestController
@Slf4j
public class PowerController {

    final TcpService tcpService;

    @Autowired
    public PowerController(TcpService tcpService) {
        this.tcpService = tcpService;
    }

    @PutMapping("/{id}")
    public ResponseEntity getBulb(@PathVariable("id") String serial, @RequestParam boolean power){
        tcpService.bulbSetPower(serial,power,0);
        return ResponseEntity.ok().build();
    }
}
