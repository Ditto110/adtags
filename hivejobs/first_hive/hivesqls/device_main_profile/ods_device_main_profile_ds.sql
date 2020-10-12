--
CREATE TABLE IF NOT EXISTS ods_device_main_profile_ds(
    mid string comment '主键id',
    mac string comment '有线mac',
    wifiMac string comment '无线mac',
    deviceId string comment '设备id'
) PARTITIONED BY (stat_date string comment '按日期分区字段')
STORED AS ORC ;

