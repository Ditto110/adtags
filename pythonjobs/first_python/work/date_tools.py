from datetime import datetime, timedelta
import calendar

fmt_ymd = "%Y-%m-%d"
fmt_ymd_s = "%Y%m%d"


def get_today(the_day, delta_day=0):
    today = datetime.strptime(the_day, fmt_ymd) - timedelta(delta_day)
    v_today = datetime.strftime(today, fmt_ymd)
    v_today_s = datetime.strftime(today, fmt_ymd_s)
    return v_today, v_today_s


def get_week(the_day):
    today = datetime.strptime(the_day, fmt_ymd)
    isoweekday = today.isoweekday()
    week_start = today - timedelta(isoweekday - 1)
    week_end = today + timedelta(7 - isoweekday)
    last_week_start = week_start - timedelta(weeks=1)
    last_week_end = week_end - timedelta(weeks=1)
    next_week_start = week_start + timedelta(weeks=1)
    next_week_end = week_end + timedelta(weeks=1)
    v_week_first = week_start.strftime(fmt_ymd)
    v_week_first_s = week_start.strftime(fmt_ymd_s)
    v_week_end = week_end.strftime(fmt_ymd)
    v_week_end_s = week_end.strftime(fmt_ymd_s)
    v_last_week_start = last_week_start.strftime(fmt_ymd)
    v_last_week_start_s = last_week_start.strftime(fmt_ymd_s)
    v_last_week_end = last_week_end.strftime(fmt_ymd)
    v_last_week_end_s = last_week_end.strftime(fmt_ymd_s)
    v_next_week_start = next_week_start.strftime(fmt_ymd)
    v_next_week_start_s = next_week_start.strftime(fmt_ymd_s)
    v_next_week_end = next_week_end.strftime(fmt_ymd)
    v_next_week_end_s = next_week_end.strftime(fmt_ymd_s)

    return (v_week_first,
            v_week_first_s,
            v_week_end,
            v_week_end_s,
            v_last_week_start,
            v_last_week_start_s,
            v_last_week_end,
            v_last_week_end_s,
            v_next_week_start,
            v_next_week_start_s,
            v_next_week_end,
            v_next_week_end_s)
    pass


def get_month(the_day):
    today = datetime.strptime(the_day, fmt_ymd)
    month_start = datetime(today.year, today.month, 1)
    month_end = datetime(today.year, today.month, calendar.monthrange(today.year, today.month)[1])
    v_month_start = month_start.strftime(fmt_ymd)
    v_month_start_s = month_start.strftime(fmt_ymd_s)
    v_month_end = month_end.strftime(fmt_ymd)
    v_month_end_s = month_end.strftime(fmt_ymd_s)
    return v_month_start, v_month_start_s, v_month_end, v_month_end_s

    pass


def check_file():
    v_dates = {
        "v_today": '20201017',
        "v_today_d": '20201017'
    }
    sqls = "select *,'$v_today' from xxx_'$v_today_d' "
    for (k, v) in v_dates.items():
        if k == "v_today_d":
            sqls = sqls.replace('\'$' + k + '\'', v)
        else:
            sqls = sqls.replace('\'$' + k + '\'', '\'' + v + '\'')

    print(sqls)


if __name__ == '__main__':
    # today = get_today('2020-10-10')
    # week = get_week(today[0])
    # month = get_month(today[0])
    # print(today)
    # print(week)
    # print(month)
    check_file()
    pass
