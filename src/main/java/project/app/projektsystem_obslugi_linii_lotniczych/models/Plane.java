package project.app.projektsystem_obslugi_linii_lotniczych.models;

public class Plane {
    int plane_id;
    String model;
    int capacity;

    public Plane(int plane_id, String model, int capacity) {
        this.plane_id = plane_id;
        this.model = model;
        this.capacity = capacity;
    }

    public int getPlane_id() {
        return plane_id;
    }

    public void setPlane_id(int plane_id) {
        this.plane_id = plane_id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return model;
    }

}
