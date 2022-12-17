package pl.edu.pw.mini.projektZPOIF.DTO;

public class RequestTCP {

    public int id;
    public String method;
    public Object[] params;

    public RequestTCP(int id, String method, Object... params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }
}
