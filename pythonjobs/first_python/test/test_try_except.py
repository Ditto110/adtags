"""
捕获异常
"""


def test_while():
    try:
        print(1 / 0)
    except Exception as e:
        print(e.args)
    finally:
        print("done")


if __name__ == '__main__':
    test_while()
    pass
