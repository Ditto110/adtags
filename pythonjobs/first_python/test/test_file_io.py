"""
file i/o
通过python file i/o
默认读取文本文件，且是UTF-8编码的格式
"""

in_file = "D:\\python_test\\in"
out_file = "D:\\python_test\\out"


# read 后指针会发生偏移，因此如上一次的read操作读取数据将指针移到末尾后，下一次操作将不能读取到数据
# 除非将指针重新置为0
def read_file():
    global file
    try:
        # 默认读取文本文件，且是UTF-8编码的格式，可以通过指定编码和读取的数据类型 rb 表示读取字节码文件（如图片）
        file = open(in_file + "\\words.txt", "r",encoding="UTF-8")
        print(file.name)            # 文件名
        print(file.read())          # 一次性读取指定字节数，若为指定则读取所有
        file.seek(0)                # 重置指针
        print(file.readline())      # 读取一行，包括\n
        file.seek(0)
        readlines = file.readlines() # 一次性读取所有行，包括\n
        for line in readlines:
            print(line.strip())      # 需要去掉\n

    except Exception as e:
        print(e.args)
    finally:
        if file:
            file.close()


# 自动捕获异常/关流方式读取
def read_file2():
    # 默认读取文本文件，且是UTF-8编码的格式，可以通过指定编码和读取的数据类型 rb 表示读取字节码文件（如图片）
    with open(in_file + "\\words.txt", "r",encoding="UTF-8") as file:
        print(file.name)                # 文件名
        print(file.read())              # 一次性读取指定字节数，若为指定则读取所有
        print(file.readline())          # 重置指针
        readlines = file.readlines()    # 读取一行，包括\n
        for line in readlines:          # 一次性读取所有行，包括\n
            print(line.strip())         # 需要去掉\n


# 写入文件
def write_file():
    with open(out_file+"\\write1.txt",'a') as file:
        file.write("hello,world\n")
        file.writelines("hello world")


if __name__ == '__main__':
    pass
    # read_file()
    # read_file2()
    write_file()

