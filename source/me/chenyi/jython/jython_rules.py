import sys

#sys.add_package('me.chenyi.jython')

from me.chenyi.jython import ScriptLibrary

scriptLibrary = ScriptLibrary.getInstance()

def getCurrentMovieId():
    return scriptLibrary.getCurrentMovieId()

def setCurrentMovieId(id):
    return scriptLibrary.setCurrentMovieId(id)

def addMovie(movieName):
    return scriptLibrary.addMovie(movieName)

def addMovies(movieName):
    return scriptLibrary.addMovies(movieName)

def addMovies(movieName, maxCount):
    return scriptLibrary.addMovies(movieName, maxCount)

def updateMovie(movieId):
    return scriptLibrary.updateMovie(movieName)

def getMovieCount():
    return scriptLibrary.getMovieCount()

def getAttributeValue(itemId, attributeName):
    return scriptLibrary.getAttributeValue(itemId, attributeName)

def getAttributeName(attributeId):
    return scriptLibrary.getAttributeName(attributeId)
