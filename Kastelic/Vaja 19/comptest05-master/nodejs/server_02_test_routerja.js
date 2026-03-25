var express = require('express');		                  // exprtess server

var fs = require("fs");                                   // dostop do dat. sistema : temps.json in certifikati so na FS
var key = fs.readFileSync('./server.key.pem');            // preberemo cert in ključ
var cert = fs.readFileSync('./server.certificate.pem')

var http = require('http');
var https = require('https');


var options = {                     // tole rabimo pri https serverju
    key: key,
    cert: cert
};


var app = express();

app.use( express.json() );                 // express json middleware  !!! - POST


//----------------------------------------------------
// zbindamo oba serverja z app (expressom)  : da lahko uporabimo isti endpoint za oba načina dostopa
//----------------------------------------------------
var httpServer = http.createServer(app).listen(8080);                //http
var httpsServer = https.createServer(options, app).listen(443);      //https


/*  --- dolga varianta s feedbackom na konzolo ob pravilnem zagonu ..... ------     
var httpServer = http.createServer(app).listen(8080, function () {
   var host = httpServer.address().address
   var port = httpServer.address().port
   console.log("HTTP server listening at http://%s:%s", host, port)
});

var httpsServer = https.createServer(options, app).listen(443, function () {
   var host = httpsServer.address().address
   var port = httpsServer.address().port
   console.log("HTTPS server listening at https://%s:%s", host, port)
});
*/




// -------------- dodajmo routerček :
let router = require('express').Router();    // nov router
app.use('/', router);                        // tule rečemo app-ju da naj ga uporabi s korena /



// Set default API response                  // za preskus delovanja routerja 
router.get('/', function (req, res) {
    res.json({
        status: 'API dela',
        message: 'Welcome to RESTHub crafted with love in Router-jem!',
    });
});




//---------------------------------------------------------------------------------------
//temperature --------------------------------------- end-point aka paths ---------------
//---------------------------------------------------------------------------------------


// vrne vse meritve
// test z brskalnikom in/ali
// curl -X GET "http://localhost:8080/api/show" -H  "accept: application/json"
app.get('/api/show', function (req, res) {
   fs.readFile( __dirname + "/" + "temps.json", 'utf8', function (err, data) {
      console.log( data );                                                       // v konzole
      res.json( data );                                                          // response v odjemalca
	  res.end();
   });
})


// vrne izbrano meritev (izbiramo z id meritve : glej, kako v get dobiš vrednost parametra
// test z brskalnikom in/ali
// curl -X GET "http://localhost:8080/api/show/1" -H  "accept: application/json"
app.get('/api/show/:id', function (req, res) {
  
   fs.readFile( __dirname + "/" + "temps.json", 'utf8', function (err, data) {
      var temperature = JSON.parse( data );
      var merit = temperature["meritev" + req.params.id] ;   // ID meritve je zapisan 2x : id atribut v meritve in del indexa celotne meritve
      console.log( merit );
      //res.json( JSON.stringify(merit,null,2));
	  res.json(merit);
	  res.end();
   });
   
})

//odstrani izbrano meritev
// test z brskalnikom na metodi delete http protokola ni možen, torej
// curl -X DELETE "http://localhost:8080/api/delete/1" -H  "accept: application/json"
app.delete('/api/delete/:id', function (req, res) {
	
 
     //standardni postopek za write after read (gnezdenje !) : zadeva se mora izvesti asinhrono;
	 // če bi sekvenčno izvedel 
	 //
	 //       fs.readFile(){}
	 //       fs.writeFile(){}
	 //
	 // bi se ti zgodilo, da bi se write začel, predenj bi se read končal !!!!
   
     fs.readFile( __dirname + "/" + "temps.json", 'utf8', function (err, data) {
            if (err) throw err;
            
			data = JSON.parse( data );                         // bereš celo datoteko in iz rezultativne kolekcije odstraniš izbranega
			delete data["meritev" + req.params.id];   
			
			//console.log(req); 
				
            fs.writeFile (__dirname + "/" + "temps.json", JSON.stringify(data,null,2), function(err) { // povoziš datoteko s celo kolekcijo
                if (err) throw err;
                console.log('save completed');
				
			console.log( data );
            res.end( JSON.stringify(data));		// ponavadi tega nočeš kot response pri delete, pa vseeno je za test ok	
				
            });
        });
   
     
   
})

// doda novo meritev,
// test (z brskalnikom ne gre, torej :)
// curl -X POST "http://localhost:8080/api/addMeritev" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"temp\" : \"133.3\",\"prit\" : \"3\",\"date\" : \"2017-12-22 10:03:07\",\"id\":44}"
// opomba:  za to rabiš express.json middleware
app.post('/api/addMeritev', function (req, res) {
   // First read existing users.
   fs.readFile( __dirname + "/" + "temps.json", 'utf8', function (err, data) {
         data = JSON.parse( data );
	     //console.log(req.body);
		 
		 var ss = {};
		 ss['meritev'+req.body['id']] = req.body;
		 
		data = Object.assign( {}, data, ss ); 

		// za pisanje naredi enako, kot pri delete : celo strukturo daš na datoteko:
		// pazi: vedno dodaš kot meritev, s tem da razširiš z ID
		
		console.log( data );
      res.end( JSON.stringify(data));
   });
})

// ažurira meritev na osnovi vrednosti polja id znotraj zapisa (pošlješ nov zapis z istim id-jem)
// test (z brskalnikom ne gre, torej :)
// curl -X PUT "http://localhost:8080/api/updateMeritev" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"temp\":\"888.3\",\"prit\":\"88\",\"date\":\"2017-11-22 08:03:07\",\"id\":1}"//
// opomba: za to rabiš express.json middleware
app.put('/api/updateMeritev', function (req, res) {
   // First read existing users.
   fs.readFile( __dirname + "/" + "temps.json", 'utf8', function (err, data) {
         data = JSON.parse( data );
	     console.log(req.body);
		 
		 delete data['meritev'+req.body['id']];
		 		 
		 var ss = {};
		 ss['meritev'+req.body['id']] = req.body;
		 
		data = Object.assign( {}, data, ss ); 
		// grrr. merging two js object into single one

		
		console.log( data );
      res.end( JSON.stringify(data));
   });
})
