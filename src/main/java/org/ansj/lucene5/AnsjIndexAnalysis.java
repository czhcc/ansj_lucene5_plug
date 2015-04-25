package org.ansj.lucene5;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Set;

import org.ansj.lucene.util.AnsjTokenizer;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

public class AnsjIndexAnalysis extends Analyzer {

	boolean pstemming;
	public Set<String> filter;

	/**
	 * @param filter
	 *            停用词
	 * @param pstemming
	 *            是否分析词干
	 */
	public AnsjIndexAnalysis(Set<String> filter, boolean pstemming) {
		this.filter = filter;
	}

	/**
	 * @param pstemming
	 *            是否分析词干.进行单复数,时态的转换
	 */
	public AnsjIndexAnalysis(boolean pstemming) {
		this.pstemming = pstemming;
	}

	public AnsjIndexAnalysis() {
		super();
	}

	/*@Override
	protected TokenStreamComponents createComponents(String fieldName, final Reader reader) {
		// TODO Auto-generated method stub
		Tokenizer tokenizer = new AnsjTokenizer(new IndexAnalysis(reader), reader, filter, pstemming);
		return new TokenStreamComponents(tokenizer);
	}*/

	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.Analyzer#createComponents(java.lang.String)
	 */
	@Override
	protected TokenStreamComponents createComponents(String text)
	{
		Reader reader = new StringReader(text);
		Tokenizer tokenizer = new AnsjTokenizer(new IndexAnalysis(reader), reader, filter, pstemming);
		try
		{
			tokenizer.setReader(reader);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return new TokenStreamComponents(tokenizer);
	}

}
