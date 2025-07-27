package app;

public record Localidade(String pais, String local) {
    @Override
    public String toString() {
        return "{\"pais\": \"" + pais + "\", \"local\": \"" + local + "\"}";
    }
}