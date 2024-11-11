package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

public record StudentResource(String id, String name, int age, String address,
                              Long programId, String startPeriod) {
}
