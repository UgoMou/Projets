# -*- coding: utf-8 -*-
"""
Code modifiable.
Vous avez dans le dossier test des fichiers
qui permettent de tester vos fonctions.
Pour cela il suffit de décommenter le code
de la fonction que vous souhaiter tester
"""

from automate import Automate
from state import State
from transition import Transition
from parser import *
#from essai import Essai

#import projet
s0=State(0,True,False)
s1=State(1,False,False)
s2=State(2,False,True)
t1=Transition(s0,'a',s0)
t2=Transition(s0,'b',s1)
t3=Transition(s1,'a',s2)
t4=Transition(s1,'b',s2)
t5=Transition(s2,'a',s0)
t6=Transition(s2,'b',s1)
L=[ t1,t2,t3,t4,t5,t6]
#auto=Automate(L)


print(" -auto est l'automate suivant : ")
autoB = Automate.creationAutomate("tripleA.txt")
autoA = Automate.creationAutomate("auto.txt")
#autoA.show('autoA')
#autoB.show('autoB')

#print(autoA)
#print(autoB)

"""Cet automate est complet et déterministe
On test maintenant la fonction succ avec a,b et c (sachant qu'auto2 ne contient pas de transition c
"""



print "\n TEST SUCC \n"

print('# test succ pour autoA.succ(auto2.listStates,"a") : ')
print(autoA.succ(autoA.listStates,"a"))

print('# test succ pour autoA.succ(auto2.listStates,"b") : ')
print(autoA.succ(autoA.listStates,"b"))

print('# test succ pour autoA.succ(auto2.listStates,"c") : ')
print(autoA.succ(autoA.listStates,"c"))



print "\n TEST 'accepte' \n"
print (
"Le mot ababaaaabababababa est il accepté par l'automate A ? => " + str(Automate.accepte(autoA,"ababaaaabababababa")) +
"\nLe mot aab est il accepté par l'automate A ? => " + str (Automate.accepte (autoA,"aba"))
)



print "\n TEST 'estComplet' \n"
res = Automate.estComplet(autoA,autoA.getAlphabetFromTransitions())
if res == True :
	print "L'automate est complet."
else :
	print "L'automate n'est pas complet."



print ("\n APRES UTILISATION DE completeAutomate\n")
complete = Automate.completeAutomate(autoA,autoA.getAlphabetFromTransitions())
res = Automate.estComplet(complete,complete.getAlphabetFromTransitions())
if res == True :
	print "L'automate est complet."
else :
	print "L'automate n'est pas complet."
#complete.show("autocomplete")




print "\n TEST 'estDeterministe' \n"
res = Automate.estDeterministe(autoA)
if res == True :
	print "L'automate est déterministe."
else :
	print "L'automate n'est pas déterministe."



print "\n TEST 'determinisation' \n"
deter = Automate.determinisation(autoA)



print ("\n APRES UTILISATION DE 'determinisation'\n")
res = Automate.estDeterministe(deter)
if res == True :
	print "L'automate est déterministe."
else :
	print "L'automate n'est pas déterministe."

#deter.show('determinisation')



print "\n TEST 'complementaire' \n"
complement = Automate.complementaire(autoA,autoA.getAlphabetFromTransitions())
print (
"Le mot ababaaaabababababa est il accepté par :" +
"\n\t-l'automate A ? => " + str (Automate.accepte(autoA,"")) +
"\n\t-le complémentaire A ? => " + str(Automate.accepte(complement,"")) +
"\nLe mot aab est il accepté par :" +
"\n\t-l'automate A ? => " + str (Automate.accepte (autoA,"aba")) +
"\n\t-le complémentaire A ? => " + str(Automate.accepte (complement,"aba"))
)
complement.show("autocomplementaire")


print "\n TEST 'intersection' \n"
inter = Automate.intersection(autoA, autoB)
print (
"Le mot aaaba est il accepté par :" +
"\n\t-l'automate A ? => " + str(Automate.accepte(autoA,"aaab")) +
"\n\t-l'automate B ? => " + str(Automate.accepte(autoB,"aaaba")) +
"\n\t-l'intersection AnB ? => " + str(Automate.accepte(inter, "aaaba")) +
"\nLe mot aaa est il accepté par :" +
"\n\t-l'automate A ? => " + str(Automate.accepte(autoA,"aaa")) +
"\n\t-l'automate B ? => " + str(Automate.accepte(autoB,"aaa")) +
"\n\t-l'intersection AnB ? => " + str(Automate.accepte(inter,"aaa"))
)
#inter.show("intersection")



print "\n TEST 'union' \n"
uni = Automate.union(autoA, autoB)
print (
"Le mot aaab est il accepté par :" +
"\n\t-l'automate A ? => " + str(Automate.accepte(autoA,"aaab")) +
"\n\t-l'automate B ? => " + str(Automate.accepte(autoB,"aaab")) +
"\n\t-l'union AuB ? => " + str(Automate.accepte(uni,"aaab")) +
"\nLe mot abb est il accepté par :" +
"\n\t-l'automate A ? => " + str(Automate.accepte(autoA,"abb")) +
"\n\t-l'automate B ? => " + str(Automate.accepte(autoB,"abb")) +
"\n\t-l'union AuB ? => " + str(Automate.accepte(uni,"abb"))
)
#uni.show("union")



print "\n TEST 'concatenation' \n"
conca = Automate.concatenation(autoA, autoB)
print (
"Le mot aaab est il accepté par l'automate A ? => " + str(Automate.accepte(autoA, "aaab")) +
"\nLe mot abb est il accepté par l'automate B ? => " + str(Automate.accepte(autoB, "abb")) + "\nLe mots aaab.abb est il accepté par la concatenation A.B ? => " + str(Automate.accepte(conca, "aaababb"))
)
print (autoB)
#conca.show("concatenation")


print "\n TEST 'etoile' \n"
etoi = Automate.etoile(autoA)
print(
"Le mot vide ε est il accepté par :" +
"\n\t-l'automate A ? => " + str(Automate.accepte(autoA,"")) +
"\n\t-l'etoile A ? => " + str(Automate.accepte(etoi,"")) +
"\nLe mot aaab.aaab est il accepté par :" +
"\n\t-l'automate A ? => " + str(Automate.accepte(autoA,"aaabaaab")) +
"\n\t-l'etoile A ? => " + str(Automate.accepte(etoi,"aaabaaab"))
)
#etoi.show("etoile")


"""
print(auto)
#auto.show("A_ListeTrans")

auto1 = Automate(L,[s0,s1,s2])
print(auto1)
#auto1.show("A_ListeTrans1")

auto2 = Automate.creationAutomate("auto.txt")

auto.removeTransition(t1)
t = Transition(s0,'y',s2)
auto.addTransition(t)
#auto.removeState(s1)
#auto.addState(s1)
s3 = State(0,True,False)
auto.addState(s3)
auto.removeState(s0)
#auto.show("A_ListeTrans")
auto.getListTransitionsFrom(s1)
print(auto.getListTransitionsFrom(s1))

print(auto1.succ([s1,s2],'a'))

print "DEBUT PROGRAMME\n"


s = State(1, False, False)
s2=State(1, True, False)
t = Transition(s,"a",s)
t2=Transition(s,"a",s2)
s.insertPrefix(2)
a= Automate([t,t2])
a.prefixStates(3)
a.show("justep")

"""
'''
print "etat s " + s.id
print "s "+ str(s)
print "t "+ str(t)
print "a "+ str(a)
'''
"""
print "s=s2? "+ str(s==s2)
print "t=t2? "+ str(t==t2)

s1=State(1, True, False)
s2=State(2, False, True)
t1= Transition(s1,"a",s1)
t2=Transition(s1,"a",s2)
t3=Transition(s1,"b",s2)
t4=Transition(s2,"a", s2)
t5=Transition(s2,"b",s2)
liste = [t1,t2,t3,t4,t5]
a=Automate(listStates=[], label="a", listTransitions=liste)

print "a : "
print a
print a.listStates
#print a.getListStates()
#print a.getSetStates()
print a.getListInitialStates()
print a.getListFinalStates()
print a.getListTransitionsFrom(s1)
#a.show("nouvela")
a.prefixStates(0)
a.show("prefixe")



a.removeTransition(t5)
print a
a.removeTransition(t5)
print a

a.addTransition(t5)
print a

a.addTransition(Transition(s2,"c", s1))
print a

a.addTransition(Transition(s2,"c",s1))
print a
#t = Transition("a", )


#a.show("essai")
print a.succ(s1,"a")
print a.succ(s2,"c")
list = [s1,s2]
print a.succ(list,"c")
print a.succ(list,"b")
print "etats accessibles"
print a.acc()

print Automate.accepte(a, "abc")
print Automate.accepte(a, "aaabbcb")
print Automate.accepte(a, "abs")


print Automate.estComplet(a,["a","b","c"])
print Automate.estComplet(a, ["0","1"])
print Automate.estComplet(a, ["a","b"])

print "Deterministe"
print Automate.estDeterministe(a)

#b=Automate.completeAuto(a,["a","b","c"])
#a.show("automate_a")
#b.show("b=a_aprescompletion")
#print b

print b.getAlphabetFromTransitions()
print "etats accessibles"
print b.acc()
print Automate.accepteVide(b)
#b.show("automate_b")

print "determinisation"
c = Automate.determinisation(b)
#c.show("Determinisationb")

s3 = State("3",True,False)
c=Automate([])
c.addTransition(Transition(s3,"a",s3))
c.addTransition(Transition(s3,"b",s3))
s4 = State("4",False,True)
c.addTransition(Transition(s3,"c",s4))
c.show("automate_c")

d = Automate.unionND(b,c)
d.show("unionND_b_c")
#b.show("b_apres")



my_parser=Parser.Auto()
result = my_parser("#E: 4 1 5 #I: 1 2 #F: 3 4 #T: (1 2 2)")
#result = my_parser("#E:ab c #I:z r #F:a ab #T: (1 a 2)")
#result = my_parser("E:42 12 3")
#result = my_parser("E: Q1 Q2 Q3 -I: Q1 Q2")
print "result ",result

determinisation
fichier = open("../../test/testDeter.txt")
s = fichier.read()
print s
result = my_parser(s)
print result
fichier.close()

automate = Automate.creationAutomate("../../test/testDeter.txt")
print "AUTOMATE CREATION"
print automate
automate.prefixStates(0)
print "PREFIXE"
print automate
automate.show("parser")



print "\n TEST ACCEPTE_MOT \n"
a = Automate.initAutomate("test/testDeter.txt")
res = projet.accepteMot(a,"aaa")
print "Le mot aaa est il accepté par l'automate? => " + str (res)
res = projet.accepteMot (a,"aab")
print "Le mot aab est il accepté par l'automate? => " + str (res) + "\n"



print "\n TEST EST_COMPLET \n"
res = projet.estComplet(a)
if res == True :
	print "L'automate est complet."
else :
	print "L'automate n'est pas complet."



print "\n TEST EST_DETERMINISTE \n"
res = projet.estDeterministe(a)
if res == True :
	print "L'automate est déterministe."
else :
	print "L'automate n'est pas déterministe."



print "\n TEST DETERMINISATION \n"
a = Automate.initAutomate("test/testDeter.txt")
b = projet.determinisation(a)

a.affiche ("aavDeter")
b.affiche("apDeter")
"""

"""
print "\n TEST INTERSECTION \n"
c = Automate.initAutomate("test/testInterbis1.txt")
d = Automate.initAutomate("test/testInter2.txt")
e = projet.intersection(c,d)

c.affiche("aavInter1")
d.affiche("aavInter2")
e.affiche("apInter")



print "\n TEST UNION \n"
f = Automate.initAutomate("test/testUnion12.txt")
g = Automate.initAutomate("test/testUnion22.txt")
h = projet.union(f,g)
print "\n TEST COMPLEMENTAIRE \n"
i = Automate.initAutomate("test/testCompl.txt")
j = projet.complementaire(i)
print "\n TEST COMPLEMENTAIRE \n"
i = Automate.initAutomate("test/testCompl.txt")
j = projet.complementaire(i)

i.affiche("aavCompl")
j.affiche("apCompl")

i.affiche("aavCompl")
j.affiche("apCompl")

f.affiche("aavUnion1")
g.affiche("aavUnion2")
h.affiche("apUnion")



print "\n TEST COMPLEMENTAIRE \n"
i = Automate.initAutomate("test/testCompl.txt")
j = projet.complementaire(i)

i.affiche("aavCompl")
j.affiche("apCompl")



print "\n TEST PRODUIT \n"

k = Automate.initAutomate("test/testProduit11.txt")
l = Automate.initAutomate("test/testProduit21.txt")
m = projet.produit(k,l)

k.affiche("aavProduit11")
l.affiche("aavProduit21")
m.affiche("apProduit1")

p = Automate.initAutomate("test/testProduit1.txt")
q = Automate.initAutomate("test/testProduit2bis.txt")
r = projet.produit(p,q)

p.affiche("aavProduit1")
q.affiche("aavProduit2")
r.affiche("apProduit")



print "\n TEST SONT_EQUIVALENTS \n"
res = projet.sontEquivalents(k,m)
print "L'automate k et l'automate produit sont équivalents? => " + str(res)

res2 = projet.sontEquivalents(k,k)
print "L'automate k et l'automate k sont équivalents? => " + str(res2)



print "\n TEST ETOILE \n"
n = Automate.initAutomate("test/testEtoile.txt")
o = projet.etoile(n)

n.affiche("aavEtoile")
o.affiche("apEtoile")
"""

print "\nFIN PROGRAMME\n"
