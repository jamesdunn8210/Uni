#pragma once
#include <string>
#include <list>
#include <vector>
#include <deque>
#include <fstream>
#include <sstream>
#include <iostream>
#include <iostream>
#include <algorithm>
#include <map>
#include <unordered_map>

class mm
{
public:
	mm();
	~mm();
	void readTextFile(std::string, bool useList);
	void print();
	void sort(std::string);

private:

	bool useList = true;

	std::list<std::pair<std::string, std::string>> men_list;

	std::map<std::string, std::string> men_map;

	std::unordered_map<std::string, std::string> men_unmap;

	std::deque <std::string> output;

	

};
