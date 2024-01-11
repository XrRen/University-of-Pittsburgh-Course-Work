import java.util.*;
import java.io.*;

/* use lab5 and site it
 * if use diagraph, use two edges
 */
public class AirlineSystem implements AirlineInterface {

    private String[] citys;
    private Digraph G;
    private final int INFINITY = Integer.MAX_VALUE;
    private Set<ArrayList<Route>> withIn;
  /**
   * reads the city names and the routes from a file
   * @param fileName the String file name
   * @return true if routes loaded successfully and false otherwise
   */
  public boolean loadRoutes(String fileName)
  {
    try
    {
        File file = new File(fileName);
        Scanner fileRead = new Scanner(file);
        int cityNum = Integer.parseInt(fileRead.nextLine());
        citys = new String[cityNum];
        G = new Digraph(cityNum);
        for(int i = 0; i < cityNum; i++)
        {
          citys[i] = fileRead.nextLine();
        }
        while(fileRead.hasNextLine())
        {
          String tempE = fileRead.nextLine();
          String[] splitE = tempE.split(" ");
          int from = Integer.parseInt(splitE[0]);
          int to = Integer.parseInt(splitE[1]);
          int dist = Integer.parseInt(splitE[2]);
          double cost = Double.parseDouble(splitE[3]);
          WeightedDirectedEdge edgeTo = new WeightedDirectedEdge(from - 1, to - 1,dist,cost);
          WeightedDirectedEdge edgeFrom = new WeightedDirectedEdge(to - 1,from - 1,dist,cost);
          G.addEdge(edgeTo);
          G.addEdge(edgeFrom);
        }

		    fileRead.close();
        /*more code needed, figure out that does route.java do. */
        return true;
    }
    catch(FileNotFoundException e)
    {
        return false;
    }
    
  }
  private int found(String c)
  {
    for(int i = 0; i < citys.length; i++)
    {
      if(c.equals(citys[i]))
      {
        return i;
      }
    }
    return -1;
  }
  /**
   * returns the set of city names in the Airline system
   * @return a (possibly empty) Set<String> of city names
   */
  public Set<String> retrieveCityNames()
  {
    Set<String> cityName = new HashSet<String>();
    for(int i = 0; i < citys.length; i++)
    {
      cityName.add(citys[i]);
    }
    return cityName;
  }

  /**
   * returns the set of direct routes out of a given city
   * @param city the String city name
   * @return a (possibly empty) Set<Route> of Route objects representing the
   * direct routes out of city
   * @throws CityNotFoundException if the city is not found in the Airline
   * system
   */
  public Set<Route> retrieveDirectRoutesFrom(String city)
    throws CityNotFoundException
    {
      int pos = found(city);
      Set<Route> route = new HashSet<Route>();
      if(pos < 0)
      {
        CityNotFoundException n = new CityNotFoundException("This city is not existed in the airline system");
        throw n;
      }
      else
      {
        for(WeightedDirectedEdge e : G.adj(pos))
        {
          Route temp = new Route(citys[e.from()], citys[e.to()], e.distance(), e.cost());
          route.add(temp);
        }
      }
      return route;
    }

  /**
   * finds fewest-stops path(s) between two cities // BFS
   * @param source the String source city name
   * @param destination the String destination city name
   * @return a (possibly empty) Set<ArrayList<String>> of fewest-stops paths.
   * Each path is an ArrayList<String> of city names that includes the source
   * and destination city names.
   * @throws CityNotFoundException if any of the two cities are not found in the
   * Airline system
   */
  // try to implement the extra credit
  public Set<ArrayList<String>> fewestStopsItinerary(String source,
    String destination) throws CityNotFoundException
    {
      int sou = found(source);
      int des = found(destination);
      if(sou < 0)
      {
        throw new CityNotFoundException("No such city.");
      }
      if(des < 0)
      {
        throw new CityNotFoundException("No such city.");
      }
      Set<ArrayList<String>> fewest = new HashSet<ArrayList<String>>();
      G.bfs(sou);
      if(!G.marked[des]){
        return fewest;
      } else {
        Stack<Integer> path = new Stack<>();
        ArrayList<String> onePath = new ArrayList<String>();
        for (int x = des; x != sou; x = G.edgeTo[x]){
          path.push(x);
        }
        path.push(sou);
          while(!path.empty()){
            onePath.add(citys[path.pop()]);
          }

        fewest.add(onePath);
      }
      return fewest;
    }


  /**
   * finds shortest distance path(s) between two cities //dijkstra on distance
   * @param source the String source city name
   * @param destination the String destination city name
   * @return a (possibly empty) Set<ArrayList<Route>> of shortest-distance
   * paths. Each path is an ArrayList<Route> of Route objects that includes a
   * Route out of the source and a Route into the destination.
   * @throws CityNotFoundException if any of the two cities are not found in the
   * Airline system
   */
  public Set<ArrayList<Route>> shortestDistanceItinerary(String source,
    String destination) throws CityNotFoundException
    {
      int sou = found(source);
      int des = found(destination);
      if(sou < 0)
      {
        throw new CityNotFoundException("No such city.");
      }
      if(des < 0)
      {
        throw new CityNotFoundException("No such city.");
      }
      Set<ArrayList<Route>> fewest = new HashSet<ArrayList<Route>>();
      
      G.dijkstrasForDis(sou, des);
      if(!G.marked[des])
      { 
        return fewest;
      } 
      else 
      {
        Stack<Integer> path = new Stack<>();
        for (int x = des; x != sou; x = G.edgeTo[x])
        {
          path.push(x);
        }
        int prevVertex = sou;
        ArrayList<Route> tempR = new ArrayList<Route>();
        while(!path.empty())
        {
          int v = path.pop();
          tempR.add(new Route(citys[prevVertex], citys[v],G.dist[v],G.cost[v]));
          prevVertex = v;
        }
        fewest.add(tempR);
      }
      return fewest;
    }


 /**
  * finds cheapest path(s) between two cities   //dijkstra on price
  * @param source the String source city name
  * @param destination the String destination city name
  * @return a (possibly empty) Set<ArrayList<Route>> of cheapest
  * paths. Each path is an ArrayList<Route> of Route objects that includes a
  * Route out of the source and a Route into the destination.
  * @throws CityNotFoundException if any of the two cities are not found in the
  * Airline system
  */
  public Set<ArrayList<Route>> cheapestItinerary(String source,
  String destination) throws CityNotFoundException
  {
    int sou = found(source);
    int des = found(destination);
    if(sou < 0)
    {
      throw new CityNotFoundException("No such city.");
    }
    if(des < 0)
    {
      throw new CityNotFoundException("No such city.");
    }
    Set<ArrayList<Route>> fewest = new HashSet<ArrayList<Route>>();
      
    G.dijkstrasForCost(sou, des);
    if(!G.marked[des])
    { 
      return fewest;
    } 
    else 
    {
      Stack<Integer> path = new Stack<>();
      for (int x = des; x != sou; x = G.edgeTo[x])
      {
        path.push(x);
      }
      int prevVertex = sou;
      ArrayList<Route> tempR = new ArrayList<Route>();
      while(!path.empty())
      {
        int v = path.pop();
        tempR.add(new Route(citys[prevVertex], citys[v],G.dist[v],G.cost[v]));
        prevVertex = v;
      }
      fewest.add(tempR);
    }
    return fewest;
  }

  // dijkstrs on cost from a to b to c = a to b + b to c, b is duplicated, need to solve this

  /**
  * finds cheapest path(s) between two cities going through a third city
  * @param source the String source city name
  * @param transit the String transit city name
  * @param destination the String destination city name
  * @return a (possibly empty) Set<ArrayList<Route>> of cheapest
  * paths. Each path is an ArrayList<Route> of city names that includes
  * a Route out of source, into and out of transit, and into destination.
  * @throws CityNotFoundException if any of the three cities are not found in
  * the Airline system
  */
  public Set<ArrayList<Route>> cheapestItinerary(String source,
  String transit, String destination) throws CityNotFoundException
  {
    int sou = found(source);
    int des = found(destination);
    int tra = found(transit);
    if(sou < 0)
    {
      throw new CityNotFoundException("No such city.");
    }
    if(des < 0)
    {
      throw new CityNotFoundException("No such city.");
    }
    if(tra < 0)
    {
      throw new CityNotFoundException("No such city.");
    }
    Set<ArrayList<Route>> fewest = new HashSet<ArrayList<Route>>();
    ArrayList<Route> tempR = new ArrayList<Route>(); 
    G.dijkstrasForCost(sou, tra);
    if(!G.marked[tra])
    { 
      return fewest;
    } 
    else 
    {
      Stack<Integer> path = new Stack<>();
      for (int x = tra; x != sou; x = G.edgeTo[x])
      {
        path.push(x);
      }
      int prevVertex = sou;
      
      while(!path.empty())
      {
        int v = path.pop();
        tempR.add(new Route(citys[prevVertex], citys[v],G.dist[v],G.cost[v]));
        prevVertex = v;
      }
    }
    G.dijkstrasForCost(tra, des);
    if(!G.marked[des])
    { 
      return fewest;
    } 
    else 
    {
      Stack<Integer> path = new Stack<>();
      for (int x = des; x != tra; x = G.edgeTo[x])
      {
        path.push(x);
      }
      int prevVertex = tra;
      while(!path.empty())
      {
        int v = path.pop();
        tempR.add(new Route(citys[prevVertex], citys[v],G.dist[v],G.cost[v]));
        prevVertex = v;
      }
      fewest.add(tempR);
    }
    return fewest;
    
  }

  /**
   * finds one Minimum Spanning Tree (MST) for each connected component of
   * the graph  // prim on distance, disconected graph will return a set of MST, can use a list to rep what has visted or not, if some of them are not visited, then check again.
   *            // but there's a connected compoent code in the handout. 
   * @return a (possibly empty) Set<Set<Route>> of MSTs. Each MST is a Set<Route>
   * of Route objects representing the MST edges.
   */
  public Set<Set<Route>> getMSTs()
  {

    /*
     * Stack<Integer> path = new Stack<>();
      for (int x = G.edgeTo[G.edgeTo.length - 1]; x != sou; x = G.edgeTo[x])
      {
        path.push(x);
      }
      int prevVertex = start;
      
      while(!path.empty())
      {
        int v = path.pop();
        tempR.add(new Route(citys[prevVertex], citys[v],G.dist[v],G.cost[v]));
        prevVertex = v;
      }
     */
    G.marked = new boolean[G.v];
    for(int i = 0; i < G.marked.length; i++)
    {
      G.marked[i] = false;
    }
    Set<Set<Route>> mst = new HashSet<Set<Route>>();
    //G.marked = new boolean[G.v];
    for(int i = 0; i < G.v; i++)
    {
      if(!G.marked[i])
      {
        G.prims(i);
        Set<Route> trip = new HashSet<Route>();
        for(int e = 0; e < G.edgeTo.length;e++)
        {
            Route temp = new Route(citys[G.edgeTo[e]], citys[e], G.dist[e], G.cost[e]);
            trip.add(temp);
        }
        mst.add(trip);
      }
    }
    return mst;
  }

  /**
   * This is my recursive method to help me find the trip that's within the budget
   * @param total the double is the total amount of money we spend already
   * @param city the integer represent the city we want to start at and find the neighbor of it
   * @param budget the double is the maxium number we can spend on a trip
   * @param trip the ArrayList is the data structure we used to store one possible trip we can visit
   * @return
   */
  private ArrayList<Route> solve(double total, int city, double budget, ArrayList<Route> trip)
  {    
    for(WeightedDirectedEdge e : G.adj(city)) // we will iterate through all the city that is a neighbor of our starting city
    {
      if(!G.marked[e.to()] &&  total + e.cost() <= budget) // then we check have we visited this neighbor or not and if we visit this city, will we reach over the maxium
      {
        // if we have not visit this city yet, we marked this city as visited
        G.marked[e.to()] = true; 
        // then we add this path to our overall trip because it's under our budget
        Route path = new Route(citys[city], citys[e.to()], e.distance(), e.cost); 
        trip.add(path);
        ArrayList<Route> temp = new ArrayList<>(trip);
        // then we add this overall trip into our whole set and we call recursivily. 
        withIn.add(temp);
        solve(total+e.cost(), e.to(),budget,temp);
        // after we got returned from the recursive call, we need to set all the changes back to original.
        G.marked[e.to()] = false;
        //total-=e.cost();
        trip.remove(path);
      }
    }
    return trip;
  }

  /**
   * finds all itineraries starting out of a source city and within a given
   * price
   * @param city the String city name
   * @param budget the double budget amount in dollars
   * @return a (possibly empty) Set<ArrayList<Route>> of paths with a total cost
   * less than or equal to the budget. Each path is an ArrayList<Route> of Route
   * objects starting with a Route object out of the source city.
   */

   //can be recursive
  public Set<ArrayList<Route>> tripsWithin(String city, double budget)
    throws CityNotFoundException
    {
      /**
       * we first test to see wether the user gave us the approporate city or not.
       */
      withIn = new HashSet<ArrayList<Route>>();
      int start = found(city);
      if(start < 0)
      {
        throw new CityNotFoundException("No such city.");
      }

      ArrayList<Route> trip = new ArrayList<Route>();
      // then we initialize the the marked array to false and set the start vertex as visited because we start here.
      G.marked = new boolean[G.v];
      for (int i = 0; i < G.v; i++)
      {
        G.marked[i] = false;
      }
      G.marked[start] = true;
      G.cost = new double[G.v];
      G.cost[start] = 0;
      // then we call the helper method.
      solve(0,start, budget,trip);
      return withIn;
    }

  /**
   * finds all itineraries within a given price regardless of the
   * starting city
   * @param  budget the double budget amount in dollars
   * @return a (possibly empty) Set<ArrayList<Route>> of paths with a total cost
   * less than or equal to the budget. Each path is an ArrayList<Route> of Route
   * objects.
   */
  public Set<ArrayList<Route>> tripsWithin(double budget)
  {
    withIn = new HashSet<ArrayList<Route>>();
    ArrayList<Route> trip = new ArrayList<Route>();
    // we iterate all cities because we want to any routes that start at any city that fulfill
    for(int i = 0; i < citys.length; i++)
    {
      // then we initialize the the marked array to false and set the start vertex as visited because we start here.
      G.marked = new boolean[G.v];
      for (int j = 0; j < G.v; j++)
      {
        G.marked[j] = false;
      }
      G.marked[i] = true;
      G.cost = new double[G.v];
      G.cost[i] = 0;
      solve(0, i, budget,trip);
    }
    return withIn;
  }

  /**
	*  The <tt>Digraph</tt> class represents an directed graph of vertices
	*  named 0 through v-1. It supports the following operations: add an edge to
	*  the graph, iterate over all of edges leaving a vertex.Self-loops are
	*  permitted.
  * Got it from Lab9 of Sherif Khattab's CS 1501 
	*/
  private class Digraph 
  {
    private final int v;
    private int e;
    private LinkedList<WeightedDirectedEdge>[] adj;
    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path
    private double[] costTo;
    private int[] dist;
    private double[] cost;
    private int[] best;
    private LinkedList<WeightedDirectedEdge>[] sameW;


    /**
    * Create an empty digraph with v vertices.
    */
    public Digraph(int v) 
    {
      if (v < 0) throw new RuntimeException("Number of vertices must be nonnegative");
      this.v = v;
      this.e = 0;
      @SuppressWarnings("unchecked")
      LinkedList<WeightedDirectedEdge>[] temp =
      (LinkedList<WeightedDirectedEdge>[]) new LinkedList[v];
      adj = temp;
      for (int i = 0; i < v; i++)
      {
        adj[i] = new LinkedList<WeightedDirectedEdge>();
      }
    }

    /**
    * Add the edge e to this digraph.
    */
    public void addEdge(WeightedDirectedEdge edge) 
    {
      int from = edge.from();
      adj[from].add(edge);
      e++;
    }


    /**
    * Return the edges leaving vertex v as an Iterable.
    * To iterate over the edges leaving vertex v, use foreach notation:
    * <tt>for (WeightedDirectedEdge e : graph.adj(v))</tt>.
    */
    public Iterable<WeightedDirectedEdge> adj(int v) 
    {
      return adj[v];
    }
    public void bfs(int source) 
    {
      marked = new boolean[this.v];
      distTo = new int[this.e];
      edgeTo = new int[this.v];

      Queue<Integer> q = new LinkedList<Integer>();
      for (int i = 0; i < v; i++)
      {
        distTo[i] = INFINITY;
        marked[i] = false;
      }
      distTo[source] = 0;
      marked[source] = true;
      q.add(source);

      while (!q.isEmpty()) 
      {
        int v = q.remove();
        for (WeightedDirectedEdge w : adj(v)) 
        {
          if (!marked[w.to()]) 
          {
            edgeTo[w.to()] = v;
            distTo[w.to()] = distTo[v] + 1;
            marked[w.to()] = true;
            q.add(w.to());
          }
        }
      }
    }
    public void prims(int start)
    {
      //marked = new boolean[this.v];
      distTo = new int[this.v];
      edgeTo = new int[this.v];
      cost = new double[this.v];
      dist = new int[this.v];


      for (int i = 0; i < v; i++)
      {
        distTo[i] = INFINITY;
        //marked[i] = false;
      }
      distTo[start] = 0;
      marked[start] = true;
      int nMarked = 1;

      int current = start;
      while (nMarked < this.v) 
      {
        for (WeightedDirectedEdge w : adj(current)) 
        {
          if(distTo[current]+w.distance() < distTo[w.to()]) 
          {
            edgeTo[w.to()] = current;
            distTo[w.to()] = distTo[current]+w.distance();
            cost[w.to()] = w.cost();
            dist[w.to()] = w.distance();
          }
          
        }
        //Find the vertex with minimim path distance
        //This can be done more effiently using a priority queue!
        int min = INFINITY;
        current = -1;

        for(int i=0; i<distTo.length; i++)
        {
          if(marked[i])
            continue;
          if(distTo[i] < min){
            min = distTo[i];
            current = i;
          }
        }
        marked[current] = true;
        nMarked++;
      }
    }
    public void dijkstrasForDis(int source, int destination) 
    {
      marked = new boolean[this.v];
      distTo = new int[this.v];
      edgeTo = new int[this.v];
      cost = new double[this.v];
      dist = new int[this.v];


      for (int i = 0; i < v; i++)
      {
        distTo[i] = INFINITY;
        marked[i] = false;
      }
      distTo[source] = 0;
      marked[source] = true;
      int nMarked = 1;

      int current = source;
      while (nMarked < this.v) {
        for (WeightedDirectedEdge w : adj(current)) 
        {
          if (distTo[current]+w.distance() < distTo[w.to()]) 
          {
            edgeTo[w.to()] = current;
            distTo[w.to()] = distTo[current]+w.distance();
            cost[w.to()] = w.cost();
            dist[w.to()] = w.distance();
          }
          
        }
        //Find the vertex with minimim path distance
        //This can be done more effiently using a priority queue!
        int min = INFINITY;
        current = -1;

        for(int i=0; i<distTo.length; i++)
        {
          if(marked[i])
            continue;
          if(distTo[i] < min){
            min = distTo[i];
            current = i;
          }
        }
        if(current >= 0)
        {
          marked[current] = true;
          nMarked++;
        } 
        else //graph is disconnected
          break;
      }
    }
     public void dijkstrasForCost(int source, int destination) {
      marked = new boolean[this.v];
      edgeTo = new int[this.v];
      costTo = new double[this.v];
      cost = new double[this.v];
      dist = new int[this.v];

      for (int i = 0; i < v; i++)
      {
        costTo[i] = INFINITY;
        marked[i] = false;
      }
      costTo[source] = 0;
      marked[source] = true;
      int nMarked = 1;

      int current = source;
      while (nMarked < this.v) 
      {
        for (WeightedDirectedEdge w : adj(current)) 
        {
          if (costTo[current]+w.cost() < costTo[w.to()]) 
          {
            edgeTo[w.to()] = current;
            costTo[w.to()] = costTo[current]+w.cost();
            cost[w.to()] = w.cost();
            dist[w.to()] = w.distance();
          }
          
        }
        //Find the vertex with minimim path distance
        //This can be done more effiently using a priority queue!
        double min = INFINITY;
        current = -1;

        for(int i=0; i<costTo.length; i++)
        {
          if(marked[i])
            continue;
          if(costTo[i] < min){
            min = costTo[i];
            current = i;
          }
        }
        if(current >= 0)
        {
          marked[current] = true;
          nMarked++;
        } 
        else //graph is disconnected
          break;
      }
    }
  }
  private class WeightedDirectedEdge {
    private final int v;
    private final int w;
    private int distance;
    private double cost;

    /**
    * Create a directed edge from v to w with given weight.
    */
    public WeightedDirectedEdge(int v, int w, int distance, double c) {
      this.v = v;
      this.w = w;
      this.distance = distance;
      cost = c;
    }

    public int from(){
      return v;
    }

    public int to(){
      return w;
    }

    public int distance(){
      return distance;
    }
    public double cost()
    {
      return cost;
    }
  }
}
