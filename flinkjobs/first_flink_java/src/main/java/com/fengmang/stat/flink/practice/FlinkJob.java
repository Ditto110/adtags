package com.fengmang.stat.flink.practice;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : ASUS
 * @create 2021/1/15 22:54
 */
interface FlinkJob {

    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    class DataSource {
        public static DataStreamSource<DataDots> source = FlinkJob.env.addSource(new SourceFunction<FlinkJob.DataDots>() {
            Random random = new Random();
            public void run(SourceContext<FlinkJob.DataDots> sourceContext) throws Exception {
                int dot_index;
                FlinkJob.DataDots dot;
                while (true) {
                    dot_index = random.nextInt(10);
                    dot = new FlinkJob.DataDots("dot_" + dot_index, dot_index, System.currentTimeMillis());
                    sourceContext.collect(dot);
                    Thread.sleep(1000);
                }
            }

            public void cancel() {
            }
        });
    }

    class DataDots{
        private String dName;
        private Integer dDots;
        private Long ts;

        //pojo 类必须指定默认构造参数
        public DataDots() {
        }

        public DataDots(String dName, Integer dDots, Long ts) {
            this.dName = dName;
            this.dDots = dDots;
            this.ts = ts;
        }

        public String getdName() {
            return dName;
        }

        public void setdName(String dName) {
            this.dName = dName;
        }

        public Integer getdDots() {
            return dDots;
        }

        public void setdDots(Integer dDots) {
            this.dDots = dDots;
        }

        public Long getTs() {
            return ts;
        }

        public void setTs(Long ts) {
            this.ts = ts;
        }

        @Override
        public String toString() {
            return "DataDots{" +
                    "dName='" + dName + '\'' +
                    ", dDots=" + dDots +
                    ", ts=" + ts +
                    '}';
        }
    }


}
