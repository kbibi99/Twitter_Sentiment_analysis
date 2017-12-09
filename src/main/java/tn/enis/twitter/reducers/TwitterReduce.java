package tn.enis.twitter.reducers;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import tn.enis.twitter.dictionary.Dic;

public class TwitterReduce extends Reducer<Text, Text, Text, FloatWritable> {
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		Dic dic = new Dic();
		Iterator<Text> i = values.iterator();
		float count = 0;
		float tauxSentiment = 0;
		while (i.hasNext()) {
			String word = i.next().toString().toLowerCase();
			if (dic.map.get(word) != null) {
				count++;
				tauxSentiment += dic.map.get(word);
			}
		}
		float t;
		if (count != 0) {
			t = (tauxSentiment / count );
			context.write(key, new FloatWritable(t));
		}

		else
			context.write(key, new FloatWritable(0));

	}

}
