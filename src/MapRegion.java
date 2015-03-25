package snpclip;
import java.util.Vector;


public class MapRegion {
    String Name = new String();
    Vector Markers = new Vector();
    Vector MarkerList = new Vector();
    Vector MarkerDistance = new Vector();
    public MapRegion(String N) {
        Name = N;
    }
    public void AddMarker(MapMarker M){
        Markers.add(M);
        MarkerList.add(M.Name);
        MarkerDistance.add(M.GDistance);
    }
}
