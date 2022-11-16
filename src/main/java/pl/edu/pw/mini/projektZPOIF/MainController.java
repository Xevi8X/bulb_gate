package pl.edu.pw.mini.projektZPOIF;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController { // super robota

    final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/api")
    String api() {
        return String.format("Ver. %s", mainService.getVersion());
    }
}
