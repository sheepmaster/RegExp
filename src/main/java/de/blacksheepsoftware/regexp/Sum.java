package de.blacksheepsoftware.regexp;




/**
 * @author <a href="bauerb@in.tum.de">Bernhard Bauer</a>
 *
 */
public class Sum<T> extends BinaryOperator<T> {

    protected Sum(RegularExpression<T> l, RegularExpression<T> r) {
        super(l, r);
    }

    @Override
    protected boolean _containsEpsilon() {
        return left.containsEpsilon() || right.containsEpsilon();
    }

    @Override
    protected RegularExpression<T> _diff(T c) {
        final RegularExpression<T> l = left.derivative(c);
        final RegularExpression<T> r = right.derivative(c);
        return (l == left && r == right) ? this : l.or(r);
    }

    @Override
    public RegularExpression<T> star() {
        if (left instanceof Epsilon) {
            // r?* == r*
            return right.star();
        }
        return super.star();
    }

    public int compareTo(RegularExpression<T> o) {
        if (this.equals(o)) {
            return 0;
        } else if (o instanceof Sum) {
            Sum<T> s = (Sum<T>)o;
            final int leftComparison = left.compareTo(s.left);
            return (leftComparison == 0) ? right.compareTo(s.right) : leftComparison;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return (left instanceof Epsilon) ?
                (Star.parenthesizePostfixop(right) + "?") :
                    (parenthesizeByPrecedence(left) + "|" + parenthesizeByPrecedence(right));
    }

    /**
     * @return
     */
    private static <T> String parenthesizeByPrecedence(RegularExpression<T> r) {
        return ((r instanceof Sum || r instanceof Literal || r instanceof Star) ? r.toString() : ("(" + r.toString() + ")"));
    }
}

