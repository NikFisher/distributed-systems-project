from datetime import datetime
from datetime import date


#Checks if the route's timeframe matches falls within the user's requested timeframe
class TimeCheckFilter:

    def __init__(self, data):
        self.data = data

    def get_date(self):
        departure_time = self.data.get("timeOfDeparture")
        return departure_time

    def date_time_test(self, start_dt_str, end_dt_str):
        received_dt = datetime.strptime(self.get_date(), '%Y-%m-%d %H:%M')
        start_dt = datetime.strptime(start_dt_str, '%Y-%m-%d %H:%M')
        end_dt = datetime.strptime(end_dt_str, '%Y-%m-%d %H:%M')

        if received_dt >= start_dt and received_dt <= end_dt:
            return self.data
        else:
            return None

#Check's if the purpose in the route matches with the user's requested purpose.
class PurposeCheckFilter:

    def __init__(self, data, purpose):
        self.data = data
        self.purpose = purpose

    def purpose_test(self):
        received_purpose = self.data.get("purpose")
        if received_purpose == self.purpose:
            return self.data
        else:
            return

class CoordinateCheckFilter:
    def __init__(self, data):
        self.data = data

    def coordinate_test(self):

        origin_latitude = self.data.get("origin").get("latitude")
        origin_longitude = self.data.get("origin").get("longitude")
        destination_latitude = self.data.get("destination").get("latitude")
        destination_longitude = self.data.get("destination").get("longitude")
        if (57.625166 <= origin_latitude <= 57.8) and (57.625166 <= destination_latitude <= 57.8):
            if (11.863835 <= origin_longitude <= 13) and (11.863835 <= origin_longitude <= 13):
                return self.data


#Runs data through the TimeCheckFilter
class Pipe1:

    def __init__(self, data, start_time, end_time):
        self.data = data
        self.start_time = start_time
        self.end_time = end_time

    def filter_1(self):
        filter = TimeCheckFilter(self.data)
        filtered_data = filter.date_time_test(self.start_time, self.end_time)
        return filtered_data

#Runs data through the the PurposeCheckFilter
class Pipe2:

    def __init__(self, data, purpose):
        self.data = data
        self.purpose = purpose

    def filter_2(self):
        filter = PurposeCheckFilter(self.data, self.purpose)
        filtered_data = filter.purpose_test()
        return filtered_data

class Pipe3:
    def __init__(self, data):
        self.data = data

    def filter_3(self):
        filter = CoordinateCheckFilter(self.data)
        filtered_data = filter.coordinate_test()
        return filtered_data


#Combines all the pipes and returns the filtered data.
class Run:

    def __init__(self, data, start_time, end_time, purpose):
        self.data = data
        self.start_time = start_time
        self.end_time = end_time
        self.purpose = purpose

    def push_pipe_1(self):
        pipe = Pipe1(self.data, self.start_time, self.end_time)
        filtered_data = pipe.filter_1()
        return filtered_data

    def push_pipe_2(self):
        pipe = Pipe2(self.push_pipe_1(), self.purpose)
        filtered_data = pipe.filter_2()
        return filtered_data

    def push_pipe_3(self):
        pipe = Pipe3(self.push_pipe_2())
        filtered_data = pipe.filter_3()
        return filtered_data

    def run_all(self):
        final_data = self.push_pipe_3()
        return final_data
