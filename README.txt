Niniejszy projekt został wykonany przez:
Kinaga Kalisz
Marek Pałdyna 

W folderze "Maszyna Turinga" znajduje się kod programu i przykładowe pliki wejściowe
Plik MaszynaTurniga.jar jest skompilowaną wersją projketu i powinno się ją uruchomić używając terminala systemowego, za pomocą:
java -jar MaszynaTuringa.jar sciezka/pliku/wejsciowego.txt

"sciezka/pliku/wejsciowego.txt" musi być scieżką pliku wejściowego.

Oznaczenia na wykresach:

Okno z grafem przedstawiajacym maszynę turinga: 
Kolor zielony - stan bierzący
Kolor czerwony - stan akceptujący
Kolor pomarańczowy - pojawia się w momencie kiedy stan bierzący jest jednocześnie stanem wejsciowym

Okno z grafem przedstawiającym Taśmę:
Kolor zielony - znak na którym znajduje się głowica
Kolor czerwony - znak który był na wskazanym miejscu przed ostatnim obliczeniem wykonanym przez maszynę.
linie łączące znaki - pomagają określić kolejny i poprzedni znak na taśmie


Tryby symulacji maszyny:
Zwykły - tryb w którym kolejne kroki są wykonywane samoczynnie z opóźnieniem 200ms pomiędzy kolejnymi krokami
Przyspieszony - tryb w którym kolejne kroki są wykonywane samoczynnie z opóźnieniem 25ms pomiędzy kolejnymi krokami
Krokowy- tryb w którym maszyna przed wykonaniem kolejnego obliczenia czeka na wciśnięcie klawisza "ENTER" na klawiaturze.
UltraPrzyspieszonym - tryb w którym kolejne kroki są wykonywane zamoczynnie bez opóźnienia pomiędzy nimi, więc wynik, najprawdopodobniej pokaże się od razu na grafach.

Dodatkowo w przypadku symulacji w trybie zwykłym i przyspieszonym po wykonaniu 1000 obliczeń maszyna przyspieszy i opóźnienie pomiędzy krokami zostanie zredukowane do 1ms.
