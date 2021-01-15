package com.fengmang.stat.flink.practice;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : ASUS
 * @create 2021/1/15 22:57
 */
public class Flink_Test_source {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<FlinkJob.DataDots> source = FlinkJob.DataSource.source;
        //针对keyBy 操作，只能对pojo类型才能通过name指定key,对于tuple类型，可以通过脚标指定key
//        source.keyBy("dName").print();
        source.keyBy(1).print();

        FlinkJob.env.execute(String.format("FlinkJob:-%s", Flink_Test_source.class.getName()));
    }
}
