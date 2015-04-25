/**
 * 
 */
package ansj_lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.ansj.lucene5.AnsjAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author czhcc
 *
 */
public class CreateIndex
{
	private String readText(File file)
	{
		try
		{
			InputStream in = new FileInputStream(file);
			int length = in.available();
			byte[] data = new byte[length];
			in.read(data);

			return new String(data);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return "";
		}
	}

	@Ignore
	@Test
	public void readFiles()
	{
		File dir = new File("/home/czhcc/data/temp/index/txt");
		File[] files = dir.listFiles();
		for (File file : files)
		{
			String txt = readText(file);
			System.out.println(txt);
		}
	}

	@Test
	public void createIndex1()
	{
		IndexWriter writer = null;
		try
		{
			Analyzer stdAn = new AnsjAnalysis();
			// 1、创建Directory对象
			Path path = FileSystems.getDefault().getPath("/home/czhcc/data/temp/index/index");
			Directory dir = FSDirectory.open(path);
			// 2、创建indexWrite
			writer = new IndexWriter(dir, new IndexWriterConfig(stdAn));
			// 3、创建document对象
			Document document = null;
			// 4、将要索引的文件已Field形式添加到document
			File files = new File("/home/czhcc/data/temp/index/txt");
			for (File file : files.listFiles())
			{
				document = new Document();
				document.add(new StringField("fileName", file.getName(),
						Field.Store.YES)); // 为文件名创建索引，存储
				document.add(new StringField("filePath",
						file.getAbsolutePath(), Field.Store.YES)); // 为文件路径创建索引，存储
				document.add(new TextField("content", new BufferedReader(
						new InputStreamReader(new FileInputStream(file),
								"UTF-8")))); // 为内容创建索引，但不存储
				writer.addDocument(document);

			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (writer != null)
			{
				try
				{
					writer.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}

		}
	}
}
