package com.camel.cup;

import java.util.ArrayList;
import java.util.List;

/**
 * User: jan-marc
 * Date: 21.01.15
 * Time: 03:16
 */
class SlowestPosition {

    private final List<CamelFinalCard> slowestPosition = new ArrayList<>();

    public List<CamelFinalCard> getSlowestPosition() {
        return slowestPosition;
    }

    public void setSlowestPosition(CamelFinalCard slowestPosition) {
        this.slowestPosition.add(slowestPosition);
    }


}
