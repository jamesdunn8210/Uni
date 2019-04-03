import pickle

def get_inventory():
    file_read = open("inventory.dat", "rb") 
    
    global item
    global quantity
    global price
    
    item = pickle.load(file_read)
    quantity = pickle.load(file_read)
    price = pickle.load(file_read)

    
def display_inventory():
    
    for x,y,z in zip(item,quantity,price):
        print (x,y,z)

        
def add_new_item():
    new_item = input("Enter new Item ")
    new_item = new_item.capitalize()
    new_quantity = int(input("Enter new Quantity "))
    new_price = float(input("Enter new Price "))

    item.append(new_item)
    quantity.append(new_quantity)
    price.append(new_price)
    print(new_item, " added")

    save()

def remove_item():
    remove_item = input("Enter the item to remove ")
    remove_item = remove_item.capitalize()
    if remove_item in item:
        position = item.index(remove_item)
        
        item.remove(item[position])
        quantity.remove(quantity[position])
        price.remove(price[position])
        print(remove_item, " removed")
        
    else:
        print(remove_item, "Item not found")

    save()

def restock_item():
    restock = input("Enter the item to restock ")
    restock = restock.capitalize()
    if restock in item:
        position = item.index(restock)
        print("There are currently ",quantity[position],"in stock")
        new_stock = int(input("Enter new stock number "))
        quantity[position] = new_stock
        print("Item restocked")
    else:
        print(restock, ": Item not found")

    save()
    
    
def save():
    file_write = open("inventory.dat", "wb") 
    pickle.dump(item,file_write)
    pickle.dump(quantity,file_write)
    pickle.dump(price,file_write)
    file_write.close()

def sale():
    sales = open("sales.txt","r")
    for i in sales:
        print(i.rstrip())

def alert():
    for x,y in zip(item,quantity):
        if y == 0:
            print("ALERT: ",x,"needs restocking")

get_inventory()
alert()
choice = None
while choice != "0":

    print(
    """
    Vending Machine
    
    0 - Exit
    1 - Show Stock
    2 - Add an Item
    3 - Remove Item
    4 - Restock Item
    5 - View Recent Sales
    """
    )
    
    choice = input("Choice: ")

    if choice == "0":
        print("Exiting....")
        
    elif choice == "1":
        display_inventory()
        
    elif choice == "2":
        add_new_item()
        save()

    elif choice == "3":
        remove_item()
        save()
    elif choice =="4":
        restock_item()
        save()
        
    elif choice =="5":
        sale()
        
    else:
        print("Sorry, but ", choice, "isn't a valid choice.")
  
