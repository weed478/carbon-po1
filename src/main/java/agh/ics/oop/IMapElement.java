package agh.ics.oop;

/**
 * Map element with a graphical representation.
 */
public interface IMapElement {

    /**
     * Get asset resource path representing this element.
     */
    String assetName();

    /**
     * Get a label representing this element.
     */
    String mapLabel();
}
