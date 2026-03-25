#!/usr/bin/env python

#
# neprečiščeno, 
#  določanje nadmorse višine z BME280 na I2c
#


import time
from bme280pi import Sensor


print("""relative-altitude.py - Calculates relative altitude from pressure.

Press Ctrl+C to exit!

""")

# Initialise the BME280
 
sensor=Sensor() #Sensor(address=0x76)

baseline_values = []
baseline_size = 100

print("Collecting baseline values for {:d} seconds. Do not move the sensor!\n".format(baseline_size))

for i in range(baseline_size):
    pressure = sensor.get_pressure()
    baseline_values.append(pressure)
    datX = sensor.get_data()
    print(datX)
    time.sleep(1)

baseline = sum(baseline_values[:-25]) / len(baseline_values[:-25])

print(baseline)

while True:
    #altitude = sensor.get_altitude(qnh=baseline)
    altitude = 44330*(1-(sensor.get_pressure()/1010.00)**(1/5.255))
    print('Relative altitude: {:05.2f} metres'.format(altitude))
    time.sleep(1)

#hh = 44330 * ( 1-(sensor.get_pressure()/1013.25)**(1/5.255) )
#print(hh);


'''
nadmorska višina iz tlaka:

(international barometric formula / 
  Bosch-Sensortech, FAQ, https://community.bosch-sensortec.com/t5/Question-and-answers/How-to-calculate-the-altitude-from-the-pressure-sensor-data/qaq-p/5702

H = 44330 * [1 - (P/p0)^(1/5.255) ]

H = altitude (m)
P = measured pressure (Pa) from the sensor
p0 = reference pressure at sea level (e.g. 1013.25hPa)
referenčna temperatura T=15(C)
opomba: pri nas je p0 nekje okoli 1010 (glej kodo), sicer pa je to potrebno vsakokrat kalibrirati


natančnejaša formula z upoštevanjem temerature je:

    ((p0/P)^(1/5.257)-1)*(T+273.15)
H=---------------------------------- 
          0.0065

'''

