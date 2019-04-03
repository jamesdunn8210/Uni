#include "Dictionary.h"
#include <iostream>

using namespace Containers;
Dictionary d;

int main()
{
	d.insert("1", "Jam1es ");
	d.insert("4", "Jam4es ");
	d.insert("5", "Jam5es ");
	d.insert("7", "Jam7es ");
	d.insert("2", "Jam2es ");

	std::cout<< *d.lookup("5");

	d.remove("5");

	std::cout << *d.lookup("5");


}