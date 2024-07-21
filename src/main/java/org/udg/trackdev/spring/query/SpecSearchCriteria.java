package org.udg.trackdev.spring.query;

/**
 * The type Spec search criteria.
 */
public class SpecSearchCriteria {

    private String key;
    private SearchOperation operation;
    private Object value;
    private boolean orPredicate;

    /**
     * Instantiates a new Spec search criteria.
     */
    public SpecSearchCriteria() {

    }

    /**
     * Instantiates a new Spec search criteria.
     *
     * @param key       the key
     * @param operation the operation
     * @param value     the value
     */
    public SpecSearchCriteria(final String key, final SearchOperation operation, final Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    /**
     * Instantiates a new Spec search criteria.
     *
     * @param orPredicate the or predicate
     * @param key         the key
     * @param operation   the operation
     * @param value       the value
     */
    public SpecSearchCriteria(final String orPredicate, final String key, final SearchOperation operation, final Object value) {
        super();
        this.orPredicate = orPredicate != null && orPredicate.equals(SearchOperation.OR_PREDICATE_FLAG);
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    /**
     * Instantiates a new Spec search criteria.
     *
     * @param key       the key
     * @param operation the operation
     * @param prefix    the prefix
     * @param value     the value
     * @param suffix    the suffix
     */
    public SpecSearchCriteria(String key, String operation, String prefix, String value, String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
        }
        if(value.equals(SearchOperation.NO_VALUE)) {
            value = null;
        }
        this.key = key;
        this.operation = op;
        this.value = value;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets key.
     *
     * @param key the key
     */
    public void setKey(final String key) {
        this.key = key;
    }

    /**
     * Gets operation.
     *
     * @return the operation
     */
    public SearchOperation getOperation() {
        return operation;
    }

    /**
     * Sets operation.
     *
     * @param operation the operation
     */
    public void setOperation(final SearchOperation operation) {
        this.operation = operation;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(final Object value) {
        this.value = value;
    }

    /**
     * Is or predicate boolean.
     *
     * @return the boolean
     */
    public boolean isOrPredicate() {
        return orPredicate;
    }

    /**
     * Sets or predicate.
     *
     * @param orPredicate the or predicate
     */
    public void setOrPredicate(boolean orPredicate) {
        this.orPredicate = orPredicate;
    }

}

