"""
lambda
"""


def test_lambda(x):
    # 定义lambda 表达式
    result = lambda v: "good" if v > 10 else "bad"
    print(result(x))


if __name__ == '__main__':
    pass
    test_lambda(15)