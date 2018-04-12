3. Perquè serveixen les anotacions següents: @Required, @Ignore, @Index, @PrimaryKey

@Required
L'anotació @Required es pot utilitzar per dir al Realm que no permeti valors nuls (null) en un camp, fent-lo necessari en comptes d'opcional.

@Ignore
Es pot utilitzar aquesta anotació si no es vol desar un camp del model al Realm.

@Index
Per indexar un camp, s'ha d'utiltizar l'anotació @Index. Igual que les claus primàries, això fa que l'escriptura sigui lleugerament més lenta, però fa que les lectures siguin més ràpides. (També fa que el nostre arxiu del Realm sigui lleugerament més gran, per emmagatzemar l'índex). És millor que només s'utilitzin índexs quan es vol optimitzar el rendiment de lectura per a situacions específiques.

@PrimaryKey
Per a marcar un camp com a clau primària del model, s'utilitza l'anotació @PrimaryKey. El tipus de camp ha de ser una cadena (String) o un enter (byte, short, int, long, Byte, Short, Integer i Long).


4. Anota una @PrimaryKey a Persona i explica quines avantatges o desavantatges té fer aquesta operació. Es poden utilitzar claus compostes?

Quan s'utilitzen claus principals, les lectures (consultes) seran lleugerament més ràpides, però escriure (crear i actualitzar objectes) serà una mica més lent. Els canvis en el rendiment dependran de la mida del conjunt de dades del nostre Realm.

L'ús d'un camp de String com a clau principal indexa automàticament el camp: l'anotació @PrimaryKey en un String implícitament estableix l'anotació @Index. El Realm no admet claus compostes, és a dir, utilitzant diversos camps com una única clau primària.