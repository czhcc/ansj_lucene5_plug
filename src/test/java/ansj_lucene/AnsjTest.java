/**
 * 
 */
package ansj_lucene;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Date;

import org.ansj.lucene5.AnsjAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

/**
 * @author czhcc
 *
 */
public class AnsjTest
{
	@Test
	public void test() throws IOException {
		Token nt = new Token();
		Analyzer ca = new AnsjAnalysis();
		Reader sentence = new StringReader(
				"事主张三主张认真s");
		TokenStream ts = ca.tokenStream("sentence", sentence);

		System.out.println("start: " + (new Date()));
		long before = System.currentTimeMillis();
		while (ts.incrementToken()) {
			System.out.println(ts.getAttribute(CharTermAttribute.class));
		}
		ts.close();
		long now = System.currentTimeMillis();
		System.out.println("time: " + (now - before) / 1000.0 + " s");
	}
}
