package regexp.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RegexpTest {
    
    @Test
    public void  行頭にマッチ() {
        assertTrue("a".matches("^a"));
        assertFalse("abc".matches("^a"));
    }
    
    @Test
    public void 行末にマッチ() {
        assertTrue("a".matches("a$"));
        assertFalse("abc".matches("a$"));
    }
    
    @Test
    public void 改行以外の任意の一文字にマッチ() {
        assertTrue("a".matches("."));
        assertFalse("abc".matches("."));
    }
    
    @Test
    public void 角括弧内の任意の1文字にマッチ() {
        assertTrue("a".matches("[abc]"));
        assertTrue("b".matches("[abc]"));
        assertTrue("c".matches("[abc]"));
        assertTrue("put".matches("p[ueo]t"));
        assertTrue("pet".matches("p[ueo]t"));
        assertTrue("pot".matches("p[ueo]t"));
    }
    
    @Test
    public void 英大文字AからZの任意の1文字にマッチ() {
        
    }

}
