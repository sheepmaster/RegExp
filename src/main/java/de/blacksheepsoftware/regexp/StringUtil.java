package de.blacksheepsoftware.regexp;

/**
 * @author <a href="bauerb@in.tum.de">Bernhard Bauer</a>
 *
 */
public class StringUtil {
    private StringUtil() {}

    public static boolean matches(RegularExpression<Character> e, String s) {
        for (char c : s.toCharArray()) {
            e = e.derivative(c);
        }
        return e.containsEpsilon();
    }

    public static RegularExpression<Character> characterClass(String s) {
        RegularExpression<Character> e = RegularExpression.emptySet();
        for (char c : s.toCharArray()) {
            e = new Sum<Character>(e, new Literal<Character>(c));
        }
        return e.simplify();
    }

    public static RegularExpression<Character> stringMatch(String s) {
        RegularExpression<Character> e = RegularExpression.epsilon();
        for (char c : s.toCharArray()) {
            e = new Product<Character>(e, new Literal<Character>(c));
        }
        return e.simplify();
    }
}
