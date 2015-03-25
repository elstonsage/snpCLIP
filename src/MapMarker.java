package snpclip;

public class MapMarker {
    public String Name = new String();
    public double PDistance;
    public double GDistance;
    public MapMarker(String N, double P, double G) {
        Name = N;
        PDistance = P;
        GDistance = G;
    }
    public MapMarker() {
        Name = "NULL";
        PDistance = -1;
        GDistance = -1;
    }
}
