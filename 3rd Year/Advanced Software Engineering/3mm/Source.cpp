#include "3mm.h"
#include <iostream>
#include <map>

int main()
{
	mm test;
	test.readTextFile("50.txt", false); //true = list; false = unordered_map
	test.sort("Ktfhizqv"); //pick starting point
	test.print();
	return 0;
}
