package org.udg.trackdev.spring.query;

import com.google.common.base.Joiner;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Criteria parser.
 */
public class CriteriaParser {

    private static Map<String, Operator> ops;

    private static Pattern SpecCriteraRegex = Pattern.compile("^(\\w+?)(" + Joiner.on("|")
            .join(SearchOperation.SIMPLE_OPERATION_SET) + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?)$");

    private enum Operator {
        /**
         * Or operator.
         */
        OR(1),
        /**
         * And operator.
         */
        AND(2);
        /**
         * The Precedence.
         */
        final int precedence;

        Operator(int p) {
            precedence = p;
        }
    }

    static {
        Map<String, Operator> tempMap = new HashMap<>();
        tempMap.put("AND", Operator.AND);
        tempMap.put("OR", Operator.OR);
        tempMap.put("or", Operator.OR);
        tempMap.put("and", Operator.AND);

        ops = Collections.unmodifiableMap(tempMap);
    }

    private static boolean isHigerPrecedenceOperator(String currOp, String prevOp) {
        return (ops.containsKey(prevOp) && ops.get(prevOp).precedence >= ops.get(currOp).precedence);
    }

    /**
     * Parse deque.
     *
     * @param searchParam the search param
     * @return the deque
     */
    public Deque<?> parse(String searchParam) {

        Deque<Object> output = new LinkedList<>();
        Deque<String> stack = new LinkedList<>();

        Arrays.stream(searchParam.split("\\s+")).forEach(token -> {
            if (ops.containsKey(token)) {
                while (!stack.isEmpty() && isHigerPrecedenceOperator(token, stack.peek()))
                    output.push(stack.pop()
                            .equalsIgnoreCase(SearchOperation.OR_OPERATOR) ? SearchOperation.OR_OPERATOR : SearchOperation.AND_OPERATOR);
                stack.push(token.equalsIgnoreCase(SearchOperation.OR_OPERATOR) ? SearchOperation.OR_OPERATOR : SearchOperation.AND_OPERATOR);
            } else if (token.equals(SearchOperation.LEFT_PARANTHESIS)) {
                stack.push(SearchOperation.LEFT_PARANTHESIS);
            } else if (token.equals(SearchOperation.RIGHT_PARANTHESIS)) {
                while (!stack.peek()
                        .equals(SearchOperation.LEFT_PARANTHESIS))
                    output.push(stack.pop());
                stack.pop();
            } else {

                Matcher matcher = SpecCriteraRegex.matcher(token);
                while (matcher.find()) {
                    output.push(new SpecSearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5)));
                }
            }
        });

        while (!stack.isEmpty())
            output.push(stack.pop());

        return output;
    }

}

