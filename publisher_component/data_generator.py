from faker import Faker
import random
import time
from datetime import date

# faker allows us to generate fake data such as time
fake_data = Faker()

purpose = ["work", "school", "other", "distraction"]

request_id = 0

# method to generate fake time
def generate_time():

    local_time = fake_data.time(pattern="%H:%M", end_datetime=None)
    current_date = date.today()
    date_time = current_date.__str__() + " " + local_time
    return date_time

# method to generate coordinates within a given grid (gothenburg)

def generate_data():
    start_lat = random.uniform(57.625166, 57.827873)
    start_lat_round = round(start_lat, 6)

    start_lng = random.uniform(11.863835, 12.201881)
    start_lng_round = round(start_lng, 6)

    end_lat = random.uniform(start_lat_round, 57.827873)
    end_lat_round = round(end_lat, 6)

    end_lng = random.uniform(start_lng_round, 12.201881)
    end_lng_round = round(end_lng, 6)

    device_id = random.randrange(100000000, 999999999, 1)

    global request_id
    request_id += 1

    route ={
        "deviceId": device_id,
        "requestId": request_id,
        "origin": {
            "latitude": start_lat_round,
            "longitude": start_lng_round
        },
        "destination": {
            "latitude": end_lat_round,
            "longitude": end_lng_round
        },
        "timeOfDeparture": generate_time(),
        "purpose": purpose[random.randrange(0,4,1)],
        "issuance": int(round(time.time() * 1000))
    }

    route_string = route.__str__()

    return route_string