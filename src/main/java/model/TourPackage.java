package model;

public enum TourPackage {
    BACKPACK_CAL("Backpack Cal"),
    CALIFORNIA_CALM("California Calm"),
    CALIFORNIA_HOT_SPRINGS("California Hot springs"),
    CYCLE_CALIFORNIA("Cycle California"),
    FROM_DESERT_TO_SEA("From Desert to Sea"),
    KIDS_CALIFORNIA("Kids California"),
    NATURE_WATCH("Nature Watch"),
    SNOWBOARD_CALI("Snowboard Cali"),
    TASTE_OF_CALIFORNIA("Taste of California");

    private String label;

    TourPackage(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
