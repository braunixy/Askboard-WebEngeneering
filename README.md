# Askboard-WebEngineering

ReadMe für WebEngineering-Projekt SS 2017 vom Team "qwert"

Teammitglieder:

  Fabian Moker (72628) - FrontEnd
  
  Michael Braunsperger (74977) - BackEnd

Um unser Projekt zu starten/testen gibt es zwei Möglichkeiten:

  1)
   Hier gibt es ebenfalls zwei Startmöglichkeiten 
   a) ohne vorgenerierte Fragen und User und b) mit vorgenerierte Fragen und User

  a)
  Laden Sie die "my-server.jar" Datei herunter. 
  Führen Sie die Datei "my-server.jar" aus und öffnen Sie einen Browser (Wir verwendeten bei der Erstellung des Projekts 
  Mozilla Firefox und Internet Explorer).
  Gehen Sie nach erfolgreichen Starten des Servers auf die URL:
  
      http://localhost:8080/
    
  Der Server ist nun testbereit.
  
  b)
  
  Laden Sie die "my-server.jar" Datei herunter. 
  Führen Sie die Datei "my-server.jar" mittels Cmd Leiste mit folgendem Befehl aus:
  
    (Beispielsweise)
    java -jar C:\User\my-server.jar --cc
    
  ODER
    
    Laden Sie die "my-server-content.jar" Datei herunter.
    Führen Sie die Datei "my-server-content.jar" aus.
  
  
  
  Öfnnen Sie einen Browser(Wir verwendeten bei der Erstellung des Projekts 
  ausschließlich Mozilla Firefox).
  Gehen Sie nach erfolgreichen Starten des Servers auf die URL:
  
      http://localhost:8080/
    
  Der Server ist nun testbereit.
  Folgende User mit dazugehörigen Passwörtern sind nun vorgeneriert:
  
  Username: Olaf123
  Password: olaf123
  
  Username: Sepp123
  Password: sepp123
  
  Username: Adolf123
  Password: adolf123
  
  Username: admin
  Password: admin
  
  2)d
  Laden Sie via Intellij oder Eclipse den Code herunter und führen Sie die Klasse "MyServerAskboardApplication.java" aus.
  Öfnnen Sie einen Browser(Wir verwendeten bei der Erstellung des Projekts 
  Mozilla Firefox und Internet Explorer).
  Gehen Sie nach erfolgreichen Starten des Servers auf die URL:
  
      http://localhost:8080/
    
  Der Server ist nun testbereit.
  
  Um vorgenerierten Content zu haben gehen Sie in die Klasse "Seeder.java" und setzen sie in Zeile 28 die Boolean Variable 
  "create" auf "true".
  Öffnen Sie einen Browser(Wir verwendeten bei der Erstellung des Projekts 
  Mozilla Firefox und Internet Explorer).
  Gehen Sie nach erfolgreichen Starten des Servers auf die URL:
  
      http://localhost:8080/
    
  Der Server ist nun testbereit.
