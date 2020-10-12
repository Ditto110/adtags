"""
argparse
"""
import argparse

parser = argparse.ArgumentParser()
# 非必须参数
parser.add_argument("-c", "--city", type=str, required=False, default="成都",help="地区信息")
# 必须参数
parser.add_argument("-p", "--province", type=str, required=True, help="地区信息")
args = parser.parse_args()


# 获取参数
def get_args():
    print("省份:%s,城市:%s" % (args.province,args.city))


if __name__ == '__main__':
    pass
    get_args()
