"""
通过os组件实现文件io
"""
import os

in_file = "D:\\python_test\\in"
out_file = "D:\\python_test\\out"


def test_op():
    os.remove("")   # 删除文件路径
    os.removedirs("")   # 删多级文件路径


# 创建目录
def test_mkdir():
    os.makedirs(out_file + "\\11\\22", True)
    os.mkdir(out_file + "\\33")

# 读取文件
def test_read_file():
    file = os.open(in_file + "\\words.txt", os.O_RDWR | os.O_CREAT)
    print(os.read(file,10))
    os.close(file)

# 写入文件
def test_write_file():
    file = os.open(in_file + "\\words.txt", os.O_RDWR | os.O_CREAT)
    os.write(file,b"hello")
    os.close(file)


if __name__ == '__main__':
    pass
    # test_mkdir()
    test_read_file()
