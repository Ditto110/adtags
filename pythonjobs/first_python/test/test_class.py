"""
class api
"""


# 定义类1
class EcsInfo:
    def __init__(self,ip,port,host_name):
        self.ip = ip
        self.port = port
        self.host_name = host_name


# 定义类2
class ClusterInfo:
    addr="广州华为云"

    def show_info(self):
        print("机房地址:%s" % self.addr)


def get_ecs_info():
    ecs = EcsInfo("192168.52.13", 22, "测试机器")
    print(ecs.ip,ecs.port,ecs.host_name)


def get_cluster_info():
    c_info = ClusterInfo()
    c_info.show_info()
    print(c_info.addr)


if __name__ == '__main__':
    get_ecs_info()

