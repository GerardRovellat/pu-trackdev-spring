package org.udg.trackdev.spring.query;

/**
 * The enum Search operation.
 */
public enum SearchOperation {
    /**
     * Equality search operation.
     */
    EQUALITY,
    /**
     * Negation search operation.
     */
    NEGATION,
    /**
     * Greater than search operation.
     */
    GREATER_THAN,
    /**
     * Less than search operation.
     */
    LESS_THAN,
    /**
     * Like search operation.
     */
    LIKE,
    /**
     * Starts with search operation.
     */
    STARTS_WITH,
    /**
     * Ends with search operation.
     */
    ENDS_WITH,
    /**
     * Contains search operation.
     */
    CONTAINS;

    /**
     * The constant SIMPLE_OPERATION_SET.
     */
    public static final String[] SIMPLE_OPERATION_SET = { ":", "!", ">", "<", "~" };

    /**
     * The constant OR_PREDICATE_FLAG.
     */
    public static final String OR_PREDICATE_FLAG = "'";

    /**
     * The constant ZERO_OR_MORE_REGEX.
     */
    public static final String ZERO_OR_MORE_REGEX = "*";

    /**
     * The constant NO_VALUE.
     */
    public static final String NO_VALUE = "NULL";

    /**
     * The constant OR_OPERATOR.
     */
    public static final String OR_OPERATOR = "OR";

    /**
     * The constant AND_OPERATOR.
     */
    public static final String AND_OPERATOR = "AND";

    /**
     * The constant LEFT_PARANTHESIS.
     */
    public static final String LEFT_PARANTHESIS = "(";

    /**
     * The constant RIGHT_PARANTHESIS.
     */
    public static final String RIGHT_PARANTHESIS = ")";

    /**
     * Gets simple operation.
     *
     * @param input the input
     * @return the simple operation
     */
    public static SearchOperation getSimpleOperation(final char input) {
        switch (input) {
            case ':':
                return EQUALITY;
            case '!':
                return NEGATION;
            case '>':
                return GREATER_THAN;
            case '<':
                return LESS_THAN;
            case '~':
                return LIKE;
            default:
                return null;
        }
    }
}
