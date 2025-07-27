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
        // Só aceita IPv4
        if (ipAddress == null || !ipAddress.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
            return null; // não retorna IPv6 nem formatos inválidos
        }
        return ipAddress;
    }

    ResponseEntity<Localidade> busca_local(String ip) {
        Localidade local = app.busca_localidade(ip);
        if (local == null) {
            // Retorna corpo JSON de "desconhecido", e status 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Localidade("Desconhecido", "Desconhecido"));
        } else {
            return ResponseEntity.ok(local);
        }
    }

    @GetMapping("/geoip")
    ResponseEntity<Localidade> meu_local(HttpServletRequest req) {
        String ip = get_remote_ip(req);
        if (ip == null) {
            // Retorna erro explícito de IP inválido
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return busca_local(ip);
    }

    @GetMapping("/geoip/{ip}")
    ResponseEntity<Localidade> meu_local(@PathVariable String ip) {
        // Só aceita IPv4
        if (ip == null || !ip.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return busca_local(ip);
    }
}