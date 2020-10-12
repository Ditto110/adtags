"""
循环遍历
"""
from datetime import datetime
import time


# while
def test_while():
    while True:
        print(datetime.now())   # 获取日期
        print(time.time())  # 获取时间毫米数
        time.sleep(2)   # 线程休眠，秒 可以是浮点数


# for
def test_for():
    arr = [12, 2, 3, 4, 5]
    for e in arr:
        if e == 4:
            continue
        else:
            print(e)


if __name__ == '__main__':
    test_while()
    test_for()
    pass
