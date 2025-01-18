package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Recommendation implements Writable {
    private String userId;
    private int commonRelations;

    public Recommendation() {
    }

    public Recommendation(String userId, int commonRelations) {
        this.userId = userId;
        this.commonRelations = commonRelations;
    }

    public String getUserId() {
        return userId;
    }

    public int getCommonRelations() {
        return commonRelations;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(userId);
        out.writeInt(commonRelations);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.userId = in.readUTF();
        this.commonRelations = in.readInt();
    }

    @Override
    public String toString() {
        return userId + " (" + commonRelations + " relations)";
    }
}
