package pl.edu.pw.mini.projektZPOIF.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.mini.projektZPOIF.Services.TcpService;

@Tag(name = "Name")
@RestController
public class NameController {
    final TcpService tcpService;

    @Autowired
    public NameController(TcpService tcpService) {
        this.tcpService = tcpService;
    }

    @PutMapping("/{id}")
    public ResponseEntity setName(@PathVariable("id") String serial,
                                  @RequestParam String name)
    {
        tcpService.setBulbName(serial, name);
        return ResponseEntity.ok().build();
    }
}
