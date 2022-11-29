package model;

public class DJ extends Product {
    private boolean lights;
    private boolean stereo;

    public DJ(String name, String description, boolean lights, boolean stereo) {
        super(name, description);
        this.lights = lights;
        this.stereo = stereo;
    }

    public boolean isLights() {
        return lights;
    }

    public void setLights(boolean lights) {
        this.lights = lights;
    }

    public boolean isStereo() {
        return stereo;
    }

    public void setStereo(boolean stereo) {
        this.stereo =stereo;
    }

}
