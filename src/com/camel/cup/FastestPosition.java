package com.camel.cup;

import java.util.ArrayList;
import java.util.List;

class FastestPosition {


    private final List<CamelFinalCard> fastestPosition = new ArrayList<>();

    public List<CamelFinalCard> getFastestPosition() {
        return fastestPosition;
    }

    public void setFastestPosition(CamelFinalCard fastestPosition) {
        this.fastestPosition.add(fastestPosition);
    }


}
