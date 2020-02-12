import paho.mqtt.client as mqtt
import time
from publisher_component import data_generator
from datetime import date


class RouteHandler:

    client = mqtt.Client("route-handler")

    #used for testing the manager component
    current_date = date.today().__str__()
    start_date_string = current_date + " 08:00"
    end_date_string = current_date + " 23:00"
    date_dict = {"startDate": start_date_string, "endDate": end_date_string}


    def __init__(self, ip_address, port, topic):
        self.ip_address = ip_address
        self.port = port
        self.topic = topic

    # This is the Publisher
    def publish(self):
        self.client.connect(self.ip_address) # used for testing with localhost
        #self.client.connect(self.ip_address + ":" + self.port)  #used when running on different devices

        self.client.on_disconnect = self.on_disconnect  #Fault tolerance. In case the connection with the broker is lost
        self.client.loop_start()

        while True:
            data = data_generator.generate_data()  #a route created by the data_generator
            self.client.publish(self.topic, data)  #publishes the route to the broker

            #Used for testing the manager component
            #self.client.publish("date_filter_param", self.date_dict.__str__())
            #self.client.publish("purpose_param", "work")
            ###

            time.sleep(0.1)

    # Used for fault tolerance. Runs if the connection with the broker is lostf
    def on_disconnect(self):
        for _ in range(6):
            self.client.connect(self.ip_address)
            if self.client.is_connected():
                break
            time.sleep(10)

a = RouteHandler("localhost", "", "external")
a.publish()
