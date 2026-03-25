Readme nodejs

realizacija RESTful API

strežnik bazira na  express (nodejs web application framework)

v kodi:
1. demonstracija izvedbe metod http protokola:
  * get (parametra ni ali je indeks nečesa, kar naslavljate; prenaša se v glavi)   -**Read**
  * delete (parameter je index tistega, kar brišete, prenaša se v glavi)           -**Delete**
  * post (parameter je zapis, , velik podatek, prenos v telesu),                   -**Add**
  * put (parameter je zapis/strukturiran, velik podatek, prenos v telesu),         -**Update**
2. demonstracije uporabe orodja cURL pri testiranju postopkov

     curl -X metoda URL [ -H "accept: application/json" ] [-d "podatek" ]

3. implementirani endpoints / paths
* /api/show
* /api/addMeritev
* /api/updateMeritev
* /api/delete

(imena endpointov niso ravno konsistentna .....)


posebnosti realizacije:

realizacija namesto SQL uporablja zbirko zapisov oblike JSON (datoteko temps.json), če te
datoteke ni ali je prazna, lahko pričakujete probleme

uporabljene odvisnosti:
* mysql
* express


namestitev:
* npm install

zagon:
* node index_01.js   (oziroma varianta te datoteke)

osnovni test:
* glej konzolo
* http://"host_server_name":8080/api/get    aka   localhost:8080/api/get
