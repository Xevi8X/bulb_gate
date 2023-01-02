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
import java.util.Optional;

@Tag(name = "HSV")
@RestController
public class HsvController {
    final TcpService tcpService;

    @Autowired
    public HsvController(TcpService tcpService) {
        this.tcpService = tcpService;
    }

    @PutMapping("/{id}/hsv")
    public ResponseEntity setBulbHsv(
            @PathVariable("id") String serial,
            @RequestParam @Min(0) @Max(359) int hue,
            @RequestParam @Min(0) @Max(100) int sat,
            @RequestParam(required = false) Optional<Integer> changingTimeInMillis)

    {
        tcpService.setHsv(serial, hue, sat,changingTimeInMillis.orElse(0));
        return ResponseEntity.ok().build();
    }
}
