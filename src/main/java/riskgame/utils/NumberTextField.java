package riskgame.utils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * NumberTextField class is to show the changed number
 */
public class NumberTextField extends TextField {

    private final NumberFormat nf;

    private int min = 0;

    private int max = Integer.MAX_VALUE;

    private final ObjectProperty<Integer> number = new SimpleObjectProperty<>();


    public NumberTextField() {

        this(0);
    }
     /**
     * constructor of class NumberTextField
     * @param : Integer value
     */
     public NumberTextField(Integer value) {

        this(value, NumberFormat.getInstance());
      
        initHandlers();
    }
     /**
     * constructor of class NumberTextField
     * @param :Integer value
     * @param :NumberFormat nf
     */
    private NumberTextField(Integer value, NumberFormat nf) {

        super();

        this.nf = nf;

        initHandlers();

        setNumber(value);
    } 
    /**
     * param value get Number
     *@return number
     */

    public final Integer getNumber() {

        return number.get();
    }
    /**
     * the method of set number 
     * param value set new value with type integer
     */
    public final void setNumber(Integer value) {
      
        number.set(value);
    }
    /**
     * the method of objectProperty
     * @return number is the Property 
     */
    private ObjectProperty<Integer> numberProperty() {

        return number;
    }
    /**
     * the  method to initial the Handlers
     */
    private void initHandlers() {

        // try to parse when focus is lost or RETURN is hit
        setOnAction((ActionEvent e) -> parseAndFormatInput());

        focusedProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue) parseAndFormatInput();
        });

        // Set text in field if Integer property is changed from outside.
        numberProperty().addListener((observable, oldValue, newValue) -> setText(nf.format(newValue)));
    }

    /**
     * Tries to parse the user input to a number according to the provided
     * NumberFormat
     */
    private void parseAndFormatInput() {

        try {

            String input = getText();

            if (input == null || input.length() == 0) {
                return;
            }

            Number parsedNumber = nf.parse(input);

            Integer newValue = Integer.valueOf(parsedNumber.toString());

            setNumber(newValue);

            selectAll();

        } catch (ParseException ex) {

            setText(null);

        }
    }
    /**
     * set the minimum and the maximum number
     * @param min the minimum number
     *        max the minimum number
     */
    public void setRange(int min, int max) {

        this.min = min;

        this.max = max;
    }
     /**
     * if the number is valid
     * @return true the number is valid
     *        fault the number is not valid
     */
    public boolean isOutRange() {

        return this.number.getValue() > max || this.number.getValue() < min;
    }
    /**
     * get the rang of number
     * @return a string that show the range
     */
    public String getRange() {

        return "[" + min + ", " + max + "]";
    }
}
