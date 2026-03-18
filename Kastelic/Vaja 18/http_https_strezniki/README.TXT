------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all they need to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE:
PURPOSE OF PROJECT:
VERSION or DATE:
HOW TO START THIS PROJECT:
AUTHORS:
USER INSTRUCTIONS:


gesla narejenih (za dostop do privatnih ključev v storih)  - vidni v skriptah za generiranje
 za jks : mojeGeslo
 za p12 : mojeGeslo12


certifikat za https strežnik:
  - uporabimo javanski keystore in vgrajeno orodje za izdelavo certifikata
keytool -genkeypair -keyalg RSA -alias selfsigned -keystore mojKeystore1.jks -storepass mojeGeslo -validity 360 -keysize 2048

info
  :: keytool -list -keystore mojKeystore1.jks ( še switch -v)


če rabiš pkcs12 :
keytool -importkeystore -srckeystore mojKeystore1.jks -destkeystore mojCert.p12 -srcstoretype JKS -deststoretype PKCS12
        
info:
keytool -list -v -keystore mojCert.p12 -storetype PKCS12


