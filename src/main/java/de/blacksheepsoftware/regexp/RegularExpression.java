package de.blacksheepsoftware.regexp;


/**
 * @author <a href="bauerb@in.tum.de">Bernhard Bauer</a>
 *
 */

// EmptySet < Epsilon < Product < Star < Literal < Sum

public abstract class RegularExpression<T> implements Comparable<RegularExpression<T>> {

    public abstract boolean containsEpsilon();

    public abstract RegularExpression<T> derivative(T c);

    public static final RegularExpression<Object> EPSILON = new Epsilon();
    @SuppressWarnings("unchecked")
    public static <T> RegularExpression<T> epsilon() {
        return (RegularExpression<T>)EPSILON;
    }

    @Override
    public abstract int hashCode();
    @Override
    public abstract boolean equals(Object o);

    public RegularExpression<T> or(RegularExpression<T> right) {
        int cmp = compareTo(right);
        if (cmp == 0) {
            return this;
        }
        if (cmp > 0) {
            return right.or(this);
        }
        if (right instanceof Sum) {
            Sum<T> s = (Sum<T>)right;
            if (compareTo(s.left) > 0) {
                return s.left.or(or(s.right));
            }
        }
        return new Sum<T>(this, right);
    }

    public RegularExpression<T> followedBy(RegularExpression<T> r) {
        if (r instanceof EmptySet) {
            return r;
        }
        if (r instanceof Epsilon) {
            return this;
        }
        return new Product<T>(this, r);
    }

    public RegularExpression<T> star() {
        return new Star<T>(this);
    }

    public RegularExpression<T> optional() {
        final RegularExpression<T> epsilon = epsilon();
        return or(epsilon);
    }

    public RegularExpression<T> repeated(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("i must not be less than zero");
        }
        if (i == 0) {
            return RegularExpression.epsilon();
        }
        if (i == 1) {
            return this;
        }
        return followedBy(repeated(i-1));
    }

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
        public String toString() {
            return "<Epsilon>";
        }

        @Override
        public boolean containsEpsilon() {
            return true;
        }

        @Override
        public RegularExpression<Object> derivative(Object c) {
            return RegularExpression.EMPTY_SET;
        }

        @Override
        public RegularExpression<Object> or(RegularExpression<Object> r) {
            return r.containsEpsilon() ? r : super.or(r);
        }

        @Override
        public RegularExpression<Object> followedBy(RegularExpression<Object> r) {
            return r;
        }

        @Override
        public RegularExpression<Object> star() {
            return this;
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
        public String toString() {
            return "<EmptySet>";
        }

        @Override
        public boolean containsEpsilon() {
            return false;
        }

        @Override
        public RegularExpression<Object> derivative(Object c) {
            return this;
        }

        @Override
        public RegularExpression<Object> or(RegularExpression<Object> r) {
            return r;
        }

        @Override
        public RegularExpression<Object> followedBy(RegularExpression<Object> r) {
            return this;
        }

        @Override
        public RegularExpression<Object> star() {
            return epsilon();
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
