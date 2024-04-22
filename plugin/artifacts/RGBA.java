public class RGBA {

    public short red;
    public short blue;
    public short green;
    public short alpha;
    
    public RGBA(short red, short green, short blue, short  alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public short getRed() { return red; }
    public short getGreen() { return green; }
    public short getBlue() { return blue; }
    public short getAlpha() { return alpha; }

    @Override
    public String toString() {
        return "Red: " + red + ", Green: " + green + ", Blue: " + blue + ", Alpha: " + alpha;
    }
    
}
