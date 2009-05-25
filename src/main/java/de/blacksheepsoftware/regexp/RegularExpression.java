package de.blacksheepsoftware.regexp;


/**
 * @author <a href="bauerb@in.tum.de">Bernhard Bauer</a>
 *
 */

// EmptySet < Epsilon < Sum < Product < Star < Literal

public abstract class RegularExpression<T> implements Comparable<RegularExpression<T>> {

    public abstract boolean containsEpsilon();

    public abstract RegularExpression<T> derivative(T c);

    protected RegularExpression<T> simplify() {
        return this;
    }

    public static final RegularExpression<Object> EPSILON = new Epsilon();
    @SuppressWarnings("unchecked")
    public static <T> RegularExpression<T> epsilon() {
        return (RegularExpression<T>)EPSILON;
    }

    @Override
    public abstract int hashCode();
    @Override
    public abstract boolean equals(Object o);

    public static class Epsilon extends RegularExpression<Object> {

        private Epsilon() {}

        @Override
        public int hashCode() {
            return 17;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (!(obj instanceof Epsilon))
                return false;
            return true;
        }

        @Override
        public boolean containsEpsilon() {
            return true;
        }

        @Override
        public RegularExpression<Object> derivative(Object c) {
            return RegularExpression.EMPTY_SET;
        }

        public int compareTo(RegularExpression<Object> o) {
            if (this.equals(o)) {
                return 0;
            } else if (o instanceof EmptySet) {
                return 1;
            } else {
                return -1;
            }
        }

    }

    public static final EmptySet EMPTY_SET = new EmptySet();
    @SuppressWarnings("unchecked")
    public static <T> RegularExpression<T> emptySet() {
        return (RegularExpression<T>)EMPTY_SET;
    }

    public static class EmptySet extends RegularExpression<Object> {

        private EmptySet() {
            // singleton
        }

        @Override
        public int hashCode() {
            return 31;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (!(obj instanceof EmptySet))
                return false;
            return true;
        }
        @Override
        public boolean containsEpsilon() {
            return false;
        }

        @Override
        public RegularExpression<Object> derivative(Object c) {
            return RegularExpression.EMPTY_SET;
        }

        public int compareTo(RegularExpression<Object> o) {
            if (this == o || o instanceof EmptySet) {
                return 0;
            } else {
                return -1;
            }
        }

    }
}
