import datetime
import random

sql_commands = []
start_date = datetime.datetime(2020, 5, 1)
num_records = 5
days = range(31)
trafficrecorder_ids = range(1, num_records + 1)
# add trafficrecorders
for trafficrecorder_id in trafficrecorder_ids:
    sql_commands.append(f"INSERT into TRAFFICRECORDER VALUES (1, 'in', 'neighbor', 'specialty', 'location', 23.32, 50.21, {trafficrecorder_id});")
    
    
# add trafficrecords
for trafficrecorder_id in trafficrecorder_ids:
    for day in days:
        record_date = start_date + datetime.timedelta(days=day)
        date_str = record_date.strftime("%Y-%m-%d 00:00:00")
        car_count = random.randint(0, 100)
        sql_commands.append(f"INSERT into TRAFFICRECORD (RECORDDATE, WEEKDAY, WEEKEND, HOLIDAY, VACATIONLOWERSAXONY, PLANTHOLIDAY, CARCOUNT, TRAFFICRECORDER_ID) "
                            f"VALUES ('{date_str}', 2, 1, 1, 1, 1, {car_count}, {trafficrecorder_id});")
    
with open("persistence/src/test/resources/db/dummydata/dummy_data.sql", "w") as f:
    f.write("\n".join(sql_commands))