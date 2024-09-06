package org.udg.trackdev.spring.entity.enums;

/**
 * The enum Task status.
 */
public enum TaskStatus {
    /**
     * Backlog task status.
     */
    BACKLOG("BACKLOG"),
    /**
     * Todo task status.
     */
    TODO("PRIORITZADA"),
    /**
     * The Inprogress.
     */
    INPROGRESS("EN PROGRES"),
    /**
     * The Verify.
     */
    VERIFY("EN VERIFICACIO"),
    /**
     * Done task status.
     */
    DONE("FINALITZADA"),
    /**
     * Defined task status.
     */
    DEFINED("DEFINIDA"),
    ;

    private final String text;
    TaskStatus(final String text) {
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