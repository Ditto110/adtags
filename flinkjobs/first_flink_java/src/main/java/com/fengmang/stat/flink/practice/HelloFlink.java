package com.fengmang.stat.flink.practice;

import com.fengmang.stat.flink.pojo.DataDots;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/12 9:23
 */
public class HelloFlink {
    public static void main(String[] args) throws Exception {
        System.out.println("hello flink_java");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().enableSysoutLogging();

        DataStreamSource<DataDots> source = env.addSource(new SourceFunction<DataDots>() {
            Random random = new Random();

            public void run(SourceContext<DataDots> sourceContext) throws Exception {
                int dot_index;
                DataDots dots;
                while (true) {
                    dot_index = random.nextInt(100);
                    dots = new DataDots("dot_" + dot_index, dot_index, System.currentTimeMillis() / 1000);
                    sourceContext.collect(dots);
                    Thread.sleep(1000);
                }
            }

            public void cancel() {
            }
        });

//        source.keyBy("dName").print().setParallelism(2);
        SingleOutputStreamOperator<DataDots> ds = source.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<DataDots>() {
            Watermark watermark;
            long currentTimestamp = 0L;
            long offset = 2L;

            @Nullable
            public Watermark getCurrentWatermark() {
                return new Watermark(currentTimestamp - offset);
            }

            public long extractTimestamp(DataDots dataDots, long l) {
                Long ts = dataDots.getTs();
                currentTimestamp = Math.max(ts, currentTimestamp);
                return 0;
            }
        });

        ds.keyBy("dName").window(TumblingEventTimeWindows.of(Time.seconds(10))).sum("dDots").print();

        env.execute("Flink-job:" + HelloFlink.class.getName());
    }
}
