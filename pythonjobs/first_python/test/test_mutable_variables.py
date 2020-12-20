"""
 @Author: ASUS
 @Create: 2020/12/5 19:48
 @Desc: 针对python函数传递可变参数
"""


# 将传递的参数封装成一个元组
def test_function(*v):
    total = 0
    for i in v:
        total = i + total
    print(total)
    return total


# 将传递的参数封装成一个dict
def test_function2(**kv):
    total = 0
    for i in kv.values():
        total = total + i
    print(total)
    return total


# 针对普通参数，可变参数，默认参数，关键字参数顺序
def test_function3(a, *tup, c=1, d=2, **kwargs):
    total = 0
    print(kwargs)
    for i in kwargs.values():
        total = total + i
    print("m:%s" % kwargs['m'])
    print(tup)
    for v in tup:
        total = total + v
    return total + a + c + d


if __name__ == '__main__':
    # test_function(1, 2, 3)
    # test_function2(a=1, b=2, c=3)
    test_function3(1, 2, 3, 4, m=1, n=2)
    pass
