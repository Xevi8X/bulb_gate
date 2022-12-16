package pl.edu.pw.mini.projektZPOIF.DTO;

public class CommandDTO {

    public int id;
    public String method;
    public Object[] params;

    public CommandDTO(int id, String method, Object... params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }
}
