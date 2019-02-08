import math
import sys
from operator import itemgetter
import timeit


def setUp():
    f = sys.stdin
    s = []
    for line in f:
        p = line.split()
        p = list(map(float, p))
        for i in range(0, len(p), 2):
            s.append((p[i], p[i+1]))
    s = sorted(s, key=itemgetter(0))
    print('number of input points: ' + str(len(s)))
    return s


def getX(xy):
    """sort helper: gets x coordinate from tuple (x,y)"""
    return xy[0]


def getY(xy):
    'sort helper: gets y coordinate from enumerated tuple (i, (x,y))'
    return xy[1][1]


def lineLength(a, b):
    'computes length of line connecting points a and b, stored as (x,y) tuples'
    return math.sqrt((b[0] - a[0]) ** 2 + (b[1] - a[1]) ** 2)


# def closestPair(xSorted, ySorted, yxIndex):
#     'computes closest pair of points from pre-sorted lists of coordinates'
#     # xSorted = sortX(coord)
#     listLen = len(xSorted)
#     # # base case
#     # if (listLen <= 1 ):
#     #     return (None, None, float("inf"))
#     if (listLen <= 3):

#         minLen = lineLength(xSorted[0], xSorted[1])
#         minPair = [0, 1]

#         for i in range(listLen - 1):
#             for j in range(i + 1, listLen):
#                 testLen = lineLength(xSorted[i], xSorted[j])
#                 if (testLen < minLen):
#                     minLen = testLen
#                     minPair = [i, j]
#         return (xSorted[minPair[0]], xSorted[minPair[1]], minLen)


def RunClosestPair():
    'reads list of coordinates from stdin, sorts list, and calls closestPair'

    coordList = setUp()
    minLen = lineLength(coordList[0], coordList[1])
    minPair = [0, 1]

    listLen = len(coordList)
    for i in range(listLen):
        for j in range(listLen):
            if (i != j):
                testLen = lineLength(coordList[i], coordList[j])
                if (testLen < minLen):
                    minLen = testLen
                    minPair = [i, j]
    print("(x1,y1), (x2,y2), distance:  ", (coordList[minPair[0]], coordList[minPair[1]], minLen))


# Main Program
start = timeit.default_timer()
RunClosestPair()
stop = timeit.default_timer()
print('Time: ', stop - start)
