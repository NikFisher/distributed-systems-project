import paho.mqtt.client as mqtt
import time

# This is only used for testing and is not used in the final product.
# It subscribes to the filtered data that has been published by the route handler.

def on_connect(client, userdata, flags, rc):
    print("Connected with result code " + str(rc))
# states what to do when a message arrives

def on_message(client, userdata, msg):

    print(msg.topic + " " + str(msg.payload))


client = mqtt.Client("test-subscriber")
client.connect("localhost")
client.on_connect = on_connect
client.loop_start()

while True:
    client.on_message = on_message
    client.subscribe("external")
    time.sleep(1)



