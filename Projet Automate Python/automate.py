# -*- coding: utf-8 -*-
from transition import *
from state import *
import os
import copy
from sp import *
from parser import *
from itertools import product
from automateBase import AutomateBase



class Automate(AutomateBase):

    def succElem(self, state, lettre):
        """State x str -> list[State]
        rend la liste des états accessibles à partir d'un état
        state par l'étiquette lettre
        """
        successeurs = []
        # t: Transitions
        for t in self.getListTransitionsFrom(state):
            if t.etiquette == lettre and t.stateDest not in successeurs:
                successeurs.append(t.stateDest)
        return successeurs


    def succ (self, listStates, lettre):
        """list[State] x str -> list[State]
        rend la liste des états accessibles à partir de la liste d'états
        listStates par l'étiquette lettre
        """
        successeurs = []
        #s: State
        for i in listStates:
            #t: Transition
            for t in self.getListTransitionsFrom(i):
                if t.etiquette == lettre and t.stateDest not in successeurs:
                    successeurs.append(t.stateDest)

        return successeurs




    """ Définition d'une fonction déterminant si un mot est accepté par un automate.
    Exemple :
            a=Automate.creationAutomate("monAutomate.txt")
            if Automate.accepte(a,"abc"):
                print "L'automate accepte le mot abc"
            else:
                print "L'automate n'accepte pas le mot abc"
    """
    @staticmethod
    def accepte(auto,mot) :
        """ Automate x str -> bool
        rend True si auto accepte mot, False sinon
        """
        #list_init: list[States]
        liste= auto.getListInitialStates()
        #c : str
        for c in mot:
            liste=auto.succ(liste,c)

        return State.isFinalIn(liste)


    @staticmethod
    def estComplet(auto,alphabet) :
        """ Automate x str -> bool
         rend True si auto est complet pour alphabet, False sinon
        """
        #state : State
        for state in auto.listStates:
            #lettre : str
            for lettre in alphabet:
                if auto.succElem(state,lettre)==[]:
                    return False
        return True



    @staticmethod
    def estDeterministe(auto) :
        """ Automate  -> bool
        rend True si auto est déterministe, False sinon
        """
        if (len(auto.getListInitialStates()) > 1):
            return False
        #alphabet : str
        alphabet = auto.getAlphabetFromTransitions()
        #state : State
        for state in auto.listStates:
            #lettre : str
            for lettre in alphabet:
                if len(auto.succElem(state,lettre))>1:
                    return False
        return True



    @staticmethod
    def completeAutomate(auto,alphabet) :
        """ Automate x str -> Automate
        rend l'automate complété d'auto, par rapport à alphabet
        """
        #newAuto : Automate
        newAuto = Automate(auto.listTransitions)
        if(Automate.estComplet(newAuto,alphabet)):
            return newAuto
        #trash : State
        trash = State(len(auto.listStates)+1,False,False,"poubelle")
        newAuto.addState(trash)
        #liste_state : list[State]
        liste_state = newAuto.listStates
        #state : State
        for state in liste_state:
            #lettre : str
            for lettre in alphabet:
                if newAuto.succElem(state,lettre)==[]:
                    newAuto.addTransition(Transition(state,lettre,trash))

        return newAuto



    @staticmethod
    def determinisation(auto) :
        """ Automate  -> Automate
        rend l'automate déterminisé d'auto
        """
        #newAuto : Automate
        newAuto = Automate(auto.listTransitions)
        if(Automate.estDeterministe(newAuto)):
            return newAuto
        #alphabet : str
        alphabet = newAuto.getAlphabetFromTransitions()
        #Liste : list(list[States])
        Liste=[newAuto.getListInitialStates()]    #Notre liste d'attente
        #Liste_f : list[States]
        Liste_f=newAuto.getListFinalStates()
        #Liste_temp : list[list[States]]
        Liste_temp=[]
        #Liste_Transition: list[Transition]
        Liste_Transition=[]
        #test : bool
        test=False
        for k in Liste[0]:
            if k in Liste_f:
                test = True
        #State_temp1 : States
        State_temp1=State(0, True, test, str(Liste[0]))
        #new_States : set(States)
        new_States =set()
        new_States.add(State_temp1)
        test=False
        #L2: list[list[States]]
        L2=[Liste[0]]
        #id : int
        id=1
        #s : str
        s=""
        while(Liste!=[]):
            s=str(Liste[0])
            for l in new_States:
                if s==l.label:
                    State_temp1=l
                    break

            for lettre in alphabet:
                Liste_temp=newAuto.succ(Liste[0],lettre)
                if Liste_temp in L2:
                    s=str(Liste_temp)
                    for l in new_States:
                        if s==l.label:
                            State_temp2=l
                            Liste_Transition.append(Transition(State_temp1,lettre,State_temp2))
                            break
                else:
                    if Liste_temp!=[]:
                        L2.append(Liste_temp)
                        #Test pour voir si l'état sera final
                        for k in Liste_temp:
                            if k in Liste_f:
                                test=True
                        s=str(Liste_temp)
                        State_temp2=State(id,False,test,s)
                        id+=1
                        test=False
                        Liste_Transition.append(Transition(State_temp1,lettre,State_temp2))
                        new_States.add(State_temp2)
                        Liste.append(Liste_temp)

            del Liste[0]

        return Automate(Liste_Transition)



    @staticmethod
    def complementaire(auto,alphabet):
        """ Automate -> Automate
        rend  l'automate acceptant pour langage le complémentaire du langage de auto
        """
        #newAuto : Automate
        newAuto = Automate(auto.listTransitions)
        newAuto = Automate.completeAutomate(newAuto,alphabet)
        newAuto = Automate.determinisation(newAuto)
        #Liste_f : list[States]
        Liste_f=newAuto.getListFinalStates()
        for i in newAuto.listStates:
             i.fin= not (i.fin)
        return newAuto


    @staticmethod
    def intersection (auto0, auto1):
        """ Automate x Automate -> Automate
        rend l'automate acceptant pour langage l'intersection des langages des deux automates
        """
        if(auto0.listTransitions==auto1.listTransitions):
            return Automate(auto0.listTransitions)
        ################### Creation des Variables utiles au programme ########
        #id : int
        id=0
        #test : boolean
        test=False
        #Liste_Transition: list[Transition]
        Liste_Transition=[]
        #Liste_parcourue ; list[tuple(States)]
        Liste_parcourue=[]
        #Liste_temp : list[States]
        Liste_temp=[]
        #Liste_temp0 : list[States]
        Liste_temp0=[]
        #Liste_temp1 : list[States]
        Liste_temp1=[]
        #State_temp1 : States
        State_temp1=State(0,True,True,"trash")
        #State_temp2 : States
        State_temp2=State(0,True,True,"trash1")

        ################# Création de l'alphabet #############################
        #alphabet0 : str
        alphabet0 = auto0.getAlphabetFromTransitions()
        #alphabet1 : str
        alphabet1 = auto1.getAlphabetFromTransitions()
        #alphabet : str
        alphabet= alphabet0
        for c in alphabet1:
            if c not in alphabet0:
                alphabet+=c
        ################# Création de la liste des States initiaux ###############
        #Liste : list[tuple(States)]
        Liste=[]                                                            #Notre liste d'attente
        #Liste0 : list[States]
        Liste0=auto0.getListInitialStates()
        #Liste1 : list[States]
        Liste1=auto1.getListInitialStates()
        for l0 in Liste0:
            for l1 in Liste1:
                 Liste.append((l0,l1))
                 Liste_parcourue.append((l0,l1))
        ################ Création de la liste des States des états finaux #########
        #Liste0_f : list[States]
        Liste0_f=auto0.getListFinalStates()
        #Liste1_f : list[States]
        Liste1_f=auto1.getListFinalStates()
        #Liste_f : list[States]
        Liste_f =Liste0_f
        for l1 in Liste1_f:
            if l1 not in Liste0_f:
                Liste_f.append(l1)
        ################ Création de l'ensemble des States du nouvel automate #########
        #new_States ; set(State)
        new_States=set()
        for l in Liste:
            if l[0] in Liste_f and l[1] in Liste_f:
                test=True
            new_States.add(State(id ,True ,test ,"("+ str (l[0])+ " ; "+ str (l[1])+ ")") )
            test=False
            id+=1
        ################ Parcours de la Liste d'attente #############################
        while(Liste!=[]):
            s="("+ str (Liste[0][0]) + " ; "+str (Liste[0][1])+")"
            for l in new_States:
                if s==l.label:
                    State_temp1=l
                    break

            for lettre in alphabet:
                Liste_temp0=auto0.succElem(Liste[0][0],lettre)
                Liste_temp1=auto1.succElem(Liste[0][1],lettre)
                for l0 in Liste_temp0:
                    for l1 in Liste_temp1:
                        if (l0,l1) in Liste_parcourue:
                            s= "("+str (l0) +" ; "+ str (l1)+")"
                            for l in new_States:
                                if s==l.label:
                                    State_temp2=l
                                    Liste_Transition.append(Transition(State_temp1,lettre,State_temp2))
                                    break

                        else:
                            if l0 in Liste_f and l1 in Liste_f:   #Test pour voir si l'état sera final
                                test=True
                            Liste_parcourue.append((l0,l1))
                            s= "("+str (l0) + " ; "+str (l1)+")"
                            State_temp2=State(id,False,test,s)
                            id+=1
                            test=False
                            Liste_Transition.append(Transition(State_temp1,lettre,State_temp2))
                            new_States.add(State_temp2)
                            Liste.append((l0,l1))

            del Liste[0]


        return Automate(Liste_Transition)


    @staticmethod
    def union (auto0, auto1):
        """ Automate x Automate -> Automate
        rend l'automate acceptant pour langage l'union des langages des deux automates
        """
        #newAuto0 : Automate
        newAuto0 = copy.deepcopy(auto0)
        #newAuto1 : Automate
        newAuto1 = copy.deepcopy(auto1)

        newAuto0.prefixStates(0)
        newAuto1.prefixStates(1)


        #Liste_init : list[States]
        Liste_init = newAuto0.getListInitialStates() + newAuto1.getListInitialStates()

        i = len ( newAuto0.listStates + newAuto1.listStates ) + 1
        #newState: State
        newState = State(i, True, False)

        newTransi = []

        Liste_Transition = newAuto0.listTransitions + newAuto1.listTransitions
        for l0 in Liste_init:
            if l0.fin:
                newState.fin = True
            l0.init= False

        for transition in Liste_Transition:
            newTransi.append(transition)
            if transition.stateSrc in Liste_init:
                newTransi.append(Transition(newState, transition.etiquette, transition.stateDest))

        return Automate(newTransi)





    @staticmethod
    def concatenation (auto1, auto2):
        """ Automate x Automate -> Automate
        rend l'automate acceptant pour langage la concaténation des langages des deux automates
        """
        #newAuto1 : Automate
        newAuto1 = copy.deepcopy(auto1)
        #newAuto2 : Automate
        newAuto2 = copy.deepcopy(auto2)
        #Liste0 : list[States]
        Liste_f = newAuto1.getListFinalStates()
        #Liste1 : list[States]
        Liste_i = newAuto2.getListInitialStates()
        #Liste0_Transition: list[Transition]
        Liste1_Transition=newAuto1.listTransitions
        #Liste1_Transition: list[Transition]
        Liste2_Transition=newAuto2.listTransitions
        #Liste_Transition : List[Transition]
        Liste_Transition=Liste1_Transition

        #test : boolean
        test=False
        for k in newAuto1.listStates:
            k.insertPrefix(1)
            if k in Liste_f:
                k.fin=False
        newAuto2.prefixStates(2)

        for f in Liste_f:
            for L in Liste2_Transition:
                if L.stateSrc in Liste_i:
                    L.stateSrc = f
                if L.stateDest in Liste_i:
                    L.stateDest = f
                if L in Liste_Transition:
                    continue
                Liste_Transition.append(L)

        return Automate(Liste_Transition)

    @staticmethod
    def standard(auto):
        """ Automate  -> Automate
        rend l'automate acceptant pour langage le standard du langage de a
        """
        #newAuto : Automate
        newAuto = copy.deepcopy(auto)
        #Liste_Transition : List[Transition]
        Liste_Transition=[]
        set_States=set()
        #State_temp1 : States
        State_temp1 = State(0,True,True,"trash")
        #State_temp2 : States
        State_temp2=State(0,True,True,"trash1")
        for s in newAuto.listStates:
            set_States.add(State(s.id,False,s.fin))
        for t in newAuto.listTransitions:
            for s1 in set_States:
                if s1==t.stateSrc:
                    State_temp1=s1
                    break
            for s1 in set_States:
                if s1==t.stateDest:
                    State_temp2=s1
                    break
            Liste_Transition.append(Transition(State_temp1, t.etiquette,State_temp2))
        i=State(len(newAuto.listStates)+1,True,False)
        if State.isFinalIn(newAuto.getListInitialStates()):
            i.fin=True
        for s in newAuto.getListInitialStates():
            for t in newAuto.listTransitions:
                if Transition(s,t.etiquette,t.stateDest) in Liste_Transition:
                    Liste_Transition.append(Transition(i,t.etiquette,t.stateDest))
                if Transition(i,t.etiquette,s) not in Liste_Transition:
                    Liste_Transition.append(Transition(i,t.etiquette,s))
        return Automate(Liste_Transition)

    @staticmethod
    def etoile (auto):
        """ Automate  -> Automate
        rend l'automate acceptant pour langage l'étoile du langage de a
        """
        #newAuto : Automate
        newAuto = Automate.standard(auto)
        #listeFin : list[State]
        listeFin = newAuto.getListFinalStates()
        #listeTransi : list[Transition]
        listeTransi = newAuto.listTransitions
        #listeTransition : list[Transition]
        listeTransition =  listeTransi
        #listeInit :  list[State]
        listeInit = newAuto.getListInitialStates()
        for T in listeTransi:
            if T.stateSrc in listeInit:
                for final in listeFin:
                    listeTransition.append(Transition(final,T.etiquette,T.stateDest))

        listeInit[0].fin = True
        return Automate(listeTransition)
