package pl.edu.pw.mini.projektZPOIF.Controllers;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.mini.projektZPOIF.DTO.VersionDTO;
import pl.edu.pw.mini.projektZPOIF.Services.MainService;

@Tag(name = "Common")
@RestController
public class CommonController { // super robota

    final MainService mainService;

    @Autowired
    public CommonController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/version")
    @ResponseStatus(HttpStatus.OK)
    public VersionDTO api() {
        return new VersionDTO(mainService.getVersion());
    }
}
