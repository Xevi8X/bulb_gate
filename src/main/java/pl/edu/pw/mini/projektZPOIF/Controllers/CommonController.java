package pl.edu.pw.mini.projektZPOIF.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.mini.projektZPOIF.DTO.VersionDTO;
import pl.edu.pw.mini.projektZPOIF.Services.MainService;
import pl.edu.pw.mini.projektZPOIF.Services.UdpService;

@Tag(name = "Common")
@RestController
public class CommonController {

    final MainService mainService;
    final UdpService udpService;

    @Autowired
    public CommonController(MainService mainService, UdpService udpService) {
        this.mainService = mainService;
        this.udpService = udpService;
    }

    @GetMapping("/version")
    public VersionDTO api() {
        return new VersionDTO(mainService.getVersion());
    }

    @GetMapping("/forceScan")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity test(){
        udpService.sendSearch();
        return ResponseEntity.ok().build();
    }
}
