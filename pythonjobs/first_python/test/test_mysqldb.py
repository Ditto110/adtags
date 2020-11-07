import MySQLdb
import argparse


# 通过pymysql读取数据
def read_from_mysql():
    db = MySQLdb.connect("192.168.52.13", "root", "", "db_dmp_data_collect")
    cursor = db.cursor()
    cursor.execute("select * from tb_sky_consumer_orders limit 10")
    fetchall = cursor.fetchall()

    for item in fetchall:
        print(item)

    db.close()


# 向mysql中写入数据
def write_to_mysql():
    db = MySQLdb.connect("192.168.52.13", "root", "", "db_dmp_data_collect")
    cursor = db.cursor()
    sql = """insert into tb_sky_consumer_orders (device_id,user_id,phone,order_no,order_money,refund_money) values (%s,%s,%s,%s,%s,%s) """
    # cursor.executemany(sql, [(1,2,3,4,4,5),(6,6,6,6,6,6)])   # 写入多条数据(参数可列表)
    cursor.execute(sql,[1,2,3,4,4,5]) # 写入单条数据(参数可以是元组或数组)
    cursor.close()
    db.commit()
    db.close()

    pass


if __name__ == '__main__':
    # write_to_mysql()

    print(isinstance({"a":1}, dict))


    pass
