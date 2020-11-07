import pymysql
import argparse


# 通过pymysql读取数据
def read_from_mysql():
    db = pymysql.connect("192.168.52.13", "root", "", "db_dmp_data_collect")
    cursor = db.cursor()
    cursor.execute("select * from tb_sky_consumer_orders limit 10")
    fetchall = cursor.fetchall()

    for item in fetchall:
        print(item)

    db.close()


# 向mysql中写入数据
def write_to_mysql(data_list):
    db = pymysql.connect("stat-api-host", "root", "fm@skyworth", "db_dmp_data_collect")
    cursor = db.cursor()
    sql = """insert into tb_sky_consumer_orders (device_id,user_id,phone,order_no,order_money,refund_money) values (%s,%s,%s,%s,%s,%s) """
    cursor.executemany(sql, data_list)   # 写入多条数据(参数可列表)
    # cursor.execute(sql,data_list[0]) # 写入单条数据(参数可以是元组或数组)
    cursor.close()
    db.commit()
    db.close()

    pass


# 读取文件数据后写入mysql
def read_file():
    args = check_argument()
    with open(args.dir, "r", encoding="UTF-8") as file:
        lines = file.readlines()
        data_list = list()
        for line in lines:
            values = line.strip().split(",")
            data_list.append(values)
            if len(data_list) == 100:
                write_to_mysql(data_list)
                data_list = []
        # 写入余量
        if len(data_list) != 0:
            write_to_mysql(data_list)


def check_argument():
    parser = argparse.ArgumentParser()
    parser.add_argument('-d', '--dir', type=str, required=True, help="文件路径不能为空")
    args = parser.parse_args()
    # print(args.dir)
    return args
    pass


if __name__ == '__main__':
    read_file()
    pass
