import datetime
import random

sql_commands = []
start_date = datetime.datetime(2020, 5, 1)
num_records = 5
days = range(31)

coordinates = [[10.809371681681368,
                52.43920606296933
                ], [
					10.763967409132312,
					52.42668262294646
				],[
					10.763309053623491,
					52.42729173101862
				], [
					10.763256199447135,
					52.42728808729468
				], [
					10.76282984464023,
					52.4268741945577
				]]

trafficrecorder_ids = range(1, num_records + 1)
# add trafficrecorders
for trafficrecorder_id in trafficrecorder_ids:
    longitude = coordinates[trafficrecorder_id-1][0]
    latitute = coordinates[trafficrecorder_id-1][1]
    sql_commands.append(
        f"INSERT into TRAFFICRECORDER VALUES (1, 'in', 'neighbor', 'specialty', 'location', {latitute}, {longitude}, {trafficrecorder_id});")


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
