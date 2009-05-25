package de.blacksheepsoftware.regexp;

import junit.framework.TestCase;

/**
 * @author <a href="bauerb@in.tum.de">Bernhard Bauer</a>
 *
 */
public class RegExpTest extends TestCase {

    /**
     * @param name
     */
    public RegExpTest(String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public static <T> boolean contains(RegularExpression<T> r, RegularExpression<T> r2) {
        if (r == r2) {
            return true;
        }
        if (!(r instanceof Sum)) {
            return false;
        }
        Sum<T> s = ((Sum<T>)r);
        return (contains(s.left, r2) || contains(s.right, r2));
    }

    public void testDings() {
        final RegularExpression<Character> expr = Util.star(StringUtil.characterClass("abc"));
        final RegularExpression<Character> d = expr.derivative('a');
        assertEquals(expr, d);

    }

    public void testExponentialBlowup() {
        final RegularExpression<Character> any = StringUtil.characterClass("01");
        final RegularExpression<Character> anyStar = Util.star(any);
        final RegularExpression<Character> expr = Util.product(anyStar, Util.product(new Literal<Character>('1'), Util.repeat(any, 10)));

        assertTrue(StringUtil.matches(expr, "010000000000"));
        assertTrue(StringUtil.matches(expr, "010101010101"));
        assertFalse(StringUtil.matches(expr, "0101010101010"));
    }

    public void testStringSearch() {
        final RegularExpression<Character> any = StringUtil.characterClass("abc");
        final RegularExpression<Character> anyStar = Util.star(any);
        final RegularExpression<Character> expr = Util.product(anyStar, Util.product(StringUtil.stringMatch("abc"), anyStar));

        final RegularExpression<Character> d = expr.derivative('a');
        assertTrue(contains(d, expr));

        assertTrue(StringUtil.matches(expr, "ababcab"));
        assertFalse(StringUtil.matches(expr, "ababab"));
    }

}
