package de.blacksheepsoftware.regexp;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author <a href="bauerb@in.tum.de">Bernhard Bauer</a>
 *
 */
public abstract class RegularExpressionImpl<T> extends RegularExpression<T> {
    protected Map<T, RegularExpression<T>> derivatives = new TreeMap<T, RegularExpression<T>>();

    @Override
    public RegularExpression<T> derivative(T c) {
        RegularExpression<T> r = derivatives.get(c);
        if (r == null) {
            r = _diff(c);
            derivatives.put(c, r);
        }
        return r;
    }

    protected abstract RegularExpression<T> _diff(T c);
}
