"""
读写二进制文件
"""

in_file = "D:\\python_test\\in"
out_file = "D:\\python_test\\out"


# 读取一张图片写入到另一个文件(即图片复制)
def copy_file():
    with open(in_file + "\\fj.jpg", "rb") as infile:
        with open(out_file + "\\fj_copping.jpg", "wb") as ofile:
            ofile.write(infile.read())


if __name__ == '__main__':
    pass
    copy_file()
