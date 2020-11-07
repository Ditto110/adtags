import pymysql
import argparse

'''
created by: jiangbo
createTime: 2020/11/07
descriptions: file is used to append sky_orders_2017 to db_dmp_data_collect
'''


# 向mysql中写入数据
def write_to_mysql(data_list):
    db = pymysql.connect("192.168.52.13", "root", "fm@skyworth", "db_dmp_data_collect")
    cursor = db.cursor()
    sql = """insert into tb_sky_consumer_orders (device_id,user_id,phone,order_no,order_money,refund_money) values (%s,%s,%s,%s,%s,%s) """
    cursor.executemany(sql, data_list)  # 写入多条数据(参数可列表)
    cursor.close()
    db.commit()
    db.close()

    pass


# 读取文件数据后写入mysql
def read_file():

    chk_value = lambda v: 0 if v == "" else v

    args = check_argument()
    with open(args.dir, "r", encoding="UTF-8") as file:
        lines = file.readlines()
        data_list = list()
        for line in lines:
            values = line.strip().split(",")
            data_list.append((values[0],values[1],values[2],values[3],values[4],chk_value(values[5])))
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
