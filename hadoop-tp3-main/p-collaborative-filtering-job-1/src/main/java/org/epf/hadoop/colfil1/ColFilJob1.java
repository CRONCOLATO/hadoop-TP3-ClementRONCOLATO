package org.epf.hadoop.colfil1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ColFilJob1 {
    public static void main(String[] args) throws Exception {
        // Log the provided arguments
        System.out.println("Arguments provided:");
        for (int i = 0; i < args.length; i++) {
            System.out.println("Arg[" + i + "]: " + args[i]);
        }

        // Check the number of arguments
        if (args.length != 2) {
            System.err.println("Usage: ColFilJob1 <input path> <output path>");
            System.exit(-1);
        }

        // Create a new Job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Collaborative Filtering Job 1");

        // Check if the output path exists, and delete it if necessary
        FileSystem fs = FileSystem.get(conf);
        Path outputPath = new Path(args[1]);
        if (fs.exists(outputPath)) {
            System.out.println("Output path exists. Deleting: " + outputPath);
            fs.delete(outputPath, true);
        }

        // Set the main class
        job.setJarByClass(ColFilJob1.class);

        // Set the Mapper and Reducer classes
        job.setMapperClass(Job1Mapper.class);
        job.setReducerClass(Job1Reducer.class);

        // Set the output key and value types
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // Set the input format (customized for our input)
        job.setInputFormatClass(RelationshipInputFormat.class);

        // Set the input and output paths
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, outputPath);

        // Set the number of reducers
        job.setNumReduceTasks(2);

        // Log job configuration details
        System.out.println("Job name: " + job.getJobName());
        System.out.println("Number of reducers: " + job.getNumReduceTasks());

        // Submit the job and wait for completion
        boolean success = job.waitForCompletion(true);

        // Log the result of the job execution
        System.out.println("Job execution completed. Success: " + success);
        System.exit(success ? 0 : 1);
    }
}
