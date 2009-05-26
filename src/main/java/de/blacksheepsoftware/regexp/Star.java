package de.blacksheepsoftware.regexp;


/**
 * @author <a href="bauerb@in.tum.de">Bernhard Bauer</a>
 *
 */
public class Star<T> extends RegularExpressionImpl<T> {

    protected RegularExpression<T> inner;

    public Star(RegularExpression<T> i) {
        if (i == null) {
            throw new IllegalArgumentException("inner expression cannot be null");
        }
        inner = i.simplify();
    }

    @Override
    public int hashCode() {
        return 13 + inner.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Star))
            return false;
        Star<T> other = (Star<T>) obj;
        if (!inner.equals(other.inner))
            return false;
        return true;
    }

    @Override
    public boolean containsEpsilon() {
        return true;
    }

    @Override
    protected RegularExpression<T> _diff(T c) {
        return inner.derivative(c).followedBy(this);
    }

    public int compareTo(RegularExpression<T> o) {
        if (o instanceof Star) {
            return inner.compareTo(((Star<T>)o).inner);
        }
        if (o instanceof Literal || o instanceof Sum) {
            return -1;
        }
        return 1;

    }

    @Override
    protected RegularExpression<T> simplify() {
        if (inner instanceof EmptySet || inner instanceof Epsilon) {
            return RegularExpression.epsilon();
        }
        if (inner instanceof Sum) {
            Sum<T> s = (Sum<T>)inner;
            if (s.left instanceof Epsilon) {
                // r?* == r*
                inner = s.right;
                return simplify();
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return parenthesizePostfixop(inner) + "*";
    }

    /**
     * @return
     */
    protected static <T> String parenthesizePostfixop(RegularExpression<T> r) {
        return (r instanceof Literal) ? r.toString() : ("(" + r.toString() + ")");
    }
}
