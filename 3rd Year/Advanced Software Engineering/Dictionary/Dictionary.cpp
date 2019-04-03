#include "Dictionary.h"
using namespace Containers;

struct Dictionary::Node
{
	Key key;
	Item item;

	Node* next;
	
	Node(Key k, Item i)
	{
		key = k;
		item = i;

		next = nullptr;
	}
	~Node()
	{
		delete next;
	}
};

Dictionary::Dictionary(const Dictionary & original)
{
	this->root = deepCopy(original.root);
}

Dictionary & Dictionary::operator=(const Dictionary &original)
{
	if (treeIsEqual(this->root, original.root))
	{
		return *this;
	}
	deepDelete(this->root);
	this->root = deepCopy(original.root);

	return *this;
}

Containers::Dictionary::~Dictionary()
{
}

void Dictionary::deepDelete(Node * n) //check if delete corrcet
{
	if (n)
	{
		delete n;
		n = nullptr;
	}

}

Dictionary::Node* Dictionary::deepCopy(Node* original)
{
	Node* copyNode = nullptr;

	if (original)
	{
		copyNode = new Node(original->key, original->item);
		copyNode->next = deepCopy(original->next);
	}
	return copyNode;
}

Dictionary::Item * Containers::Dictionary::lookup(Key k)
{
	return(lookupRec(k, root));
}

Dictionary::Item * Dictionary::lookupRec(Key k, Node *n)
{
	if (k == n->key)
	{
		return &n->item;
	}
	else if (k > n->key)
	{
		return lookupRec(k, n->next);
	}
	else if (n == nullptr)
	{
		return(false);
	}
}

void Dictionary::insert(Key k, Item i)
{
	insertRec(k, i, root);
}

void Dictionary::insertRec(Key k, Item i, Node*& n)
{
	if (n == NULL)
	{
		n = new Node(k, i);
	}
	else if (k > n->key) 
	{
		insertRec(k, i, n->next);
	}
	else if (k == n->key)
	{
		n->item = i;
	}
}

void Dictionary::remove(Key k)
{
	Node* parent = nullptr;
	removeRec(k, root, parent);
}

void Dictionary::removeRec(Key k, Node*& n, Node* parent)
{
	//find key
	if (k > n->key)
	{
		parent = n;
		removeRec(k, n->next, parent);
	}
	//delete if no next
	if (n->next == nullptr)
	{
		n = nullptr;
		delete n;
	}
	//has next
	else if (n->next != nullptr)
	{
		//point previous node to next
	}
}
