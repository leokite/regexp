package regexp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexpTest {
    
    
    @Test
    public void 改行以外の任意の一文字にマッチ() {
        assertTrue("a".matches("."));
        assertFalse("ab".matches("."));
        assertFalse("\n".matches("."));
    }
    
    @Test
    public void 文字クラス_角括弧内の任意の1文字にマッチ() {
        assertTrue("a".matches("[abc]"));
        assertTrue("b".matches("[abc]"));
        assertTrue("c".matches("[abc]"));
        assertTrue("put".matches("p[ueo]t"));
        assertTrue("pet".matches("p[ueo]t"));
        assertTrue("pot".matches("p[ueo]t"));
        assertFalse("poot".matches("p[ueo]t"));
    }
    
    @Test
    public void 文字クラス_英大文字AからZの任意の1文字にマッチ() {
        assertTrue("aBc".matches("a[A-Z]c"));
        assertFalse("aBBc".matches("a[A-Z]c"));
        assertFalse("abc".matches("a[A-Z]c"));
    }

    @Test
    public void 文字クラス_英小文字AからZの任意の1文字にマッチ() {
        assertTrue("abc".matches("a[a-z]c"));
        assertFalse("abbc".matches("a[a-z]c"));
        assertFalse("aBc".matches("a[a-z]c"));
    }

    @Test
    public void 文字クラス_数字0から9の任意の1文字にマッチ() {
        assertTrue("a0c".matches("a[0-9]c"));
        assertTrue("a5c".matches("a[0-9]c"));
        assertTrue("a9c".matches("a[0-9]c"));
        assertFalse("aBc".matches("a[0-9]c"));
    }
    
    @Test
    public void 文字クラス_任意の英数字と_1文字にマッチ() {
        assertTrue("a".matches("\\w"));
        assertTrue("_".matches("\\w"));
    }

    @Test
    public void 文字クラス_任意の英数字と_以外の1文字にマッチ() {
        assertTrue("|".matches("\\W"));
        assertFalse("_".matches("\\W"));
        assertFalse("a".matches("\\W"));
    }
    
    @Test
    public void 文字クラス_空白文字1文字にマッチ() {
        assertTrue(" ".matches("\\s"));
        assertTrue("\t".matches("\\s"));
        assertTrue("\n".matches("\\s"));
    }

    @Test
    public void 文字クラス_空白文字以外の任意の1文字にマッチ() {
        assertFalse(" ".matches("\\S"));
        assertFalse("\t".matches("\\S"));
        assertFalse("\n".matches("\\S"));
    }
    
    @Test
    public void 文字クラス_数字の1文字にマッチ() {
        assertTrue("1".matches("\\d"));
    }

    @Test
    public void 文字クラス_数字以外の1文字にマッチ() {
        assertFalse("1".matches("\\D"));
    }
    
    @Test
    public void 文字クラス_角括弧内に含まれない1文字にマッチ() {
        assertTrue("d".matches("[^abc]"));
        assertFalse("dd".matches("[^abc]"));
        assertFalse("a".matches("[^abc]"));
    }
    
    @Test
    public void 数量子_直前の表現を0回以上繰り返す() {
        assertTrue("a".matches("ab*"));
        assertTrue("ab".matches("ab*"));
        assertTrue("abb".matches("ab*"));
        assertTrue("abbb".matches("ab*"));
    }
    
    @Test
    public void 数量子_直前の表現を1回以上繰り返す() {
        assertFalse("a".matches("ab+"));
        assertTrue("ab".matches("ab+"));
        assertTrue("abb".matches("ab+"));
        assertTrue("abbb".matches("ab+"));
    }
    
    @Test
    public void 数量子_直前の表現を0回または1回繰り返す() {
        assertTrue("a".matches("ab?"));
        assertTrue("ab".matches("ab?"));
        assertFalse("abb".matches("ab?"));
    }
    
    @Test
    public void 数量子_直前の表現をN回繰り返す() {
        assertFalse("a".matches("ab{2}"));
        assertFalse("ab".matches("ab{2}"));
        assertTrue("abb".matches("ab{2}"));
        assertFalse("abbb".matches("ab{2}"));
    }
    
    @Test
    public void 数量子_直前の表現をN回以上繰り返す() {
        assertFalse("a".matches("ab{2,}"));
        assertFalse("ab".matches("ab{2,}"));
        assertTrue("abb".matches("ab{2,}"));
        assertTrue("abbb".matches("ab{2,}"));
    }
    
    @Test
    public void 数量子_直前の表現をN回以上M回以下繰り返す() {
        assertFalse("a".matches("ab{2,3}"));
        assertFalse("ab".matches("ab{2,3}"));
        assertTrue("abb".matches("ab{2,3}"));
        assertTrue("abbb".matches("ab{2,3}"));
        assertFalse("abbbb".matches("ab{2,3}"));
    }
    
    @Test
    public void 論理和_いずれかのパターンにマッチする() {
        assertTrue("a".matches("a|b"));
        assertTrue("b".matches("a|b"));
        assertFalse("c".matches("a|b"));
    }
    
   @Test
   public void グループ化_パターンをグループ化してマッチする() {
       assertTrue("def".matches("(abc)*(def)+"));
       assertTrue("abcdef".matches("(abc)*(def)+"));
       assertTrue("abcdefdef".matches("(abc)*(def)+"));
   }
    
    @Test
    public void 境界_行頭にマッチ() {
        Pattern pattern = Pattern.compile("^a");
        Matcher matcher = pattern.matcher("ab");
        assertTrue(matcher.find());
    }
    
    @Test
    public void 境界_行末にマッチ() {
        Pattern pattern = Pattern.compile("b$");
        Matcher matcher = pattern.matcher("ab");
        assertTrue(matcher.find());
        assertTrue("a".matches("a$"));
        assertFalse("ba".matches("a$"));
    }
    
    @Test
    public void 境界_単語の境界にマッチ() {
        Pattern pattern = Pattern.compile("\\bare");
        Matcher matcher = pattern.matcher("How are you?");
        assertTrue(matcher.find());

        matcher = pattern.matcher("Howareyou?");
        assertFalse(matcher.find());
    }
    
    @Test
    public void 境界_単語の境界以外にマッチ() {
        Pattern pattern = Pattern.compile("\\Bare");
        Matcher matcher = pattern.matcher("How are you?");
        assertFalse(matcher.find());

        matcher = pattern.matcher("Howareyou?");
        assertTrue(matcher.find());
    }
    
    @Test
    public void 肯定的先読み() {
        Pattern pattern = Pattern.compile("(?=fuga)"); // fugaの直前の位置を取得
        Matcher matcher = pattern.matcher("hogefuga");
        if (matcher.find()) {
            assertEquals("hoge".length(), matcher.start());
        } else {
            fail();
        }
        
        pattern = Pattern.compile(".*(?=fuga)"); // fugaの手間の文字列をすべて取得
        matcher = pattern.matcher("hogefuga");
        if (matcher.find()) {
            assertTrue("hoge".equals(matcher.group()));
        } else {
            fail();
        }
    }
    
    @Test
    public void 否定的先読み() {
        Pattern pattern = Pattern.compile("(?!fuga)"); // fugaの直前の位置以外を取得
        Matcher matcher = pattern.matcher("hfuga");
        while (matcher.find()) {
            assertNotEquals(1, matcher.start());
        }
        
        pattern = Pattern.compile(".(?!fuga)"); // 直後にfugaが現れない1文字を取得
        matcher = pattern.matcher("hfuga");
        while (matcher.find()) {
            assertNotEquals("h", matcher.group());
        }
    }
    
    
    
    
    
}
