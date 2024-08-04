package idat.proyecto.veterinaria.response;

import java.util.HashMap;
import java.util.Map;

public class Response {

    public static Map<String, Object> createMap(String message, Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("id", id);
        return map;
    }
    
    public static Map<String, Object> createMap(String message, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("id-id", id);
        return map;
    }
    
    public static Map<String, Object> createMapError(String message, String error) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("error", error);
        return map;
    }
    
    public static Map<String, Object> createMapTotalQuery(String message, Integer total) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("total", total);
        return map;
    }
    
    public static Map<String, Object> createMapTotalQuery(String message, Double total) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("total", total);
        return map;
    }
    
    public static Map<String, Object> createMapUsername(String message, String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("username", username);
        return map;
    }
    
    public static Map<String, Object> createMapCorreo(String message, String correo) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("correo", correo);
        return map;
    }
    
    public static Map<String, Object> createMapAuth(String message, String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("token", token);
        return map;
    }
    
    public static Map<String, Object> createMapValidToken(String message, Boolean valid, Long remaining) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("valid", valid);
        map.put("remaining", remaining);
        return map;
    }
    
}
