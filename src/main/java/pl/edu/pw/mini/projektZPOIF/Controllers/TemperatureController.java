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

@Tag(name = "Temperature")
@RestController
public class TemperatureController {
    final TcpService tcpService;

    @Autowired
    public TemperatureController(TcpService tcpService) {
        this.tcpService = tcpService;
    }

    @PutMapping("/{id}/temperature")
    public ResponseEntity setBulbHsv(
            @PathVariable("id") String serial,
            @RequestParam @Min(1700) @Max(6500) int temperature)
    {
        tcpService.setTemperature(serial, temperature);
        return ResponseEntity.ok().build();
    }
}
