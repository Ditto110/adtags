import numpy as np

"""
准确地说python中没有数组，只有列表list和元组tuple，数组是由numpy库定义的，使用前必须先安装numpy
list的长度可变，并且元素的数据类型可以不相同可以进行增删改查；tuple 是不可变的，一旦确定就是不能改变的，不能对其中的元素进行增删改
"""


def test_list():
    li = list()
    # 或者 li = []
    li.append("a")  # 添加元素
    li.insert(0, "3")  # 指定的角标添加元素
    print(",".join(li))  # 列表转为字符串


def test_list2():
    li = [1, 2, 2, ["a", "c", "b"], "a"]
    li.insert(0, "3")  # 指定的角标添加元素
    print(li)  # 列表转为字符串
    print(li[3])


def test_tuple():
    a = (1, 2, 4, ("a", "b", "c"))
    print(a)
    print(a[3])


def test_array():
    array = np.array([[1, 2, 3, 4, 5], ["a", "b", "c", "d"]])
    print(array)


if __name__ == '__main__':
    test_array()
    pass
