package com.fengmang.stat.spark.test

import org.apache.spark.graphx.{Edge, Graph, VertexId, VertexRDD}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object TestGraphX {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[*]")
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("warn")

    val list = List((10001, "a"), (10002, "b"), (10003, "c"))

    val VD: RDD[(Long, String)] = sc.parallelize(list).map(e => (e._1.toLong, e._2))

    val idMappings: List[(Long, Long)] = list.tail.map(e => (e._1.toLong, list.head._1.toLong))

    val ED: RDD[Edge[String]] = sc.parallelize(idMappings).map(e => Edge(e._1, e._2))

    val vertices: VertexRDD[VertexId] = Graph(VD, ED).connectedComponents().vertices

    vertices.map(_.swap).foreach(println)

    spark.close()
  }
}
