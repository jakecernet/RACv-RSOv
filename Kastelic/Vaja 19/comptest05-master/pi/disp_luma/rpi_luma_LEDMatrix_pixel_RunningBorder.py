"""
original : draw on canvas
Draw random dots

Draw random dots je lep primerček prižiganja in ugašanja posameznih 'pikslov' na matričnem displayu
ima pa par omejitev:
	- zapiranje canvas-a omeni tudi 'brisanje' vsebine display-a
          (glej sekvenco izrisa štirih točk,nato naslednjo sekvenc)
    - canvas uporablja Image(PIL); refresh canvas-a pomeni izris celotne vsebine
          dislay-a (tudi vrstice, v katerih ni bilo sprememb)
       

1. uporabimo briljantno luma.led.matrix
2. definiramo metode:
	set_pixel(row,col)
	unset_pixel(row,col)
	toggle_pixel(rox,col)
3. omejitev:
   ne deluje na kaskadno vezanih prikazovalnikih (no z malo časa, 3EUR stroška, in 7 dni od polcev za še en display)

"""

import time
import random
from luma.led_matrix.device import max7219
from luma.core.interface.serial import spi, noop
from luma.core.render import canvas

#spidev0.0
serial = spi(port=0, device=0, gpio=noop())
device = max7219(serial, cascaded=1, block_orientation=0, rotate=0)

# tekoča vsebina prikazovalnika (LED Matrix 8x8); nekam si je potrebno pomniti, ker prikazovalnik pač ne
# zmore vračati svojega trenutnega stanja
di={1:0,2:0,3:0,4:0,5:0,6:0,7:0,8:0}

# prižge pixel na dani koordinati
def set_pixel(x,y):
    print(x,y,(y-1)**2)
    ny = di.get(x)|(2**(y-1))  # sem moral uporabiti 2**n !!
    di[x]=ny
    device.data((x,ny))
    print(di)

# ugasne piksel na izbrani koordinati
def unset_pixel(x,y):
    ny=di.get(x)&~(1<<(y-1))  
    di[x]=ny
    device.data((x,ny))
    print(di)

# na ja, zamenja trenutno stanje LED na lokaciji
def toggle_pixel(x,y):
    ny=di.get(x)^(1<<(y-1))
    di[x]=ny
    device.data((x,ny))
    print(di)


# igračka 1
def running_border():
    for i in range(1,8):
        set_pixel(1,i)
        time.sleep(0.5)
    for i in range(1,8):
        set_pixel(i,8)
        time.sleep(0.5)
    for i in range(8,1,-1):
        set_pixel(8,i)
        time.sleep(0.5)
    for i in range(8,1,-1):
        set_pixel(i,1)
        time.sleep(0.5)


# igračka 2
def running_border1():
    for i in range(1,8):
        toggle_pixel(1,i)
        time.sleep(0.1)
    for i in range(1,8):
        toggle_pixel(i,8)
        time.sleep(0.1)
    for i in range(8,1,-1):
        toggle_pixel(8,i)
        time.sleep(0.1)
    for i in range(8,1,-1):
        toggle_pixel(i,1)
        time.sleep(0.1)

# endles : original Draw Random Dots
def draw_random_dots():
    while True:
        with canvas(device) as draw:
            for i in range(4):
                x = random.randint(0, device.width)
                y = random.randint(0, device.height)
                # 'draw' is an ImageDraw object.
                draw.point((x, y), fill="white")
                time.sleep(0.05)


def main():
    for i in range(8):
        running_border1()
    running_border1() 

if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        pass
