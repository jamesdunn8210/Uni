#include "3mm.h"

mm::mm()
{
}

mm::~mm()
{
}

void mm::readTextFile(std::string fileName, bool)
{
	std::ifstream myFile(fileName);

	std::string line;
	while (std::getline(myFile, line))
	{
		std::string first, second;

		first = line.substr(0, line.find(","));
		second = line.substr(line.find(",") + 1);

		if (useList)
		{
			men_list.push_back(std::make_pair(first, second));
		}
		else
		{
			men_unmap.insert(std::make_pair(first, second));
		}		
	}

}

void mm::print()
{
	std::ofstream myfile;
	myfile.open("output.txt");
	for (int i = 0; i < output.size(); i++)
	{
		myfile << i << ": " << output[i] << std::endl;
	}
	myfile.close();
}

void mm::sort(std::string startName)
{
	std::string base = startName;

	if (useList)
	{
		std::list<std::pair<std::string, std::string>>::const_iterator it_list = men_list.begin();
		while (it_list != men_list.end())
		{
			if ((*it_list).first == base)
			{
				output.push_back(base);
				base = (*it_list).second;
				//men_list.remove(*it_list);
				it_list = men_list.begin();
			}
			else
			{
				it_list++;
			}
			
		}

		output.push_back(base);

		base = output.front();

		it_list = men_list.begin();

		while (it_list != men_list.end())
		{
			if ((*it_list).second == base)
			{
				output.push_front((*it_list).first);
				base = (*it_list).first;
				//men_list.remove(*it_list);
				it_list = men_list.begin();
			}
			else
			{
				it_list++;
			}

		}
	}
	else
	{
		std::unordered_map<std::string, std::string>::const_iterator it_unmap = men_unmap.begin();

		while (it_unmap != men_unmap.end())
		{
			it_unmap = men_unmap.find(base);
			output.push_back(base);

			if (it_unmap != men_unmap.end())
			{
				base = (*it_unmap).second;
			}
		}

		output.push_back(base);
		base = output.front();
		it_unmap = men_unmap.begin();

		while (it_unmap != men_unmap.end())
		{
			if ((*it_unmap).second == base)
			{
				output.push_front((*it_unmap).first);
				base = (*it_unmap).first;

				it_unmap = men_unmap.begin();
			}
			else
			{
				it_unmap++;
			}
		}
	}
}

