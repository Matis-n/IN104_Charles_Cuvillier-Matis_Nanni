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
    def __init__(self,name,budget,nb_de_joueurs,palmaresLDC,capitaine):
        Equipe.__init__()
        self.palmaresLDC = palmaresLDC
        self.capitaine= capitaine
        
    def info(self,championnat,classement):
        return "l'equipe" + str(self.getName())+ "est" +str(classement) +"i-ème dans le championnat de" +championnat
    
    def but(self):
        print ("BUUUUUT")
        
class Basketball:
    def __init__(self,palmaresNBA, MVP):
        self.palmaresNBA = palmaresNBA
        self.MVP= MVP
        
    def MVP(self):
        return "le MVP de l'équipe est"+self.MVP
    
    def panier(self):
        print("switch")
