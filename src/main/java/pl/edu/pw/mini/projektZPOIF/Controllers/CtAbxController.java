package pl.edu.pw.mini.projektZPOIF.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.mini.projektZPOIF.Services.TcpService;
import pl.edu.pw.mini.projektZPOIF.Utils.ColorUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

@Tag(name = "Light temperature")
@RestController
public class CtAbxController {
    final TcpService tcpService;

    @Autowired
    public CtAbxController(TcpService tcpService) {
        this.tcpService = tcpService;
    }

    @PutMapping("/{id}/ct_abx")
    public ResponseEntity setBulbRgb(
            @PathVariable("id") String serial,
            @RequestParam @Min(1700) @Max(6500) int ct_value,
            @RequestParam(required = false) Optional<Integer> changingTimeInMillis)

    {
        tcpService.setCtAbx(serial, ct_value,changingTimeInMillis.orElse(0));
        return ResponseEntity.ok().build();
    }
}
