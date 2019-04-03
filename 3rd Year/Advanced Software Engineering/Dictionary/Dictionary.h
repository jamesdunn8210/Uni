#pragma once
#include <string>

namespace Containers 
{
	class Dictionary
	{
	public:
		Dictionary() = default;
		Dictionary(const Dictionary &);
		Dictionary & operator=(const Dictionary &);
		~Dictionary();

		using Key = std::string;
		using Item = std::string;

		Item* lookup(Key);
		void insert(Key, Item);
		void remove(Key);

	private:

		struct Node;
		Node* root = nullptr;

		static Item* lookupRec(Key, Node*);
		static void insertRec(Key, Item, Node*&);
		static void removeRec(Key, Node*&, Node*);

		static Node* detachMinimumNode(Node* &);

		static void deepDelete(Node*);
		static Node* deepCopy(Node*);

		static bool treeIsEqual(Node*, Node*);

	};

	
	
}