package example.gamerisk.utils;

import example.gamerisk.model.Continent;
import example.gamerisk.model.Coordinator;
import example.gamerisk.model.Country;
import example.gamerisk.model.GameMap;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

public class FileOP {
    private static String label = "";

    public static void readFile (File file) throws IOException{
        GameMap map = GameMap.getInstance ();
        BufferedReader bufferedReader = new BufferedReader (new FileReader(file));
        Stream<String> mapFile = bufferedReader.lines ();
        mapFile = mapFile.filter (l -> ! "".equals (l.trim ()));
        mapFile.forEach (s -> {
            s = s.trim ();

            if (s.equals ("[Map]") || s.equals ("[Territories]") || s.equals ("[Continents]")) {

                label = s;

            } else {

                switch (label.toLowerCase ()) {

                    case "[map]": {

                        String[] split = s.split ("=");

                        switch (split[0].trim ()) {

                            case "author":
                                map.setAuthor (split[1]);
                                break;
                            case "scroll":
                                map.setScroll (split[1]);
                                break;
                            case "image": {
                                String image = new File(file.getParent(), split[1]).getPath();
                                map.setImage (new File (image));
                                if (!map.getImage ().exists ()){
                                    Alert alert = new Alert (Alert.AlertType.ERROR);
                                    alert.setContentText (image +" doesn't exist!");
                                    alert.show ();
                                }
                                break;
                            }
                            case "wrap":
                                map.setWrap (split[1].trim ().equalsIgnoreCase("yes"));
                                break;
                            case "warn":
                                map.setWarn (split[1].trim ().equalsIgnoreCase("yes"));
                                break;
                            default:
                                break;
                        }
                        break;
                    }
                    case "[continents]": {
                        String[] str = s.split ("=");
                        try {
                            Continent continent = new Continent (str[0], Integer.parseInt (str[1]));
                            LoadUtil.addContinent (continent);
                        } catch (Exception e) {
                            e.printStackTrace ();
                        }
                        break;
                    }
                    case "[territories]": {
                        String[] str = s.split (",");
                        try {
                            int x = Integer.parseInt (str[1]);
                            int y = Integer.parseInt (str[2]);
                            Country country;
                            if (LoadUtil.getCountry (str[0]) != null) {

                                country = LoadUtil.getCountry (str[0]);

                                assert country != null;
                                country.setCoordinator (new Coordinator(x, y));

                            } else country = new Country (str[0], new Coordinator (x, y));

                            String continentName = str[3];

                            Continent continent = LoadUtil.getContinent (continentName);

                            if (continent == null)
                                throw new IllegalArgumentException ("Continent is invalid for: " + s);

                            continent.getCountries ().add (country);

                            country.setContinent (continent);

                            LoadUtil.addContinent (continent);

                            for (int i = 4; i < str.length; i++) {

                                Country adjacent = LoadUtil.getCountry (str[i]);

                                if (adjacent != null) {

                                    country.getAdjacentCountry ().add (adjacent);

                                } else {

                                    LoadUtil.addCountry (new Country(str[i]));

                                    country.getAdjacentCountry ().add (LoadUtil.getCountry (str[i]));
                                }
                            }

                            LoadUtil.addCountry (country);

                        } catch (Exception e) {

                            Alert alert = new Alert (Alert.AlertType.ERROR);

                            alert.setContentText (e.getMessage ());

                            alert.show ();
                        }

                        break;
                    }

                    default: break;
                }
            }
        });

        try {
            LoadUtil.validateConnected ();
            LoadUtil.validateContinent ();
            LoadUtil.validateCountry ();
            map.setContinents (LoadUtil.getAllContinents ());
            map.setTerritories (LoadUtil.getAllCountry ());
        } catch (Exception e) {

            Alert alert = new Alert (Alert.AlertType.ERROR);

            alert.setContentText (e.getMessage ());

            alert.show ();
        }
    }
}
