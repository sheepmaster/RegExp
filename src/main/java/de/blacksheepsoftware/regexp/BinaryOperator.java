package de.blacksheepsoftware.regexp;

/**
 * @author <a href="bauerb@in.tum.de">Bernhard Bauer</a>
 *
 */
public abstract class BinaryOperator<T> extends RegularExpressionImpl<T> {

    protected final RegularExpression<T> left;
    protected final RegularExpression<T> right;

    protected Boolean containsEpsilon = null;

    protected BinaryOperator(RegularExpression<T> l, RegularExpression<T> r) {
        if (l == null) {
            throw new IllegalArgumentException("left argument cannot be null");
        }
        if (r == null) {
            throw new IllegalArgumentException("right argument cannot be null");
        }
        left = l;
        right = r;
    }

    @Override
    public int hashCode() {
        final int result1 = 31 * this.getClass().hashCode() + left.hashCode();
        return 17 * result1 + right.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof BinaryOperator))
            return false;
        BinaryOperator<T> other = (BinaryOperator<T>) obj;
        if (!left.equals(other.left))
            return false;
        if (!right.equals(other.right))
            return false;
        return true;
    }

    @Override
    public boolean containsEpsilon() {
        if (containsEpsilon == null) {
            containsEpsilon = _containsEpsilon();
        }
        return containsEpsilon.booleanValue();
    }

    protected abstract boolean _containsEpsilon();

}
