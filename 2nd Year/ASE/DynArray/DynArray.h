#ifndef SDI_DYNAMIC_ARRAY_H
#define SDI_DYNAMIC_ARRAY_H

typedef int ComponentType;
#include <ostream>
namespace SDI
{
	class DynArray
	{
	public:
	
		DynArray();						
		DynArray(const DynArray &);
		~DynArray();					
		DynArray(int);					
		int size() const;					
		int capacity() const;			
		bool isEmpty();					
		void push_back(ComponentType);  
		void pop_back();
		ComponentType back();			
		ComponentType front();			
		ComponentType get(int);			
		void set(ComponentType, int);	
		void zap();						

		void print(std::ostream &);

		void removeElement(int);				 
		void addElement(ComponentType, int);	

	private:
		void resize();


	private:
		ComponentType * buffer_; 
		unsigned int sz_; //nbr of elements in array
		unsigned int cap_; //max number available 
		

	};
}
#endif