import numpy as np

def simple_sort(my_list):
    print('my_list in python fn: ', my_list)
    my_list = list(my_list)
    my_list.sort()
    return np.array(my_list)

def factorial(a):
    fact = 1
    for i in range(1, a+1):
        fact *= i
    return fact

number = 55
text = "Subscribe"