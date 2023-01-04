package pl.edu.pw.mini.projektZPOIF.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.mini.projektZPOIF.Services.FFTService;

@Tag(name = "FFT")
@RestController
public class FFTController {
    final FFTService fftService;

    @Autowired
    public FFTController(FFTService fftService) {
        this.fftService = fftService;
    }

    @PutMapping("{id}/fft")
    public ResponseEntity setBulbHsv(
            @PathVariable("id") String serial,
            @RequestParam String uri)
    {
        fftService.play(serial,uri);
        return ResponseEntity.ok().build();
    }
}