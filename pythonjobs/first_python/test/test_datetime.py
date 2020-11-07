"""
日期处理api
"""
import datetime


# 获取日期，时间
def get_date():
    today = datetime.datetime.today()
    yesterday = today - datetime.timedelta(days=1)

    print("current day:%s" % today)
    print("yesterday:{}".format(yesterday))


# 日期格式化
def format_date():
    today = datetime.datetime.today()
    str_today = today.strftime("%Y-%m-%d")
    print(str_today)
    print(datetime.datetime.now().strftime("%Y-%m-%d"))


# 解析日期
def parse_date():
    parsed_time = datetime.datetime.strptime("2020-10-12", "%Y-%m-%d")
    print(" parsed time:%s" % parsed_time)


# 生成指定日期
def get_define_date():
    d = datetime.datetime(2020, 10, 28)
    print(d)


if __name__ == '__main__':
    # get_date()
    # format_date()
    # parse_date()
    get_define_date()
    pass

