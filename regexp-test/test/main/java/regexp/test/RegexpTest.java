package regexp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * 正規表現の動作テスト
 */
public class RegexpTest {

    /**
     * 文字："." 
     * 意味：改行以外の任意の一文字にマッチ
     */
    @Test
    public void 改行以外の任意の一文字にマッチ() {
        assertTrue("a".matches("."));
        assertFalse("\n".matches(".")); //改行はマッチしない
        assertFalse("ab".matches(".")); //一文字より多いのでマッチしない
    }

    /**
     * 文字："[]" 
     * 意味："[]"内の任意の一文字にマッチ
     */
    @Test
    public void 角括弧内の任意の1文字にマッチ() {
        assertTrue("a".matches("[abc]"));
        assertTrue("b".matches("[abc]"));
        assertTrue("c".matches("[abc]"));
        assertTrue("put".matches("p[ueo]t"));
        assertTrue("pet".matches("p[ueo]t"));
        assertTrue("pot".matches("p[ueo]t"));
        assertFalse("poot".matches("p[ueo]t")); //一文字より多いのでマッチしない
    }

    /**
     * 文字："[A-Z]"
     * 意味：英大文字の任意の一文字にマッチ
     */
    @Test
    public void 英大文字AからZの任意の1文字にマッチ() {
        assertTrue("aBc".matches("a[A-Z]c"));
        assertFalse("aBBc".matches("a[A-Z]c"));
        assertFalse("abc".matches("a[A-Z]c")); //小文字なのでマッチしない
    }

    /**
     * 文字："[a-z]"
     * 意味：英小文字の任意の一文字にマッチ
     */
    @Test
    public void 英小文字AからZの任意の1文字にマッチ() {
        assertTrue("abc".matches("a[a-z]c"));
        assertFalse("abbc".matches("a[a-z]c")); //一文字以上なのでマッチしない
        assertFalse("aBc".matches("a[a-z]c"));  //英大文字なのでマッチしない
    }

    /**
     * 文字："[0-9]" 
     * 意味：数字の任意の一文字にマッチ
     */
    @Test
    public void 数字0から9の任意の1文字にマッチ() {
        assertTrue("a0c".matches("a[0-9]c"));
        assertTrue("a5c".matches("a[0-9]c"));
        assertTrue("a9c".matches("a[0-9]c"));
        assertFalse("a12c".matches("a[0-9]c")); //一文字以上なのでマッチしない
        assertFalse("aBc".matches("a[0-9]c")); //数字ではないのでマッチしない
    }

    /**
     * 文字："\w" 
     * 意味：任意の英数字か"_"一文字にマッチ
     */
    @Test
    public void 任意の英数字かアンダーバー1文字にマッチ() {
        assertTrue("a".matches("\\w"));
        assertTrue("0".matches("\\w"));
        assertTrue("_".matches("\\w"));
        assertFalse("|".matches("\\w"));
    }

    /**
     * 文字："\W"
     * 意味：任意の英数字か"_"以外の一文字にマッチ
     */
    @Test
    public void 任意の英数字かアンダーバー以外の1文字にマッチ() {
        assertFalse("a".matches("\\W"));
        assertFalse("0".matches("\\W"));
        assertFalse("_".matches("\\W"));
        assertTrue("|".matches("\\W"));
    }

    /**
     * 文字："\s"
     * 意味：任意の空白文字一文字にマッチ
     */
    @Test
    public void 空白文字1文字にマッチ() {
        assertTrue(" ".matches("\\s"));
        assertTrue("\t".matches("\\s")); //タブも空白として扱う
        assertTrue("\n".matches("\\s")); //改行も空白として扱う
    }

    /**
     * 文字："\S"
     * 意味：任意の空白文字以外の一文字にマッチ
     */
    @Test
    public void 任意の空白文字以外の1文字にマッチ() {
        assertFalse(" ".matches("\\S"));
        assertFalse("\t".matches("\\S")); //タブも空白として扱う
        assertFalse("\n".matches("\\S")); //改行も空白として扱う
    }

    /**
     * 文字："\d"
     * 意味：任意の数字一文字にマッチ
     */
    @Test
    public void 任意の数字1文字にマッチ() {
        assertTrue("1".matches("\\d"));
        assertFalse("12".matches("\\d")); //一文字以上なのでマッチしない
    }

    /**
     * 文字："\D"
     * 意味：任意の数字以外の一文字にマッチ
     */
    @Test
    public void 任意の数字以外の1文字にマッチ() {
        assertFalse("1".matches("\\D"));
        assertTrue("a".matches("\\D"));
    }

    /**
     * 文字："[^]"
     * 意味："[^]"に含まれていない一文字にマッチ
     */
    @Test
    public void 角括弧内に含まれない1文字にマッチ() {
        assertTrue("d".matches("[^abc]"));
        assertFalse("dd".matches("[^abc]")); //一文字以上なのでマッチしない
        assertFalse("a".matches("[^abc]"));
    }

    /**
     * 文字："*"
     * 意味：直前の表現を0回以上繰り返す
     */
    @Test
    public void 直前の表現を0回以上繰り返す() {
        assertTrue("a".matches("ab*"));     //0回繰り返しているのでマッチする
        assertTrue("ab".matches("ab*"));    //1回繰り返しているのでマッチする
        assertTrue("abb".matches("ab*"));   //2回繰り返しているのでマッチする
        assertTrue("abbb".matches("ab*"));  //3回繰り返しているのでマッチする
    }

    /**
     * 文字："+"
     * 意味：直前の表現を1回以上繰り返す
     */
    @Test
    public void 直前の表現を1回以上繰り返す() {
        assertFalse("a".matches("ab+"));   //0回繰り返しなのでマッチしない
        assertTrue("ab".matches("ab+"));   //1回繰り返しなのでマッチする
        assertTrue("abb".matches("ab+"));  //2回繰り返しなのでマッチする
        assertTrue("abbb".matches("ab+")); //3回繰り返しなのでマッチする
    }

    /**
     * 文字："?"
     * 意味：直前の表現を0回または1回繰り返す
     */
    @Test
    public void 直前の表現を0回または1回繰り返す() {
        assertTrue("a".matches("ab?"));     //0回繰り返しなのでマッチする
        assertTrue("ab".matches("ab?"));    //1回繰り返しなのでマッチする
        assertFalse("abb".matches("ab?"));  //2回繰り返しなのでマッチしない
    }

    /**
     * 文字："{N}"
     * 意味：直前の表現をN回繰り返す
     */
    @Test
    public void 直前の表現をN回繰り返す() {
        assertFalse("a".matches("ab{2}"));     //2回繰り返していないのでマッチしない
        assertFalse("ab".matches("ab{2}"));    //2回繰り返していないのでマッチしない
        assertTrue("abb".matches("ab{2}"));    //2回繰り返しているのでマッチする
        assertFalse("abbb".matches("ab{2}"));  //2回繰り返していないのでマッチしない
    }

    /**
     * 文字："{N,}"
     * 意味：直前の表現をN回以上繰り返す
     */
    @Test
    public void 直前の表現をN回以上繰り返す() {
        assertFalse("a".matches("ab{2,}"));
        assertFalse("ab".matches("ab{2,}"));
        assertTrue("abb".matches("ab{2,}"));
        assertTrue("abbb".matches("ab{2,}"));
    }

    /**
     * 文字："{N,M}"
     * 意味：直前の表現をN回以上M回以下繰り返す
     */
    @Test
    public void 直前の表現をN回以上M回以下繰り返す() {
        assertFalse("a".matches("ab{2,3}"));
        assertFalse("ab".matches("ab{2,3}"));
        assertTrue("abb".matches("ab{2,3}"));
        assertTrue("abbb".matches("ab{2,3}"));
        assertFalse("abbbb".matches("ab{2,3}"));
    }

    /**
     * 文字："|"
     * 意味："|"の左右のいずれかのパターンにマッチする
     */
    @Test
    public void いずれかのパターンにマッチする() {
        assertTrue("a".matches("a|b"));
        assertTrue("b".matches("a|b"));
        assertFalse("c".matches("a|b"));
    }

    /**
     * 文字："()"
     * 意味："()"内でパターンをグループ化してマッチする
     */
    @Test
    public void パターンをグループ化してマッチする() {
        assertTrue("def".matches("(abc)*(def)+"));
        assertTrue("abcdef".matches("(abc)*(def)+"));
        assertTrue("abcdefdef".matches("(abc)*(def)+"));
    }

    /**
     * 文字："^" 
     * 意味：行頭にマッチする（先頭ではない）
     */
    @Test
    public void 行頭にマッチ() {
        // "^"だと行頭にマッチするので、行ごとにマッチする設定の場合、セキュリテリホールになり得る
        Pattern pattern = Pattern.compile("^https?:\\/\\/[^\n]+$", Pattern.MULTILINE);
        StringBuilder builder = new StringBuilder();
        builder.append("javascript:exploit_code();/*");
        builder.append("\n");
        builder.append("http://example.com");
        builder.append("*/");
        Matcher matcher = pattern.matcher(builder.toString());
        assertTrue(matcher.find()); //行中に"http"の記述があればマッチするので、任意のjavascriptが実行されてしまう
    }

    /**
     * 文字："\A" 
     * 意味：先頭にマッチする（行頭ではない）
     */
    @Test
    public void 先頭にマッチ() {
        // "\A"だと先頭にマッチするので、行ごとにマッチする設定であっても、セキュリテリホールを防げる
        Pattern pattern = Pattern.compile("\\Ahttps?:\\/\\/[^\n]+$", Pattern.MULTILINE);
        StringBuilder builder = new StringBuilder();
        builder.append("javascript:exploit_code();/*");
        builder.append("\n");
        builder.append("http://example.com");
        builder.append("*/");
        Matcher matcher = pattern.matcher(builder.toString());
        assertFalse(matcher.find()); //先頭に"http"の記述がないものはマッチしない
    }

    /**
     * 文字："$" 
     * 意味：行末にマッチする（末尾ではない）
     */
    @Test
    public void 行末にマッチ() {
        Pattern pattern = Pattern.compile("b$", Pattern.MULTILINE);
        StringBuilder builder = new StringBuilder();
        builder.append("a");
        builder.append("\n");
        builder.append("b");
        Matcher matcher = pattern.matcher(builder.toString());
        assertTrue(matcher.find());
    }

    /**
     * 文字："\z" 
     * 意味：末尾にマッチする（行末でない）
     */
    @Test
    public void 末尾にマッチ() {
        Pattern pattern = Pattern.compile("b\\z", Pattern.MULTILINE);
        StringBuilder builder = new StringBuilder();
        builder.append("a");
        builder.append("\n");
        builder.append("b");
        Matcher matcher = pattern.matcher(builder.toString());
        assertTrue(matcher.find());
    }
     
    /**
     * 文字："\b"
     * 意味：単語の境界にマッチする
     */
    @Test
    public void 単語の境界にマッチ() {
        Pattern pattern = Pattern.compile("\\bARE");
        Matcher matcher = pattern.matcher("HOW ARE YOU?");
        assertTrue(matcher.find());

        matcher = pattern.matcher("HOWAREYOU?");
        assertFalse(matcher.find()); //単語の境界に空白がないためはマッチしない
    }

    /**
     * 文字："\B" 意味：単語の境界以外にマッチする
     */
    @Test
    public void 単語の境界以外にマッチ() {
        Pattern pattern = Pattern.compile("\\Bare");
        Matcher matcher = pattern.matcher("How are you?");
        assertFalse(matcher.find());

        matcher = pattern.matcher("Howareyou?");
        assertTrue(matcher.find()); //単語の境界に空白がないためマッチする
    }

    /**
     * 文字："(?=pattern)"
     * 意味：patternで指定した文字列が続く場合にマッチする（肯定先読み）
     */
    @Test
    public void 肯定先読み() {
        Pattern pattern = Pattern.compile("(?=fuga)"); // "fuga"の直前の位置を取得
        Matcher matcher = pattern.matcher("hogefuga");
        if (matcher.find()) {
            assertEquals("hoge".length(), matcher.start());
        } else {
            fail();
        }

        pattern = Pattern.compile(".*(?=fuga)"); // "fuga"の手間の文字列をすべて取得
        matcher = pattern.matcher("hogefuga");
        if (matcher.find()) {
            assertTrue("hoge".equals(matcher.group()));
        } else {
            fail();
        }
    }

    /**
     * 文字："(?|pattern)"
     * 意味：patternで指定しない文字列が続く場合にマッチする（否定先読み）
     */
    @Test
    public void 否定先読み() {
        Pattern pattern = Pattern.compile("(?!fuga)"); // "fuga"の直前の位置以外を取得
        Matcher matcher = pattern.matcher("hfuga");
        while (matcher.find()) {
            assertNotEquals(1, matcher.start());
        }

        pattern = Pattern.compile(".(?!fuga)"); // 直後に"fuga"が現れない1文字を取得
        matcher = pattern.matcher("hfuga");
        while (matcher.find()) {
            assertNotEquals("h", matcher.group());
        }
    }

}
