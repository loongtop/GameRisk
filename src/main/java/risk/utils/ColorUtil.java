package risk.utils;

import javafx.scene.paint.Color;

/**
 * ColorUtil class is used to show the different player and  continent
 */
public class ColorUtil {


    private static final Color[] continentColor = {

            Color.ALICEBLUE,

            Color.BLANCHEDALMOND,

            Color.BURLYWOOD,

            Color.CORAL,

            Color.VIOLET,

            Color.SEAGREEN,

            Color.TOMATO,

            Color.ANTIQUEWHITE
    };

    /**
     * a list of colors for players
     */
    private static final Color[] playerColor = {

            Color.ORANGE,

            Color.RED,

            Color.YELLOW,

            Color.GREEN,

            Color.BLUE,


            Color.PURPLE
    };
  /**
	 * get the ContinentColor
	 * @param a  input number means different color int the continentColor array
	 * @return  null means that the input is not in the color array,
	 * 			ALICEBLUE,ALICEBLUE,BLANCHEDALMOND,BURLYWOOD,CORAL,VIOLET,SEAGREEN,TOMATO,ANTIQUEWHITE
	 * 			means that color is in the color array
	 */

    public static Color getContinentColor(int a) {
    
        if (a >= 0 && a < continentColor.length) return continentColor[a];

        else return null;
    }

	/**
	 * get the ContinentColor
	 * @param a  input number means different color int the continentColor array
	 * @return  null means that the input is not in the color array
	 * 			Color.ORANGE,.RED,YELLOW,GREEN,BLUE,PURPLE
	 * 			means that color is in the color array
	 */
	public static Color getPlayerColor(int a){

		if  ( a >=0 && a < playerColor.length) return continentColor[a];
        else return null;
    }
}
