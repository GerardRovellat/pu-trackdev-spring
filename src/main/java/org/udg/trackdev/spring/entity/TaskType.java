package org.udg.trackdev.spring.entity;

/**
 * The enum Task type.
 */
public enum TaskType {
    /**
     * The User story.
     */
    USER_STORY("HISTORIA D'USUARI"),
    /**
     * Task task type.
     */
    TASK("TASCA"),
    ;

    private final String text;

    /**
     * @param text
     */
    TaskType(final String text) {
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
