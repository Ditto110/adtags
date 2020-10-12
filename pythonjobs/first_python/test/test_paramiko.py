"""
ssh/ftp等工具套件：paramiko
"""

import paramiko
import traceback  # 捕获异常详情


def ssh_remote_ecs():
    ip = "192.168.52.13"
    port = 22
    ssh = paramiko.SSHClient()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy)
    try:
        ssh.connect(ip, port, "root", "sw@123!")
        command = ssh.exec_command("ls")
        print(command)
    except Exception as e:
        print(e.args)
        print("ssh exception")
        print(traceback.format_exc())  # 通过traceback 获取异常详情


if __name__ == '__main__':
    ssh_remote_ecs()
