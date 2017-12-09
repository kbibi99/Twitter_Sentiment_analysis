package tn.enis.twitter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import tn.enis.twitter.mappers.TwitterMap;
import tn.enis.twitter.reducers.TwitterReduce;

// Note classe Driver (contient le main du programme Hadoop).
public class Twitter {
	// Le main du programme.
	public static void main(String[] args) throws Exception {
		// Créé un object de configuration Hadoop.
		Configuration conf = new Configuration();

		// Permet à Hadoop de lire ses arguments génériques, récupère les
		// arguments restants dans ourArgs.
		String[] ourArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();

		// Obtient un nouvel objet Job: une tâche Hadoop. On fourni la
		// configuration Hadoop ainsi qu'une description
		// textuelle de la tâche.
		Job job = Job.getInstance(conf, "Tweet v1.0");

		// Défini les classes driver, map et reduce.
		job.setJarByClass(Twitter.class);
		job.setMapperClass(TwitterMap.class);
		job.setReducerClass(TwitterReduce.class);

		// sorties du mapper = entrées du reducer et du combiner
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(ourArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(ourArgs[1]));

		// On lance la tâche Hadoop. Si elle s'est effectuée correctement, on
		// renvoie 0. Sinon, on renvoie -1.
		if (job.waitForCompletion(true))
			System.exit(0);
		System.exit(-1);
	}
}
