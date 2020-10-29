

def sh_exec(sh_name):
    sh_cmd = "ssh root@127.0.0.1 'cd $sh_dir;./$job $stat_date'"
    params = {
        '$sh_dir': 'test',
        '$job': 'test',
        '$stat_date': "{{ds}}"
    }

    for (k, v) in params.items():
        sh_cmd = sh_cmd.replace(k, v)

    return sh_cmd



