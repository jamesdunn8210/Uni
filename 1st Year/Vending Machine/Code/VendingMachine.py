import time
import pickle
import random

VALID_INPUT = ["A1","A2","A3","A4",
               "B1","B2","B3","B4",
               "C1","C2","C3","C4",
               "D1","D2","D3","D4"]

money = random.randrange(3,8)
selection = ""

def import_content():
    file = open("inventory.dat","rb")

    global item
    global quantity
    global price
    
    item = pickle.load(file)
    quantity = pickle.load(file)
    price = pickle.load(file)
    file.close()

    
def display_board(): 

    print("You have £",money, "left\n")    

    for i,x,y,z in zip(VALID_INPUT,item,quantity,price):
        print (i," ",x,"  ",y,"  ","£",z)
        
  
def user_cash(money,item_price,quantity):
    if money>item_price and quantity!=0:
        return money-item_price
    else:
        print("sorry, you dont have the funds")
        return money
        

def new_stock(money,instock,item,item_price):
    if instock !=0 and money>item_price:
        print("You purchased a",item)
        return instock-1
    else:
        print("Sorry, we are sold out")
        return instock
        
def save():
    file_write = open("inventory.dat", "wb") 
    pickle.dump(item,file_write)
    pickle.dump(quantity,file_write)
    pickle.dump(price,file_write)
    file_write.close()
    
def sale(item,inventory):
    sale = time.strftime("%d/%m/%Y") + " " + time.strftime("%H:%M:%S")
    text_file=open("sales.txt","a")
    str_inventory = str(inventory)
    text_file.write(item + "  current stock: "+ str_inventory+ "     " + sale + "\n")    
    text_file.close()


#Start
import_content()
display_board()

while selection !="QUIT":
    selection = input("Choose your item ('quit' to end):")
    selection = selection.upper()
    
    if selection in VALID_INPUT: 
        item_id = VALID_INPUT.index(selection)
        
        if quantity[item_id] != 0:
            pass
            money = user_cash(money,price[item_id],quantity[item_id])       
        if money > price[item_id]:
            pass
            quantity[item_id] = new_stock(money,quantity[item_id],item[item_id],price[item_id])

        sale(item[item_id],quantity[item_id])

        display_board()
    elif selection == "QUIT":
        break
    else:
        print("Please enter a valid selection")

save()

