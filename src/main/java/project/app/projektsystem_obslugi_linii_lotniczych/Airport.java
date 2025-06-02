package project.app.projektsystem_obslugi_linii_lotniczych;

public class Airport {
    int airport_id;
    String name;

    public Airport(int airport_id, String name) {
        this.airport_id = airport_id;
        this.name = name;
    }

    public int getAirport_id() {
        return airport_id;
    }

    public void setAirport_id(int airport_id) {
        this.airport_id = airport_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
