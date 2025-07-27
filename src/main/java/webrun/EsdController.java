package webrun;

import app.Localidade;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.App;

@RestController
public class EsdController {

    private final App app = new App();

    String get_remote_ip(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Real-IP"); // Check for X-Real-IP
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr(); // Fallback to getRemoteAddr
        }
        // If X-Forwarded-For contains multiple IPs, take the first one
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }
        return ipAddress;
    }

    ResponseEntity<Localidade> busca_local(String ip) {
        ResponseEntity<Localidade> result = null;

        Localidade local = app.busca_localidade(ip);
        if (local == null) {
            result = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            result = ResponseEntity.ok(app.busca_localidade(ip));
        }
        return result;
    }

    @GetMapping("/geoip")
    ResponseEntity<Localidade> meu_local(HttpServletRequest req) {
        String ip = get_remote_ip(req);

        return busca_local(ip);
    }

    @GetMapping("/geoip/{ip}")
    ResponseEntity<Localidade> meu_local(@PathVariable String ip) {
        return busca_local(ip);
    }
}
