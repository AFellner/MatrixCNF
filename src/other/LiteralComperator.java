package other;

import java.util.Comparator;

import formula.*;

/**
 * LiteralComperator.java
 * Purpose: Comperator class for literals.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */

public class LiteralComperator implements Comparator<Literal> {
    public int compare(Literal o1, Literal o2) {
        return o1.compareTo(o2);
    }
}