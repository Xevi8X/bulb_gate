package pl.edu.pw.mini.projektZPOIF.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.mini.projektZPOIF.Services.TcpService;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Tag(name= "Brightness")
@RestController
public class BrightnessController {
    final TcpService tcpService;

    @Autowired
    public BrightnessController(TcpService tcpService)
    {
        this.tcpService = tcpService;
    }

    @PutMapping("/{id}")
    public ResponseEntity setBrightness(
            @PathVariable("id") String serial,
            @RequestParam @Min(1) @Max(100) byte brightness)
    {
        tcpService.setBrightness(serial, brightness);
        return ResponseEntity.ok().build();
    }
}
