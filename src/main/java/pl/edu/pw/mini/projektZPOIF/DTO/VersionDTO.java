package pl.edu.pw.mini.projektZPOIF.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@Schema(description = "VersionDTO")
@AllArgsConstructor
public class VersionDTO {
    public String version;
}
