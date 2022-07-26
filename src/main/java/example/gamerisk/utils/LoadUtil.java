package example.gamerisk.utils;

import example.gamerisk.model.Continent;
import example.gamerisk.model.Country;
import javafx.scene.control.Alert;
import lombok.Getter;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * LoadUtil class manages the reading and writing map information from or to a .map file.
 * It also provides a format checking when reading a .map file to ensure the information can
 * be correctly loaded into the map editor.
 */

@Getter
public class LoadUtil {

	private static final HashMap<String, Continent> continentMap = new HashMap<> ();
	private static final HashMap<String, Country> countryMap = new HashMap<> ();

	/**
	 * Method to add a new Continent to the continentMap
	 *
	 * @param continent a new continent we want to add
	 */
	static void addContinent(Continent continent) {

		continentMap.put (continent.getName (), continent);
	}

	public static void addCountry(Country country) {

	}

	public static void validateConnected() {
	}

	public static void validateContinent() {
	}

	public static void validateCountry() {
	}

	public static List<Continent> getAllContinents() {
		return null;
	}

	public static List<Country> getAllCountry() {
		return null;
	}

	public static Country getCountry(String s) {
		return null;
	}

	public static Continent getContinent(String continentName) {
		return null;
	}

//	private static String label = "";
//
//	private static HashMap<String, Continent> continentMap = new HashMap<> ();
//
//	private static HashMap<String, Country> countryMap = new HashMap<> ();
//
//	/**
//	 * Method to get the Continent according to the name
//	 *
//	 * @param name name is the continent we want to get
//	 *
//	 * @return continent
//	 */
//	private static Continent getContinent (String name) {
//
//		return continentMap.get (name);
//	}
//
//	/**
//	 * Method to get the Continents
//	 *
//	 * @return ArrayList an list of continentMap.
//	 */
//	private static List<Continent> getAllContinents () {
//
//		return new ArrayList<> (continentMap.values ());
//	}
//
//	/**
//	 * Method to add a new Continent to the continentMap
//	 *
//	 * @param continent a new continent we want to add
//	 */
//	private static void addContinent (Continent continent) {
//
//		continentMap.put (continent.getName (), continent);
//	}
//
//	/**
//	 * Method to get the all the Country
//	 *
//	 * @return ArrayList an list of countryMap.
//	 */
//	private static List<Country> getAllCountry () {
//
//		return new ArrayList<> (countryMap.values ());
//	}
//
//	/**
//	 * Method to get the Country according to the name
//	 *
//	 * @return list an instance of Country.
//	 */
//	private static Country getCountry (String name) {
//
//		return countryMap.get (name);
//	}
//
//	/**
//	 * Method to add a new country to the continentMap
//	 */
//	private static void addCountry (Country country) {
//
//		countryMap.put (country.getName (), country);
//	}
//
//	private static void validateContinent () {
//
//		if (continentMap.isEmpty ()) throw new IllegalArgumentException ("No continent in the map file");
//	}
//
//	/**
//	 * Method to validate every country and their adjacent countries.
//	 */
//	private static void validateCountry () {
//
//		if (countryMap.isEmpty ()) throw new IllegalArgumentException ("No territory in the map file.");
//
//		Country country = countryMap.values ().stream ().filter (i -> i.getCoordinator () == null)
//				.findAny ().orElse (null);
//
//		if (country != null) throw new IllegalArgumentException ("Illegal adjacent country: " + country.getName ());
//
//		countryMap.values ().forEach (c -> {
//
//			Set<Country> adjacent = c.getAdjacentCountry ();
//
//			adjacent.forEach (a -> {
//				if (! a.getAdjacentCountry ().contains (c))
//
//					throw new IllegalArgumentException ("Illegal adjacent country " + c.getName () + " and " + a.getName ());
//			});
//		});
//	}
//
//	/**
//	 * Method to read a .map file and store all information to an instance of RiskMap and check
//	 * correctness of every information.
//	 *
//	 * @param file is the file to be read.
//	 *
//	 * @throws IOException if encounters IO error.
//	 */
//	public static void readFile (File file) throws IOException {
//
//		GameMap map = GameMap.getInstance ();
//
//		BufferedReader bufferedReader = new BufferedReader (new FileReader (file));
//
//		Stream<String> mapFile = bufferedReader.lines ();
//
//		mapFile = mapFile.filter (l -> ! "".equals (l.trim ()));
//
//		mapFile.forEach (s -> {
//
//			s = s.trim ();
//
//			if (s.equals ("[Map]") || s.equals ("[Territories]") || s.equals ("[Continents]")) {
//
//				label = s;
//
//			} else {
//
//				switch (label.toLowerCase ()) {
//
//					case "[map]": {
//
//						String[] split = s.split ("=");
//
//						switch (split[0].trim ()) {
//
//							case "author":
//								map.setAuthor (split[1]);
//								break;
//
//							case "scroll":
//								map.setScroll (split[1]);
//								break;
//
//							case "image": {
//
//								String image = new File(file.getParent(), split[1]).getPath();
//
//								map.setImage (new File (image));
//
//								if (!map.getImage ().exists ()){
//
//									Alert alert = new Alert (Alert.AlertType.ERROR);
//
//									alert.setContentText (image +" doesn't exist!");
//
//									alert.show ();
//								}
//								break;
//							}
//
//							case "wrap":
//								map.setWrap (split[1].trim ().toLowerCase ().equals ("yes"));
//								break;
//
//							case "warn":
//								map.setWarn (split[1].trim ().toLowerCase ().equals ("yes"));
//								break;
//
//							default:
//								break;
//						}
//
//						break;
//
//					}
//
//					case "[continents]": {
//
//						String[] str = s.split ("=");
//
//						try {
//
//							Continent continent = new Continent (str[0], Integer.parseInt (str[1]));
//
//							LoadUtil.addContinent (continent);
//
//						} catch (Exception e) {
//
//							e.printStackTrace ();
//						}
//
//						break;
//					}
//
//					case "[territories]": {
//
//						String[] str = s.split (",");
//
//						try {
//
//							int x = Integer.parseInt (str[1]);
//
//							int y = Integer.parseInt (str[2]);
//
//							Country country;
//
//							if (LoadUtil.getCountry (str[0]) != null) {
//
//								country = LoadUtil.getCountry (str[0]);
//
//								country.setCoordinator (new Coordinator (x, y));
//
//							} else country = new Country (str[0], new Coordinator (x, y));
//
//							String continentName = str[3];
//
//							Continent continent = LoadUtil.getContinent (continentName);
//
//							if (continent == null)
//								throw new IllegalArgumentException ("Continent is invalid for: " + s);
//
//							continent.getCountries ().add (country);
//
//							country.setContinent (continent);
//
//							LoadUtil.addContinent (continent);
//
//							for (int i = 4; i < str.length; i++) {
//
//								Country adjacent = LoadUtil.getCountry (str[i]);
//
//								if (adjacent != null) {
//
//									country.getAdjacentCountry ().add (adjacent);
//
//								} else {
//
//									LoadUtil.addCountry (new Country (str[i]));
//
//									country.getAdjacentCountry ().add (LoadUtil.getCountry (str[i]));
//								}
//							}
//
//							LoadUtil.addCountry (country);
//
//						} catch (Exception e) {
//
//							Alert alert = new Alert (Alert.AlertType.ERROR);
//
//							alert.setContentText (e.getMessage ());
//
//							alert.show ();
//						}
//
//						break;
//					}
//
//					default: break;
//				}
//			}
//		});
//
//		try {
//			LoadUtil.validateConnected ();
//
//			LoadUtil.validateContinent ();
//
//			LoadUtil.validateCountry ();
//
//			map.setContinents (LoadUtil.getAllContinents ());
//
//			map.setTerritories (LoadUtil.getAllCountry ());
//
//
//		} catch (Exception e) {
//
//			Alert alert = new Alert (Alert.AlertType.ERROR);
//
//			alert.setContentText (e.getMessage ());
//
//			alert.show ();
//		}
//	}
//
//	/**
//	 * Method to save a .map file
//	 *
//	 * @param PathOut is the file to be save.
//	 *
//	 * @throws IOException if encounters IO error.
//	 */
//	public static void saveFile (String PathOut) throws IOException {
//
//		GameMap map = GameMap.getInstance ();
//
//		FileWriter fw;
//
//		BufferedWriter bufw;
//		try {
//
//			fw = new FileWriter (PathOut);
//
//			bufw = new BufferedWriter (fw);
//
//
//			bufw.write ("[Map]" + "\n");
//
//			bufw.write ("author=" + map.getAuthor () + "\n");
//
//			bufw.write ("warn=" + map.getWarn () + "\n");
//
//			bufw.write ("image=" + map.getImage () + "\n");
//
//			bufw.write ("wrap=" + map.getWarn () + "\n");
//
//			bufw.write ("scroll=" + map.getScroll () + "\n");
//
//			bufw.write ("\n");
//
//			List<Continent> continents = map.getContinents ();
//
//			bufw.write ("[continents]" + "\n");
//
//			for (Continent continent : continents) {
//
//				bufw.write (continent.getName () + "=" + continent.getArmy () + "\n");
//
//				bufw.write (continent.getName () + "=" + continent.getArmy () + "\n");
//			}
//
//			bufw.write ("\n");
//
//			bufw.write ("[Territories]" + "\n");
//
//			List<Country> territories = map.getTerritories ();
//
//			LinkedList<String> str = new LinkedList<> ();
//
//			for (Country country : territories) {
//
//				Set<Country> adjacentCountry = country.getAdjacentCountry ();
//
//				StringBuilder strAdjacentCountry = new StringBuilder ();
//
//				for (Country countryAdj : adjacentCountry) {
//
//					strAdjacentCountry.append (",");
//
//					strAdjacentCountry.append (countryAdj.getName ());
//				}
//
//					str.add (country.getName () + "," + country.getCoordinator ().getX () + "," +
//						         country.getCoordinator ().getY () + ","
//						         + country.getContinent ().getName () + strAdjacentCountry);
//			}
//
//			for (Continent continent : continents) {
//
//				String strContinents = continent.getName ();
//
//				for (String aStr : str) {
//
//					if (aStr.contains (strContinents)) {
//
//						bufw.write (aStr + "\n");
//					}
//
//					bufw.flush ();
//				}
//				bufw.write ("\n");
//
//			}
//
//		} catch (IOException e) {
//
//			e.printStackTrace ();
//
//		}
//	}
//
//	/**
//	 * Validate the map is a connected graph.
//	 */
//	private static void validateConnected  ()  { //You can run DFS on any arbitrary node
//
//		Set<Country> countries = new HashSet<> ();
//
//		Country country = countryMap.values ().stream ().findAny ().orElse (null);
//
//		dfs (country,countries);
//
//		for( Country c: countryMap.values ()){
//
//			if (!countries.contains (c)) throw new IllegalArgumentException (c.getName ()+" is not connected.");
//		}
//
//	}
//
//	/**
//	 * Run the DFS algorithm for map
//	 * @param c is the start country.
//	 * @param countries is the set of countries.
//	 */
//	private static void dfs (Country c, Set<Country> countries)
//	{
//		countries.add (c) ;
//
//		for(Country adj: c.getAdjacentCountry ())
//
//			if(!countries.contains (adj)) dfs(adj,countries);
//	}
}
