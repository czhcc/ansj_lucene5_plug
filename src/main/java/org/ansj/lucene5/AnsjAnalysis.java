package org.ansj.lucene5;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;

import org.ansj.lucene.util.AnsjTokenizer;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

public class AnsjAnalysis extends Analyzer {

	boolean pstemming;
	public Set<String> filter;
	private Reader reader;

	/**
	 * @param filter
	 *            停用词
	 * @param pstemming
	 *            是否分析词干
	 */
	public AnsjAnalysis(Set<String> filter, boolean pstemming) {
		this.filter = filter;
		this.pstemming = pstemming;
	}

	/**
	 * @param pstemming
	 *            是否分析词干.进行单复数,时态的转换
	 */
	public AnsjAnalysis(boolean pstemming) {
		this.pstemming = pstemming;
	}

	public AnsjAnalysis() {
		super();
	}

	/*@Override
	protected TokenStreamComponents createComponents(String fieldName, final Reader reader) {
		Tokenizer tokenizer = new AnsjTokenizer(new ToAnalysis(reader), reader, filter, pstemming);
		return new TokenStreamComponents(tokenizer);
	}*/

	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.Analyzer#createComponents(java.lang.String)
	 */
	@Override
	protected TokenStreamComponents createComponents(String text)
	{
		Tokenizer tokenizer = new AnsjTokenizer(new ToAnalysis(reader), reader, filter, pstemming);
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

	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.Analyzer#initReader(java.lang.String, java.io.Reader)
	 */
	@Override
	protected Reader initReader(String fieldName, Reader reader)
	{
		this.reader = reader;
		return super.initReader(fieldName, reader);
	}

}
