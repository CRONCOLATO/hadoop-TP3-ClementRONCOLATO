package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Job1Reducer extends Reducer<Text, Text, Text, Text> {
    private Text result = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // Use a Set to store unique friends
        Set<String> uniqueFriends = new HashSet<>();

        for (Text value : values) {
            uniqueFriends.add(value.toString());
        }

        // Join the unique friends into a single string
        String joinedFriends = String.join(",", uniqueFriends);

        // Emit the user and their list of friends
        result.set(joinedFriends);
        context.write(key, result);
    }
}
