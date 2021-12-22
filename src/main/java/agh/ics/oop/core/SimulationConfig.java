package agh.ics.oop.core;

public class SimulationConfig {

    public final Rect mapArea;
    public final Rect jungleArea;

    public final int initialAnimals;
    public final boolean initialAnimalsInJungle;

    public final int startEnergy;
    public final int moveEnergy;
    public final int plantEnergy;
    public final int minBreedingEnergy;

    public final boolean isMagic;

    public static SimulationConfig parse(String mapWidth,
                                         String mapHeight,
                                         String jungleRatio,
                                         String initialAnimals,
                                         String startEnergy,
                                         String moveEnergy,
                                         String plantEnergy,
                                         boolean isMagic) {
        return new SimulationConfig(
                Integer.parseUnsignedInt(mapWidth),
                Integer.parseUnsignedInt(mapHeight),
                Integer.parseUnsignedInt(jungleRatio),
                Integer.parseUnsignedInt(initialAnimals),
                Integer.parseUnsignedInt(startEnergy),
                Integer.parseUnsignedInt(moveEnergy),
                Integer.parseUnsignedInt(plantEnergy),
                isMagic
        );
    }

    public SimulationConfig(int mapWidth,
                            int mapHeight,
                            int jungleRatio,
                            int initialAnimals,
                            int startEnergy,
                            int moveEnergy,
                            int plantEnergy,
                            boolean isMagic) {
        if (mapWidth < 1 || mapHeight < 1) {
            throw new IllegalArgumentException("Map width/height must be > 0");
        }

        if (jungleRatio > 100 || jungleRatio < 0) {
            throw new IllegalArgumentException("Jungle ratio must be in range [0, 100]");
        }

        if (initialAnimals < 1) {
            throw new IllegalArgumentException("Initial animals must be > 0");
        }

        if (startEnergy < 1) {
            throw new IllegalArgumentException("Start energy must be > 0");
        }

        if (moveEnergy < 1) {
            throw new IllegalArgumentException("Move energy must be > 0");
        }

        if (plantEnergy < 1) {
            throw new IllegalArgumentException("Plant energy must be > 0");
        }

        this.mapArea = new Rect(0, 0, mapWidth, mapHeight);

        int jungleW = Math.round(mapWidth * (jungleRatio / 100.f));
        int jungleH = Math.round(mapHeight * (jungleRatio / 100.f));
        this.jungleArea = new Rect(
                mapWidth / 2 - jungleW / 2,
                mapHeight / 2 - jungleH / 2,
                mapWidth / 2 - jungleW / 2 + jungleW,
                mapHeight / 2 - jungleH / 2 + jungleH
        );

        if (initialAnimals > mapArea.area()) {
            throw new IllegalArgumentException("Too many animals to fit on map");
        }

        this.initialAnimals = initialAnimals;
        this.initialAnimalsInJungle = false;

        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.minBreedingEnergy = startEnergy / 2;

        this.isMagic = isMagic;
    }
}
