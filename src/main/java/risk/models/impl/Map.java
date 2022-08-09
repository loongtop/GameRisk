package risk.models.impl;

import lombok.*;
import risk.models.IMap;
import risk.models.Coordinator;
import risk.utils.AlertError;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Map implements IMap {
    INSTANCE;
    private static final long serialVersionUID = 3162242848938299845L;

    @NonNull
    private final Map.Data mapData = new Data();

    // as the intermediate variables
    @Getter
    private Continents continentsMap = new Continents(new HashMap<>());
    @Getter
    private Countries countriesMap = new Countries(new HashMap<>());

//////////////////////////method///////////////////////////////////////////////
    public void mapLoader(File file) {

        resetMap();
        new Thread(() -> { mapLoaderThread(file);}).start();
//        mapLoaderThread(file);
    }

    private void mapLoaderThread(File file) {
        //read file
        Stream<String> mapFile = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            mapFile = bufferedReader.lines();
        } catch (Exception e) {
            AlertError alert = new AlertError(e.getMessage());
            alert.showAlert();
        }

        //handle file, assign the  map value to the intermediate variable < continentsTemp, countriesTemp >
        AtomicReference<String> header = new AtomicReference<>("");
        List headers = List.of("[Map]", "[Continents]", "[Territories]");
        mapFile.filter(l -> !"".equals(l.trim()))
                .forEach(s -> {
                    String finalS = s.trim();
                    if (headers.stream().anyMatch(m -> m.equals(finalS))) {
                        header.set(finalS);
                    } else {
                        handleMapFile(header.get(), finalS, file);
                    }
                });

        //check the input information is validate or not
        if (!continentsMap.validate() || !countriesMap.validate()){
            throw new IllegalStateException("Map Check fail!");
        }
    }

    private void handleMapFile(String label, String finalS, File file) {

        switch (label) {
            case "[Map]": {
                handleMap(finalS, file);
                break;
            }
            case "[Continents]": {
                handleContinents(finalS);
                break;
            }
            case "[Territories]": {
                handleTerritories(finalS);
                break;
            }
            default:
                break;
        }
    }

    private void handleMap(String finalS, File file) {
        String[] split = finalS.split("=");
        switch (split[0].trim()) {
            case "author":
                mapData.setAuthor(split[1]);
                break;
            case "scroll":
                mapData.setScroll (split[1]);
                break;
            case "image":
                mapData.setPathConfigFile(file.toString());
                StringBuilder imagePath = new StringBuilder().append(file.getParent()).append("/").append(split[1]);
                mapData.setImageFile(new File (imagePath.toString()));
                if (!mapData.getImageFile().exists()) {
                    AlertError alert = new AlertError(imagePath + " Image doesn't exist!");
                    alert.showAlert();
                }
                break;
            case "wrap":
                mapData.setWrap("yes".equalsIgnoreCase(split[1].trim()));
                break;
            case "warn":
                mapData.setWarn("yes".equalsIgnoreCase(split[1].trim()));
                break;
            default:
                break;
        }
    }

    private void handleContinents(String finalS) {
        String[] str = finalS.split("=");
        try {
            Continents.Continent continent = Continents.Continent.builder()
                    .name(str[0])
                    .army(Integer.parseInt(str[1]))
                    .countries(new HashMap<String, Countries.Country>())
                    .build();
            continentsMap.add(continent.getName(), continent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleTerritories(String finalS) {
        String[] territory = finalS.split(",");

        Coordinator coordinator = new Coordinator(Integer.parseInt(territory[1]), Integer.parseInt(territory[2]));
        Continents.Continent continent = continentsMap.get(territory[3]);
        Countries.Country country = countriesMap.get(territory[0]);

        if (country != null) {
            country.setContinent(continent);
            country.setCoordinator(coordinator);
        }else {
            country = Countries.Country.builder()
                    .name(territory[0])
                    .coordinator(coordinator)
                    .continent(continent)
                    .adjacentCountry(new HashMap<String, Countries.Country>())
                    .build();

            countriesMap.add(country.getName(), country);
        }
        continent.getCountries().put(country.getName(), country);

        Countries.Country adjacent;
        for (int i = 4; i < territory.length; i++) {
            adjacent = countriesMap.get(territory[i]);
            if (adjacent == null) {
                adjacent = Countries.Country.builder()
                        .name(territory[i])
                        .adjacentCountry(new HashMap<String, Countries.Country>())
                        .build();
                countriesMap.add(adjacent.getName(), adjacent);
            }
            country.getAdjacentCountry().put(adjacent.getName(), adjacent);
        }
    }

    /**
     * Method to save a .map file
     *
     * @param PathOut : is the file to be save.
     * @throws : IOException if encounters IO error.
     */
    public void saveMap(String PathOut) {

        try (FileWriter fw = new FileWriter(PathOut);
             BufferedWriter bufw = new BufferedWriter(fw)) {

            bufw.write("[Map]" + "\n");
            bufw.write("author=" + mapData.getAuthor() + "\n");
            bufw.write("warn=" + mapData.getWarn() + "\n");
            bufw.write("image=" + mapData.getImageFile().getPath() + "\n");
            bufw.write("wrap=" + mapData.getWarn() + "\n");
            bufw.write("scroll=" + mapData.getScroll() + "\n");
            bufw.write("\n");

            bufw.write("[continents]" + "\n");
            for (Continents.Continent continent : continentsMap.getAllValues()) {
                bufw.write(continent.getName() + "=" + continent.getArmy() + "\n");
                bufw.write(continent.getName() + "=" + continent.getArmy() + "\n");
            }
            bufw.write("\n");
            bufw.write("[Territories]" + "\n");

            LinkedList<String> str = new LinkedList<>();
            for (Countries.Country country : countriesMap.getAllValues()) {
                HashMap<String, Countries.Country> adjacentCountry = country.getAdjacentCountry();
                StringBuilder strAdjacentCountry = new StringBuilder();
                for (Countries.Country countryAdj : adjacentCountry.values()) {
                    strAdjacentCountry.append(",");
                    strAdjacentCountry.append(countryAdj.getName());
                }
                str.add(country.getName() + "," + country.getCoordinator().getX() + "," + country.getCoordinator().getY() + "," + country.getContinent().getName() + strAdjacentCountry);
            }

            for (Continents.Continent continent : continentsMap.getAllValues()) {
                String strContinents = continent.getName();
                for (String aStr : str) {
                    if (aStr.contains(strContinents)) {
                        bufw.write(aStr + "\n");
                    }
                    bufw.flush();
                }
                bufw.write("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetMap() {
        if (mapData != null || continentsMap != null || countriesMap != null) {
            Map.Data mapData = new Data();
            continentsMap = new Continents(new HashMap<String, Continents.Continent>());
            countriesMap = new Countries(new HashMap<String, Countries.Country>());
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Data implements Serializable {

        private static final long serialVersionUID = 1615220989857339983L;

        private String author;
        private String scroll;
        private Boolean warn;
        private Boolean wrap;
        private String pathConfigFile;
        private File imageFile;
    }
}
