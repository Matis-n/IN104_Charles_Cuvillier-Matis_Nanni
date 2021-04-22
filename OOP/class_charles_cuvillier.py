# -*- coding: utf-8 -*-
"""
Created on Tue Apr  6 17:19:29 2021

@author: charl
"""

class Equipe:
    def __init__(self,name, budget, nb_de_joueurs):
        self.name = name
        self.budget = budget
        self.nb_de_joueurs = nb_de_joueurs
     
    def __budgetPrive(self):
        print(self.budget)
    
    def getName(self):
        return self.name
       
    
class Football(Equipe):
    def __init__(self,name, budget, nb_de_joueurs,palmaresLDC, capitaine):
        self.name = name
        self.budget = budget
        self.nb_de_joueurs = nb_de_joueurs
        self.palmaresLDC = palmaresLDC
        self.capitaine= capitaine
        
    def info(self,championnat,classement):
        return "l'equipe" + str(self.getName())+ "est" +str(classement) +"i-ème dans le championnat de" +championnat
    
    def but(self):
        print ("BUUUUUT")
    
    def ajout_budget(self,montant):
        self.budget += montant
        print("l'equipe a maintenant un budget de "+str(self.budget) )
        
    def transfert(self, nom_du_joueur, prix):
        self.budget -= prix
        self.nb_de_joueurs +=1
        print("recrutement de"+ nom_du_joueur +"pour" +str(prix))
        
    def victoire_en_LDC(self,année):
        self.palmaresLDC.append(année)
        
        
class Basketball(Equipe):
    def __init__(self,name, budget, nb_de_joueurs,palmaresNBA, MVP):
        self.name = name
        self.budget = budget
        self.nb_de_joueurs = nb_de_joueurs
        self.palmaresNBA = palmaresNBA
        self.MVP= MVP
        
    def MVP(self):
        return "le MVP de l'équipe est"+self.MVP
    
    def panier(self):
        print("switch")
        
    def MVP(self):
        return "le MVP de l'équipe est"+self.MVP
    
    def panier(self):
        print("switch")
