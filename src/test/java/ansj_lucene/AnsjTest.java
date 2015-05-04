/**
 * 
 */
package ansj_lucene;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.lucene5.AnsjAnalysis;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Ignore;
import org.junit.Test;
import org.nlpcn.commons.lang.util.StringUtil;

/**
 * @author czhcc
 *
 */
public class AnsjTest
{
	@Ignore
	@Test
	public void test() throws IOException {
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
		
		ca.close();
	}
	
	@Ignore
	@Test
	public void test1() throws Exception
	{
		Analyzer ca = new AnsjAnalysis();
		Reader sentence = new StringReader(
				"张三的电话号码是37998845，有事请联系他");
		TokenStream ts = ca.tokenStream("sentence", sentence);

		System.out.println("start: " + (new Date()));
		long before = System.currentTimeMillis();
		while (ts.incrementToken()) {
			System.out.println(ts.getAttribute(CharTermAttribute.class));
		}
		ts.close();
		long now = System.currentTimeMillis();
		System.out.println("time: " + (now - before) / 1000.0 + " s");
		
		ca.close();
	}
	
	private List<String[]> nameAndNature(List<Term> parse) {
		List<String[]> result = new ArrayList<String[]>();
		for (Term term : parse) {
			if (StringUtil.isNotBlank(term.getName())) {
				result.add(new String[] { term.getName(), term.getNatureStr() });
			}
		}
		return result;
	}
	
	@Test
	public void mytest()
	{
		String content = "张三的电话号码是(020)37998845，有事请联系他";
		List<String[]> indexResult = nameAndNature(IndexAnalysis.parse(content));
		for(String[] s : indexResult)
		{
			for(String s1 : s)
			{
				System.out.println(s1);
			}
			System.out.println("============================");
		}
	}
}
