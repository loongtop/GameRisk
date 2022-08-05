package riskgame.model.impl;

import com.google.common.collect.ImmutableList;
import lombok.*;
import riskgame.model.IMap;
import riskgame.model.Coordinator;
import riskgame.utils.RiskAlert;

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

    @Setter
    private ImmutableList<Continents.Continent> continentList;
    @Setter
    private ImmutableList<Countries.Country> countriesList;

    // as the intermediate variables
    @Getter(value = AccessLevel.PRIVATE)
    private Continents continentsTemp;
    @Getter(value = AccessLevel.PRIVATE)
    private Countries countriesTemp;

//////////////////////////method///////////////////////////////////////////////
    public void mapLoader(@NonNull File file) {

        continentsTemp = new Continents(new HashMap<>());
        countriesTemp = new Countries(new HashMap<>());

//        new Thread(() -> { mapLoaderThread(file);}).start();
        mapLoaderThread(file);

        continentsTemp = null;
        countriesTemp = null;
    }

    private void mapLoaderThread(@NonNull File file) {
        //read file
        Stream<String> mapFile = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            mapFile = bufferedReader.lines();
        } catch (Exception e) {
            RiskAlert alert = new RiskAlert(e.getMessage());
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
        if (countriesTemp.validate() && continentsTemp.validate()){
            continentList = ImmutableList.copyOf(continentsTemp.getAllValues());
            countriesList = ImmutableList.copyOf(countriesTemp.getAllValues());
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
                    RiskAlert alert = new RiskAlert(imagePath + " Image doesn't exist!");
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
                    .countries(new HashSet<>())
                    .build();
            continentsTemp.add(continent.getName(), continent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleTerritories(String finalS) {
        String[] territory = finalS.split(",");

        Coordinator coordinator = new Coordinator(Integer.parseInt(territory[1]), Integer.parseInt(territory[2]));
        Continents.Continent continent = continentsTemp.get(territory[3]);
        Countries.Country country = countriesTemp.get(territory[0]);

        if (country != null) {
            country.setContinent(continent);
            country.setCoordinator(coordinator);
        }else {
            country = Countries.Country.builder()
                    .name(territory[0])
                    .coordinator(coordinator)
                    .continent(continent)
                    .adjacentCountry(new HashSet<>())
                    .build();

            countriesTemp.add(country.getName(), country);
        }
        continent.getCountries().add(country);

        Countries.Country adjacent;
        for (int i = 4; i < territory.length; i++) {
            adjacent = countriesTemp.get(territory[i]);
            if (adjacent == null) {
                adjacent = Countries.Country.builder()
                        .name(territory[i])
                        .adjacentCountry(new HashSet<>())
                        .build();
                countriesTemp.add(adjacent.getName(), adjacent);
            }
            country.getAdjacentCountry().add(adjacent);
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
            for (Continents.Continent continent : continentList) {
                bufw.write(continent.getName() + "=" + continent.getArmy() + "\n");
                bufw.write(continent.getName() + "=" + continent.getArmy() + "\n");
            }
            bufw.write("\n");
            bufw.write("[Territories]" + "\n");

            LinkedList<String> str = new LinkedList<>();
            for (Countries.Country country : countriesList) {
                Set<Countries.Country> adjacentCountry = country.getAdjacentCountry();
                StringBuilder strAdjacentCountry = new StringBuilder();
                for (Countries.Country countryAdj : adjacentCountry) {
                    strAdjacentCountry.append(",");
                    strAdjacentCountry.append(countryAdj.getName());
                }
                str.add(country.getName() + "," + country.getCoordinator().getX() + "," + country.getCoordinator().getY() + "," + country.getContinent().getName() + strAdjacentCountry);
            }

            for (Continents.Continent continent : continentList) {
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
