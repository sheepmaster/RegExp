package de.blacksheepsoftware.regexp;

/**
 * @author <a href="bauerb@in.tum.de">Bernhard Bauer</a>
 *
 */
public class Literal<T> extends RegularExpression<T> {

    protected final T literal;

    public Literal(T l) {
        if (l == null) {
            throw new IllegalArgumentException("literal cannot be null");
        }
        literal = l;
    }

    @Override
    public boolean containsEpsilon() {
        return false;
    }

    @Override
    public RegularExpression<T> derivative(T c) {
        if (c.equals(literal)) {
            return RegularExpression.epsilon();
        } else {
            return RegularExpression.emptySet();
        }
    }

    @Override
    public int hashCode() {
        return 31 + literal.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Literal))
            return false;
        Literal<T> other = (Literal<T>) obj;
        if (!literal.equals(other.literal))
            return false;
        return true;
    }

    public int compareTo(RegularExpression<T> o) {
        if (this.equals(o)) {
            return 0;
        } else if (o instanceof Literal) {
            return ((Comparable<T>)literal).compareTo(((Literal<T>)o).literal);
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return literal.toString();
    }
}
