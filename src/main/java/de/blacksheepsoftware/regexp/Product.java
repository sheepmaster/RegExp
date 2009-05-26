package de.blacksheepsoftware.regexp;


/**
 * @author <a href="bauerb@in.tum.de">Bernhard Bauer</a>
 *
 */
public class Product<T> extends BinaryOperator<T> {

    protected Product(RegularExpression<T> l, RegularExpression<T> r) {
        super(l, r);
    }

    @Override
    protected boolean _containsEpsilon() {
        return left.containsEpsilon() && right.containsEpsilon();
    }

    @Override
    protected RegularExpression<T> _diff(T c) {
        final RegularExpression<T> l = left.derivative(c);
        final RegularExpression<T> p = (l == left) ? this : l.followedBy(right);
        return left.containsEpsilon() ? right.derivative(c).or(p) : p;
    }

    public int compareTo(RegularExpression<T> o) {
        if (this.equals(o)) {
            return 0;
        } else if (o instanceof Product) {
            Product<T> s = (Product<T>)o;
            final int leftComparison = left.compareTo(s.left);
            return (leftComparison == 0) ? right.compareTo(s.right) : leftComparison;
        } else if (o instanceof Literal || o instanceof Star || o instanceof Sum) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return left.toString() + right.toString();
    }

}
