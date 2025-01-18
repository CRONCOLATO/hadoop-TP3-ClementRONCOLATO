package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;

public class Job2Reducer extends Reducer<UserPair, Text, UserPair, Text> {
    @Override
    protected void reduce(UserPair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        HashSet<String> commonFriends = new HashSet<>();

        for (Text friend : values) {
            commonFriends.add(friend.toString());
        }

        if (commonFriends.size() > 0) {
            context.write(key, new Text(String.valueOf(commonFriends.size())));
        }
    }
}
