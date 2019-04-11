//test harness for StaticArray class
#include "DynArray.h"
#include <iostream>

using namespace SDI;

int main(int argc, char* argv[])
{
	DynArray myarray;

	myarray.push_back(1);
	myarray.push_back(2);
	myarray.push_back(3);
	myarray.push_back(4); //add in before change
	myarray.push_back(5);

	myarray.print();

		
	std::cout << myarray.back();

	//myarray.addElement(9, 3);
	
	myarray.print(std::cout);



	std::cout << myarray.back();
	std::cout << myarray.capacity();


	return 0;
}
