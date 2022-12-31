package pl.edu.pw.mini.projektZPOIF.Models;

import lombok.Getter;

import java.util.Map;

public class BulbResponse {
    private Integer id;
    private String method;
    private String[] result;
    @Getter
    private Map<String, String> params;
    private BulbError error;

    public boolean isNotification()
    {
        return method.equals("props") && params != null;
    }

}
