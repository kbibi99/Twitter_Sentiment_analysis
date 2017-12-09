package tn.enis.twitter.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class Dic {

	public  Map<String, Integer> map;

	public Dic() throws IOException {

		map = new HashMap<String, Integer>();

		String line;
		/*ClassLoader loader= getClass().getClassLoader();*/
		
		Path pt=new Path(Constants.HDFS+Constants.DICTIONARY_PATH);
        FileSystem fs = FileSystem.get(new Configuration());
        BufferedReader reader=new BufferedReader(new InputStreamReader(fs.open(pt)));
	
		while ((line = reader.readLine()) != null) {
			String[] parts = line.split(" ", 2);
			if (parts.length >= 2) {
				String key = parts[0].toLowerCase();
				String value = parts[1];
				if (value.equals("1"))
					map.put(key, Constants.POSTIVE);
				else
					map.put(key, Constants.NEGATIVE);
			} else {
				System.out.println("ignoring line: " + line);
			}
		}
		reader.close();
		System.out.println(map);

	}

}
