import math
import sys
from operator import itemgetter
import timeit

def setUp():
    f = sys.stdin
    s=[]
    for line in f:
        p = line.split()
        p = list(map(float, p))
        for i in range(0, len(p), 2):
            s.append((p[i], p[i+1]))
    s = sorted(s, key = itemgetter(0))
    print('number of input points: ' + str(len(s)))
    return s


def getX(xy):
    """sort helper: gets x coordinate from tuple (x,y)"""
    return xy[0]


def getY(xy):
    'sort helper: gets y coordinate from enumerated tuple (i, (x,y))'
    return xy[1][1]


def sortX(coordList):
    'returns list of coordinates (x,y) sorted by x value using built-in timsort O(n lg n)'
    return sorted(coordList, key=getX)


def sortY(coordList):
    'returns list of coordinates (x,y) sorted by y value using built-in timsort O(n lg n)'
    tempList = [[i[0], i[1]] for i in sorted(enumerate(coordList), key=getY)]
    return [i[1] for i in tempList], [i[0] for i in tempList]


def lineLength(a, b):
    'computes length of line connecting points a and b, stored as (x,y) tuples'
    return math.sqrt((b[0] - a[0]) ** 2 + (b[1] - a[1]) ** 2)


def closestPair(coord, ySorted, yxIndex):
    'computes closest pair of points from pre-sorted lists of coordinates'
    xSorted = sortX(coord)
    listLen = len(xSorted)
    # base case
    if (listLen <= 1 ):
        return (None, None, float("inf"))
    # if (listLen <= 3):

    #     minLen = lineLength(xSorted[0], xSorted[1])
    #     minPair = [0, 1]

    #     for i in range(listLen - 1):
    #         for j in range(i + 1, listLen):
    #             testLen = lineLength(xSorted[i], xSorted[j])
    #             if (testLen < minLen):
    #                 minLen = testLen
    #                 minPair = [i, j]
    #     return (xSorted[minPair[0]], xSorted[minPair[1]], minLen)

    # Split xSorted preserving sort
    splitLine = listLen // 2
    xLeft = xSorted[0:splitLine]
    xRight = xSorted[splitLine:]

    # Split ySorted and yxIndex preserving sort
    yLeft = []
    yRight = []

    yxIndexLeft = []
    yxIndexRight = []

    for xIndex in yxIndex:
        if xIndex < splitLine:
            yLeft.append(xSorted[xIndex])
            yxIndexLeft.append(xIndex)
        else:
            yRight.append(xSorted[xIndex])
            yxIndexRight.append(xIndex - splitLine)

    # Find minimum closest pair in left and right partitions
    leftClosestPair = closestPair(xLeft, yLeft, yxIndexLeft)
    rightClosestPair = closestPair(xRight, yRight, yxIndexRight)

    if (leftClosestPair[2] < rightClosestPair[2]):
        minClosestPair = leftClosestPair
    else:
        minClosestPair = rightClosestPair

    dMin = minClosestPair[2]

    # Compute yMid array
    splitMinusD = xSorted[splitLine - 1][0] - dMin
    splitPlusD = xSorted[splitLine - 1][0] + dMin

    yMid = []

    for coord in ySorted:
        if (splitMinusD <= coord[0] <= splitPlusD):
            yMid.append(coord)

    # Find closest pair in yMid
    if (len(yMid) > 1):
        yMidClosestPair = (yMid[0], yMid[1], lineLength(yMid[0], yMid[1]))

        for i in range(len(yMid) - 1):

            if ((i + 8) > len(yMid)):
                end = len(yMid)
            else:
                end = i + 8

            for j in range(i + 1, end):
                pairLength = lineLength(yMid[i], yMid[j])
                if (pairLength < yMidClosestPair[2]):
                    yMidClosestPair = (yMid[i], yMid[j], pairLength)

        if (yMidClosestPair[2] < minClosestPair[2]):
            return yMidClosestPair

    return minClosestPair


def RunClosestPair():
    'reads list of coordinates from stdin, sorts list, and calls closestPair'

    coordList = setUp()
    # x = sortX(coordList)
    y, yxIndices = sortY(coordList)
    print("(x1,y1), (x2,y2), distance:  ", closestPair(coordList, y, yxIndices))


# Main Program
start = timeit.default_timer()
RunClosestPair()
stop = timeit.default_timer()
print('Time: ', stop - start)  