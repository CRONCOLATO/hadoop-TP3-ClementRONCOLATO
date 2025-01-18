package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Job3Mapper extends Mapper<LongWritable, Text, Text, Recommendation> {
    private Text userKey = new Text();
    private Recommendation recommendation = new Recommendation();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split("\\t");
        if (fields.length < 2) {
            return;
        }

        String[] users = fields[0].split(",");
        int commonCount = Integer.parseInt(fields[1]);

        // Emit recommendations for both users
        userKey.set(users[0]);
        recommendation = new Recommendation(users[1], commonCount);
        context.write(userKey, recommendation);

        userKey.set(users[1]);
        recommendation = new Recommendation(users[0], commonCount);
        context.write(userKey, recommendation);
    }
}
