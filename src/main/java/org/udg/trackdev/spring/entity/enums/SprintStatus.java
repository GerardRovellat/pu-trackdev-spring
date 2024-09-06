package org.udg.trackdev.spring.entity.enums;

/**
 * The enum Sprint status.
 */
public enum SprintStatus {
    /**
     * Draft sprint status.
     */
    DRAFT("PLANIFICAT"),
    /**
     * Active sprint status.
     */
    ACTIVE("ACTIU"),
    /**
     * Closed sprint status.
     */
    CLOSED("TANCAT"),
    ;

    private final String text;
    SprintStatus(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}