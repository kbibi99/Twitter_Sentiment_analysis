package tn.enis.twitter.reducers;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import tn.enis.twitter.dictionary.Dic;

public class TwitterReduce extends Reducer<Text, Text, Text, DoubleWritable> {
	MongoClient mongoClient = new MongoClient("localhost", 27017);

	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		MongoDatabase db = mongoClient.getDatabase("twitter");
		MongoCollection<Document> collection = db.getCollection("hashtags");
		collection.drop();

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
		double t = 0d;
		if (count != 0) {
			t = (tauxSentiment / count);
			context.write(key, new DoubleWritable(t));

		}

		else
			context.write(key, new DoubleWritable(0));
		collection.insertOne(new Document().append("index", key.toString())
				.append("taux", t));

	}

}
