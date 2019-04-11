#ifndef SDI_DYNAMIC_ARRAY_CPP
#define SDI_DYNAMIC_ARRAY_CPP

#include "DynArray.h"
#include <iostream>

namespace SDI
{
	//CONSTRUCTORS 

	DynArray::DynArray()
	{

		buffer_ = new ComponentType[5]; //Set default space for 5 elements
		cap_ = sz_ + 3;
	}

	DynArray::DynArray(const DynArray & tbc)
	{
		buffer_ = new ComponentType[tbc.sz_];
		sz_ = tbc.sz_;
		for (unsigned int i = 0; i < sz_; ++i)
		{
			buffer_[i] = tbc.buffer_[i];
		}
	}

	DynArray::~DynArray()
	{
		delete[] buffer_;
	}
	
	DynArray::DynArray(int sz)
	{
		buffer_ = new ComponentType[sz];
		cap_ = sz + 3;
	}

	//RETURN FUNCTIONS

	int DynArray::size() const
	{
		return sz_;
	}

	int DynArray::capacity() const
	{
		return cap_;
	}
	
	bool DynArray::isEmpty()
	{
		if (sz_ > 0)
		{
			return false;
		}
		else
		{
			return true;	
		}
	}

	void DynArray::push_back(ComponentType nbr)
	{
		if (sz_ == cap_)
		{
			resize();
		}
		
		if (sz_ < cap_) // No resizing needed
		{
			buffer_[sz_] = nbr;
			sz_ += 1;
		}
	}

	void DynArray::pop_back()
	{
		buffer_[sz_].~ComponentType();// = *new ComponentType;
		sz_ -= 1;
	}

	ComponentType DynArray::back()
	{
		return buffer_[sz_];
	}

	ComponentType DynArray::front()
	{
		return buffer_[0];
	}
	
	ComponentType DynArray::get(int index)
	{
		if (index <= sz_)
		{
			return buffer_[index];
		}
	}
	
	void DynArray::set(ComponentType index, int value)
	{
		if (index <= sz_)
		{
			buffer_[index] = value;
		}
	}
	
	void DynArray::zap()
	{
		for (int i = 0; i <= sz_; i++)
		{
			buffer_[i] = *new ComponentType[sz_];
		}
		sz_ = 0;
	}

	void DynArray::print(ostream & out)
	{
		for (int i = 0; i < sz_; i++)
		{
			out<< buffer_[i]<<", ";
		}
	}

	void DynArray::removeElement(int index)
	{
		buffer_[index] = *new ComponentType;

		for (int i = index; i< sz_; i++)
		{
			buffer_[i] = buffer_[i + 1];
		}
		sz_ -= 1;
	}

	void DynArray::addElement(ComponentType value, int index)
	{
		if (sz_ >= cap_)
		{
			resize();
		}

		for (int i = sz_; i > index; i--)
		{
			buffer_[i] = buffer_[i - 1];
		}

		sz_ += 1;
		buffer_[index] = value;
	}

	void DynArray::resize()
	{
		cap_ = sz_ + 3;

		ComponentType* newArray = new ComponentType[cap_]; //Create new empty array - bigger capacity

		for (unsigned int i = 0; i <= sz_; ++i) //Copy into new array
		{
			newArray[i] = buffer_[i];
		}

		delete[] buffer_;
		buffer_ = newArray; //point to start of new array

	}
}
#endif