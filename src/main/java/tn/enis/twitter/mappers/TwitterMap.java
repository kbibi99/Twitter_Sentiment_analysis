package tn.enis.twitter.mappers;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.JSONException;
import org.json.JSONObject;


import tn.enis.twitter.dictionary.Constants;

public class TwitterMap extends Mapper<Object, Text, Text, Text> {
	protected void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {

		String msg;
		String theme;
		String word;
		String hashtag[];
		String line = value.toString();
		String[] lines = line.split("\\n");
		try {
			for (int i = 0; i < lines.length; i++) {
				JSONObject obj = new JSONObject(lines[i]);
				msg = obj.getString("msg");
				hashtag = msg.split(Constants.HASHTAG_PREFIX);
				if (hashtag.length == 2) {
					theme = (hashtag[1].split(" "))[0];
					StringTokenizer tok = new StringTokenizer(msg, " ");
					while (tok.hasMoreTokens()) {
						word = tok.nextToken();
						if (!word.contains(Constants.HASHTAG_PREFIX))
							context.write(new Text(theme), new Text(word));
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
