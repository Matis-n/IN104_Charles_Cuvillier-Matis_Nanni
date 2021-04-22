#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Apr  6 17:26:37 2021
@author: matis
"""

class Equipe:
    def __init__(self,name,players,budget_prive):
        self.name=name
        self,players
        self.budget_prive=budget_prive
        
    def getName(self):
        print("C'est l'équipe "+self.name)



class Football(Equipe):
    def __init__(self,name,players,budget_prive,palmares_championsleague,capitaine):
        self.name=name
        self,players
        self.budgetPrivee=budgetPrivee
        self.palmares_championsleague=palmares_championsleague
        self.capitaine=capitaine
        
    def __getBudget(self):
        print("Le budget est de"+self.budget_prive )
    
    def info(self):
        print("L'équipe"+ str(self.getName())+"a gagné"+self.palmares_championsleague +"fois à la Champions League")

    def refusSuper(self):
        return str(self.getName()) + "refuse de rejoindre la Super League"
        
    def transfert(self,montant,player1,player2):
        for player in self.players :
            if player == player1 :
                player=player2
        self.budget_prive-=montant        

    
class Basketball(Equipe):
    def __init__(self,palmares_NBA,mvp):
        self.palmares_NBA=palmares_NBA
        self.mvp=mvp
    
    def __budget(self,budget_prive):
        print("Le budget est de "+self.budget_prive )
        
        
    def info(self):
        print("L'équipe"+ str(self.getName())+"a gagné"+self.palmares_championsleague +"fois à la Champion's League")


    def thrashtalk(self,players):
        n=random.randint(0, 10)
        print(self.players[n] + "thrashtalk l'équipe adverse")
