package de.blacksheepsoftware.regexp;

/**
 * @author <a href="bauerb@in.tum.de">Bernhard Bauer</a>
 *
 */
public class Util {
    private Util() {}

    public static <T> RegularExpression<T> sum(RegularExpression<T> left, RegularExpression<T> right) {
        return new Sum<T>(left, right).simplify();
    }

    public static <T> RegularExpression<T> product(RegularExpression<T> left, RegularExpression<T> right) {
        return new Product<T>(left, right).simplify();
    }

    public static <T> RegularExpression<T> star(RegularExpression<T> inner) {
        return new Star<T>(inner).simplify();
    }

    public static <T> RegularExpression<T> repeat(RegularExpression<T> exp, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("i must not be less than zero");
        }
        if (i == 0) {
            return RegularExpression.epsilon();
        }
        if (i == 1) {
            return exp;
        }
        return product(exp, repeat(exp, i-1));
    }

    public static <T> RegularExpression<T> optional(RegularExpression<T> exp) {
        final RegularExpression<T> epsilon = RegularExpression.epsilon();
        return sum(epsilon, exp);
    }

}
