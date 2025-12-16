# Uvod v datoteke
- interaktivno ustvarjanje besedilne datoteke
- programsko branje vsebine datoteke z znami sredstvi (Scanner)
- poskusi/načini naslavljanja vsebinskih elementov datoteke

Uporabite razred scanner za branje z datoteke. Princip je opisan v spodnjih treh vrsticah:

File file=new File("C:\\Users\\test.java");
Scanner sc=new Scanner(file);
//sc.useDelimiter("\\Z");


Datoteko test.java skreirajte z beležnico ali podobnim 'text editorjem', pazite, da bo uporabljeno kodiranje utf-8. 
V datoteko zapišite nekaj vrstic besedila, lahko tudi skopirate par odstavkov črtic S. Gruma npr. s https://lit.ijs.si/grum.html .

Privzeti delimiter bi moral biti presledek ....

Sicer pa je zanimiv tudi tale : [!?\.]+