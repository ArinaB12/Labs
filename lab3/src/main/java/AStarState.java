import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;
    private HashMap<Location, Waypoint> openWaypoints, closeWaypoints;


    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
        this.openWaypoints = new HashMap<>();
        this.closeWaypoints = new HashMap<>();
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns <code>null</code>.
     **/

    final static WaypointComparator waypointComparator = new WaypointComparator();


    public Waypoint getMinOpenWaypoint()
    {
        try {
            return Collections.min(this.openWaypoints.values(), waypointComparator);
        }catch(Exception e) {
            return null;
        }

    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection.  If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection.  However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one <em>only
     * if</em> the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        Waypoint oldWp = this.openWaypoints.get(newWP.loc);
        if (oldWp == null || oldWp.getPreviousCost() > newWP.getPreviousCost()){
            this.openWaypoints.put(newWP.loc, newWP);
            return true;
        }
        return false;
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return this.openWaypoints.size();
    }


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        this.closeWaypoints.put(loc,this.openWaypoints.remove(loc));
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return this.closeWaypoints.containsKey(loc);
    }
}

class WaypointComparator implements Comparator<Waypoint>{
    public int compare(Waypoint a, Waypoint b){
        return Float.compare(a.getTotalCost(),b.getTotalCost());
    }
}