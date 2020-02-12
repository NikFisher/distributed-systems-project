import paho.mqtt.client as mqtt
import time
from pipe_and_filter_component import pipe_and_filter
import ast
from datetime import date


class DataManager:

    client = mqtt.Client("manager")
    filtered_data = ""

    def __init__(self, ip_address, port, sub_topic, pub_topic):
        self.ip_address = ip_address
        self.port = port
        self.sub_topic = sub_topic
        self.current_date = date.today().__str__()
        self.date_filter_param = {"startDate": "2020-01-17 08:00", "endDate": "2020-01-17 23:00"}
        self.purpose_param = "from work"


    def on_connect(self, client, userdata, flags, rc):
        print("Connected with result code " + str(rc))

    def on_message(self, client, userdata, msg):
        #receives the user's requested timeframe
        if msg.topic == "date_filter_param":
            msg.payload = msg.payload.decode("utf-8")
            date_filter_param_str = str(msg.payload)
            self.date_filter_param = ast.literal_eval(date_filter_param_str)

        #receives the user's requested purpose
        if msg.topic == "purpose_param":
            msg.payload = msg.payload.decode("utf-8")
            self.purpose_param = str(msg.payload)

        #receives the route that was published from the route_handler
        msg.payload = msg.payload.decode("utf-8")
        unfiltered_data = str(msg.payload)
        result_dict = ast.literal_eval(unfiltered_data)  #converts string into python dictionary

        #runs the route through the pipe_and_filter
        e = pipe_and_filter.Run(result_dict, self.date_filter_param.get("startDate"), self.date_filter_param.get("endDate"), self.purpose_param)
        self.filtered_data = e.run_all()

        print(self.filtered_data)  #used for testing

    def subscribe_publish(self):
        self.client.connect(self.ip_address) # used for testing with localhost
        #self.client.connect(self.ip_address + ":" + self.port) #used when running on different computers
        self.client.on_disconnect = self.on_disconnect #Fault tolerance. In case the connection with the broker is lost
        self.client.on_connect = self.on_connect
        self.client.loop_start()
        while True:
            self.client.on_message = self.on_message
            self.client.subscribe(self.sub_topic) #The route published by the data_generator
            self.client.subscribe("date_filter_param") #The user's requested timeframe
            self.client.subscribe("purpose_param")  #The user's requested purpose

            #This publishes the filtered data to the broker so that it can be used by the application
            #If the data does not match the user's requested parameters, the pipe_and_filter returns a value of None
            #This ensures that the data that is published is the correct kind.
            if isinstance(self.filtered_data, dict):
                self.client.publish("filtered_data", str(self.filtered_data))
            time.sleep(0.1)

    #Used for fault tolerance. Runs if the connection with the broker is lost
    def on_disconnect(self):
        for _ in range(6):
            self.client.connect(self.ip_address)
            if self.client.is_connected():
                break
            time.sleep(10)

a = DataManager("localhost", "", "external", "")
a.subscribe_publish()