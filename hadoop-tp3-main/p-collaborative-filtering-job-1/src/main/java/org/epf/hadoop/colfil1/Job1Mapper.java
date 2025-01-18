package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Job1Mapper extends Mapper<LongWritable, Relationship, Text, Text> {
    private Text userKey = new Text();
    private Text friendValue = new Text();

    @Override
    protected void map(LongWritable key, Relationship value, Context context) throws IOException, InterruptedException {
        // Extract user1 and user2 from the Relationship object
        String user1 = value.getId1();
        String user2WithTimestamp = value.getId2();

        // Remove the timestamp part (after the comma)
        String[] user2Parts = user2WithTimestamp.split(",");
        String user2 = user2Parts[0].trim();

        // Emit bidirectional relationships without timestamps
        userKey.set(user1);
        friendValue.set(user2);
        context.write(userKey, friendValue);

        userKey.set(user2);
        friendValue.set(user1);
        context.write(userKey, friendValue);
    }
}
