import unittest
from class_charles_cuvillier import *


bayern.but()

golden_state_warriors=Basketball("Golden State",100000500,6,12,"Curry")

class TestSum(unittest.TestCase):
	def test_ajout_budget(self):

		bayern=Football("Bayern de Munich",5500000,22,[1985,2002,2014],"Neuer")

		bayern.ajout_budget(1000000)
		self.assertEqual(bayern.budget,6500000)

	def test_transfert(self):
		bayern=Football("Bayern de Munich",5500000,22,[1985,2002,2014],"Neuer")
		bayern.transfert("Messi",80)
		self.assertEqual(bayern.budget,5500000-80)


if __name__ == '__main__':
    unittest.main()