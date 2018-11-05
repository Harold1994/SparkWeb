package com.harold.spark.util;

import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;


import java.io.IOException;
import java.util.Map;

public class HBaseUtils {
    Admin admin = null;
    Configuration conf = null;
    Connection connection = null;
    private HBaseUtils() {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "localhost:2181");
        conf.set("hbase.rootdir", "hdfs://localhost:9000/hbase");

        try {
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HBaseUtils instance = null;
    public static synchronized HBaseUtils getInstance() {
        if (null == instance) {
            instance = new HBaseUtils();
        }
        return instance;
    }

    public Table getTable(String tableName) {
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    public Map<String, Long> query(String tableName, String condition) throws IOException {
        Map<String, Long> map = new HashedMap();
        Table table = connection.getTable(TableName.valueOf(tableName));
        String cf = "info";
        String qualifier = "click_count";

        Scan scan  =new Scan();
        Filter filter  = new PrefixFilter(Bytes.toBytes(condition));
        scan.setFilter(filter);

        ResultScanner rs = table.getScanner(scan);
        for (Result rws : rs) {
            String row = Bytes.toString(rws.getRow());
            long clickCount = Bytes.toLong(rws.getValue(cf.getBytes(), qualifier.getBytes()));
            map.put(row, clickCount);
        }
        return map;
    }
}
