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

@Tag(name = "RGB")
@RestController
public class RgbController {
    final TcpService tcpService;

    @Autowired
    public RgbController(TcpService tcpService) {
        this.tcpService = tcpService;
    }

    @PutMapping("/{id}")
    public ResponseEntity setBulbRgb(
            @PathVariable("id") String serial,
            @RequestParam byte red,
            @RequestParam byte green,
            @RequestParam byte blue)
    {
        tcpService.setRgb(serial, ColorUtils.toRgb(red, green, blue));
        return ResponseEntity.ok().build();
    }
}
