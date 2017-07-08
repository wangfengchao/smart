package com.smart.spark.sort_second;

import scala.math.Ordered;

import java.io.Serializable;

/**
 * 二次排序
 * Created by fc.w on 2017/6/26.
 */
public class SecondarySortKey implements Ordered<SecondarySortKey>, Serializable {

    //在自定义的key中定义要排序的列
    private int first;
    private int second;

    public boolean $greater(SecondarySortKey other) {
        if (this.first > other.getFirst()) {
            return true;
        } else if (this.first == other.getFirst() && this.second > other.getSecond()) {
            return true;
        }

        return false;
    }

    public boolean $greater$eq(SecondarySortKey other) {
        if (this.$greater(other)) {
            return true;
        } else if (this.first == other.getFirst() && this.second == other.getSecond()) {
            return true;
        }

        return false;
    }

    public boolean $less(SecondarySortKey other) {
        if (this.first < other.getFirst()) {
            return true;
        } else if (this.first == other.getFirst() && this.second < other.getSecond()) {
            return true;
        }

        return false;
    }

    public boolean $less$eq(SecondarySortKey other) {
        if (this.$less(other)) {
            return true;
        } else if (this.first == other.getFirst() && this.second == other.getSecond()) {
            return true;
        }

        return false;
    }

    public int compare(SecondarySortKey other) {
        if (this.first - other.getFirst() != 0) {
            return this.first - other.getFirst();
        } else {
            return this.second - other.getSecond();
        }
    }

    public int compareTo(SecondarySortKey other) {
        if (this.first - other.getFirst() != 0) {
            return this.first - other.getFirst();
        } else {
            return this.second - other.getSecond();
        }
    }


    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
