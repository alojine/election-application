package com.President.Election.enums;

public enum Region {
    CALIFORNIA,
    TEXAS,
    FLORIDA,
    OHIO,
    INDIANA;

    public static Region isValidRegion(String providedRegion) throws Exception {
        for(Region realRegion : values()) {
            if(realRegion.name().equalsIgnoreCase(providedRegion)){
                return realRegion;
            }
        }

        throw new Exception("No exact region found. Wrong region was provided");
    }
}
